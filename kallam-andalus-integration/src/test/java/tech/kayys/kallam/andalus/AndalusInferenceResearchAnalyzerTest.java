package tech.kayys.kallam.andalus;

import org.junit.jupiter.api.Test;
import tech.kayys.andalus.harness.inference.InferenceConstraints;
import tech.kayys.andalus.harness.inference.InferenceInput;
import tech.kayys.andalus.harness.inference.InferenceRequest;
import tech.kayys.andalus.harness.inference.InferenceResult;
import tech.kayys.andalus.harness.inference.InferenceRuntime;
import tech.kayys.andalus.harness.inference.InferenceStatus;
import tech.kayys.andalus.harness.inference.InferenceUsage;
import tech.kayys.kallam.istinbat.Evidence;
import tech.kayys.kallam.istinbat.EvidenceStatus;
import tech.kayys.kallam.istinbat.EvidenceType;
import tech.kayys.kallam.istinbat.Masalah;
import tech.kayys.kallam.istinbat.ResearchAnalysisResult;
import tech.kayys.kallam.istinbat.ResearchMethodology;
import tech.kayys.kallam.istinbat.ResearchPhase;
import tech.kayys.kallam.istinbat.ResearchState;
import tech.kayys.kallam.istinbat.SourceReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class AndalusInferenceResearchAnalyzerTest {

    private static final ResearchMethodology METHODOLOGY =
            new ResearchMethodology("shafii-research", "1.2.0", "shafii",
                    List.of("fiqh", "usul"), true);

    @Test
    void mapsMethodologyEvidenceAndBudgetIntoAndalusRequestAndParsesStructuredOutput() {
        AtomicReference<InferenceRequest> captured = new AtomicReference<>();
        InferenceRuntime runtime = request -> {
            captured.set(request);
            return CompletableFuture.completedFuture(success(validJson(), 200, 100, 25));
        };

        var result = analyzer(runtime).analyze(state(), METHODOLOGY, 80);

        assertEquals(ResearchAnalysisResult.Status.COMPLETE, result.status());
        assertEquals("claim-1", result.claims().getFirst().id());
        assertEquals(List.of("ev-1"), result.arguments().getFirst().evidenceIds());
        assertEquals(200, result.tokenUsage().inputTokens());
        assertEquals(100, result.tokenUsage().outputTokens());
        assertEquals(25, result.tokenUsage().reasoningTokens());
        assertEquals(4096, captured.get().plan().constraints().maxOutputTokens());
        assertEquals(80, captured.get().plan().constraints().attributes().get("kallam.reasoningTokenBudget"));
        assertEquals(8192, captured.get().plan().constraints().maxInputTokens());
        assertEquals("kallam.istinbat.structured-analysis", captured.get().plan().requirementId());
        assertTrue(captured.get().plan().metadata().containsKey("kallam.schema.version"));
        assertInstanceOf(Map.class, captured.get().input().content());
    }

    @Test
    void downgradesOutputWhenProviderDoesNotReportReasoningTokenUsage() {
        InferenceRuntime runtime = request -> CompletableFuture.completedFuture(
                new InferenceResult(InferenceStatus.COMPLETED, validJson(),
                        InferenceUsage.of(100, 50), Optional.of("test-model"),
                        Optional.of("test-provider"), Map.of()));

        var result = analyzer(runtime).analyze(state(), METHODOLOGY, 80);

        assertEquals(ResearchAnalysisResult.Status.PARTIAL, result.status());
        assertTrue(result.claims().isEmpty());
        assertTrue(result.uncertaintyNotes().getFirst().contains("did not report reasoning-token usage"));
    }

    @Test
    void rejectsProviderReasoningUsageOverBudget() {
        InferenceRuntime runtime = request -> CompletableFuture.completedFuture(success(validJson(), 100, 50, 81));

        var result = analyzer(runtime).analyze(state(), METHODOLOGY, 80);

        assertEquals(ResearchAnalysisResult.Status.PARTIAL, result.status());
        assertTrue(result.claims().isEmpty());
        assertTrue(result.uncertaintyNotes().getFirst().contains("exceeded"));
    }

    @Test
    void rejectsArgumentsThatReferenceEvidenceNotInResearchState() {
        String invalid = validJson().replace("[\"ev-1\"]", "[\"fabricated-evidence\"]");
        InferenceRuntime runtime = request -> CompletableFuture.completedFuture(success(invalid, 100, 50, 10));

        var result = analyzer(runtime).analyze(state(), METHODOLOGY, 80);

        assertEquals(ResearchAnalysisResult.Status.PARTIAL, result.status());
        assertTrue(result.claims().isEmpty());
        assertTrue(result.uncertaintyNotes().getFirst().contains("invalid scholarly-analysis output"));
    }

    @Test
    void rejectsMethodologyMismatchAndUnknownSchemaFields() {
        String wrongMethodology = validJson().replace("shafii-research", "hanafi-research");
        InferenceRuntime runtime = request -> CompletableFuture.completedFuture(success(wrongMethodology, 100, 50, 10));

        var mismatch = analyzer(runtime).analyze(state(), METHODOLOGY, 80);
        assertEquals(ResearchAnalysisResult.Status.PARTIAL, mismatch.status());

        String extraField = validJson().replace("\"status\":", "\"unexpected\":true,\"status\":");
        InferenceRuntime extraRuntime = request -> CompletableFuture.completedFuture(success(extraField, 100, 50, 10));
        var unknown = analyzer(extraRuntime).analyze(state(), METHODOLOGY, 80);
        assertEquals(ResearchAnalysisResult.Status.PARTIAL, unknown.status());
    }

    @Test
    void malformedOutputAndFailedInferenceFailClosed() {
        InferenceRuntime malformed = request -> CompletableFuture.completedFuture(success("{not-json", 100, 50, 10));
        assertEquals(ResearchAnalysisResult.Status.PARTIAL,
                analyzer(malformed).analyze(state(), METHODOLOGY, 80).status());

        InferenceRuntime failed = request -> CompletableFuture.completedFuture(
                InferenceResult.failed(InferenceStatus.PROVIDER_UNAVAILABLE, "offline"));
        assertEquals(ResearchAnalysisResult.Status.PARTIAL,
                analyzer(failed).analyze(state(), METHODOLOGY, 80).status());
    }

    @Test
    void doesNotInvokeRuntimeWhenNoBudgetRemains() {
        AtomicInteger calls = new AtomicInteger();
        InferenceRuntime runtime = request -> {
            calls.incrementAndGet();
            return CompletableFuture.completedFuture(success(validJson(), 100, 50, 1));
        };

        var result = analyzer(runtime).analyze(state(), METHODOLOGY, 0);

        assertEquals(0, calls.get());
        assertEquals(ResearchAnalysisResult.Status.PARTIAL, result.status());
    }

    @Test
    void rejectsCompletedResponsesWithoutProviderIdentity() {
        InferenceRuntime runtime = request -> CompletableFuture.completedFuture(
                new InferenceResult(InferenceStatus.COMPLETED, validJson(),
                        InferenceUsage.of(100, 50), Optional.of("test-model"),
                        Optional.empty(), Map.of(AndalusInferenceResearchAnalyzer.REASONING_TOKENS_METADATA_KEY, 10)));

        var result = analyzer(runtime).analyze(state(), METHODOLOGY, 80);

        assertEquals(ResearchAnalysisResult.Status.PARTIAL, result.status());
        assertTrue(result.claims().isEmpty());
        assertTrue(result.uncertaintyNotes().getFirst().contains("provider identity"));
    }

    @Test
    void cancelsInferenceWhenConfiguredTimeoutExpires() {
        CompletableFuture<InferenceResult> pending = new CompletableFuture<>();
        InferenceRuntime runtime = request -> pending;
        var adapter = new AndalusInferenceResearchAnalyzer(runtime, "test-model",
                new com.fasterxml.jackson.databind.ObjectMapper(), Duration.ofMillis(1));

        var result = adapter.analyze(state(), METHODOLOGY, 80);

        assertEquals(ResearchAnalysisResult.Status.PARTIAL, result.status());
        assertTrue(pending.isCancelled());
        assertTrue(result.uncertaintyNotes().getFirst().contains("cancellation was requested"));
    }
    private static AndalusInferenceResearchAnalyzer analyzer(InferenceRuntime runtime) {
        return new AndalusInferenceResearchAnalyzer(runtime, "test-model");
    }

    private static InferenceResult success(String output, long input, long generated, int reasoning) {
        return new InferenceResult(InferenceStatus.COMPLETED, output,
                InferenceUsage.of(input, generated), Optional.of("test-model"),
                Optional.of("test-provider"), Map.of(AndalusInferenceResearchAnalyzer.REASONING_TOKENS_METADATA_KEY, reasoning));
    }

    private static ResearchState state() {
        Evidence evidence = new Evidence("ev-1", EvidenceType.FIQH, "Arabic source", "Translation",
                new SourceReference("source-1", "Fiqh source", "Author", null,
                        null, null, null, "page 1", Map.of()),
                EvidenceStatus.PRIMARY_SOURCE, 0.9, List.of(), List.of(), Map.of("sourceId", "source-1"));
        return new ResearchState("case-1", ResearchPhase.ANALYSIS,
                new Masalah("research question", "case description", List.of(), List.of()),
                List.of(), List.of(evidence), List.of(), List.of(), List.of(),
                List.of(), List.of(), null, 1, 0);
    }

    private static String validJson() {
        return """
                {
                  "schemaVersion":"kallam-research-analysis-v1",
                  "methodologyId":"shafii-research",
                  "methodologyVersion":"1.2.0",
                  "status":"COMPLETE",
                  "claims":[{"id":"claim-1","statement":"A carefully qualified proposition","factual":false,"unresolved":false}],
                  "arguments":[{"id":"argument-1","claimId":"claim-1","direction":"SUPPORTS",
                    "statement":"A preliminary analytical observation","evidenceIds":["ev-1"],"confidence":0.65}],
                  "conflicts":[],
                  "positions":["Position requiring scholar review"],
                  "usulFindings":["A methodological observation"],
                  "unresolvedQuestions":[],
                  "uncertaintyNotes":["Interpretive uncertainty remains"]
                }
                """;
    }
}
