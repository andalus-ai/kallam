package tech.kayys.kallam.istinbat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StructuredResearchAnalysisTest {

    private static final ResearchMethodology METHODOLOGY =
            new ResearchMethodology("shafii-research", "1.2.0", "shafii",
                    List.of("fiqh", "usul"), true);

    @Test
    void analysisMustMatchSelectedMethodologyVersion() {
        var state = state(List.of(evidence("ev-1")));
        var result = result("hanafi-research", "1.0.0", List.of(), List.of(), List.of(),
                ResearchTokenUsage.none());

        assertThrows(IllegalArgumentException.class,
                () -> result.validateAgainst(state, METHODOLOGY));
    }

    @Test
    void analysisCannotInventOrReferenceUnknownSourceEvidence() {
        var state = state(List.of(evidence("ev-1")));
        var claim = new Claim("claim-1", "A legal proposition", false, false);
        var argument = new ResearchArgument("arg-1", "claim-1",
                ResearchArgument.Direction.SUPPORTS, "Reasoning statement",
                List.of("not-in-retrieved-corpus"), 0.7,
                ResearchArgument.Origin.MODEL_INFERENCE);
        var result = result(METHODOLOGY.id(), METHODOLOGY.version(),
                List.of(claim), List.of(argument), List.of(), ResearchTokenUsage.none());

        assertThrows(IllegalArgumentException.class,
                () -> result.validateAgainst(state, METHODOLOGY));
    }

    @Test
    void analysisCannotAttachArgumentToUnknownClaim() {
        var state = state(List.of(evidence("ev-1")));
        var argument = new ResearchArgument("arg-1", "missing-claim",
                ResearchArgument.Direction.CHALLENGES, "Counter argument",
                List.of("ev-1"), 0.6, ResearchArgument.Origin.MODEL_INFERENCE);
        var result = result(METHODOLOGY.id(), METHODOLOGY.version(),
                List.of(), List.of(argument), List.of(), ResearchTokenUsage.none());

        assertThrows(IllegalArgumentException.class,
                () -> result.validateAgainst(state, METHODOLOGY));
    }

    @Test
    void stateAccumulatesAuthoritativeReasoningTokenUsageAndStructuredData() {
        var state = state(List.of(evidence("ev-1")));
        var claim = new Claim("claim-1", "A legal proposition", false, false);
        var argument = new ResearchArgument("arg-1", "claim-1",
                ResearchArgument.Direction.SUPPORTS, "Reasoning statement",
                List.of("ev-1"), 0.7, ResearchArgument.Origin.MODEL_INFERENCE);
        var result = result(METHODOLOGY.id(), METHODOLOGY.version(),
                List.of(claim), List.of(argument), List.of(),
                new ResearchTokenUsage(120, 80, 50));

        var analyzed = state.withAnalysisResult(result, METHODOLOGY, 50);

        assertEquals(50, analyzed.reasoningTokensUsed());
        assertEquals("claim-1", analyzed.claims().getFirst().id());
        assertEquals(List.of("Reasoning statement"), analyzed.argumentsFor());
        assertEquals(result, analyzed.analysisResult());
    }

    @Test
    void analysisCannotExceedRemainingReasoningTokenBudget() {
        var state = state(List.of(evidence("ev-1")));
        var result = result(METHODOLOGY.id(), METHODOLOGY.version(),
                List.of(), List.of(), List.of(), new ResearchTokenUsage(100, 50, 51));

        assertThrows(IllegalArgumentException.class,
                () -> state.withAnalysisResult(result, METHODOLOGY, 50));
    }

    @Test
    void defaultAnalyzerDoesNotInventClaimsOrArguments() {
        var state = state(List.of(evidence("ev-1")));

        var result = new DefaultResearchAnalyzer().analyze(state, METHODOLOGY, 500);

        assertEquals(ResearchAnalysisResult.Status.NOT_CONFIGURED, result.status());
        assertTrue(result.claims().isEmpty());
        assertTrue(result.arguments().isEmpty());
        assertEquals(0, result.tokenUsage().reasoningTokens());
    }

    @Test
    void reportPreservesStructuredAnalysisAndStillRequiresScholarJudgment() {
        var state = state(List.of(evidence("ev-1")));
        var claim = new Claim("claim-1", "A legal proposition", false, false);
        var result = result(METHODOLOGY.id(), METHODOLOGY.version(),
                List.of(claim), List.of(), List.of(), ResearchTokenUsage.none());
        var analyzed = state.withAnalysisResult(result, METHODOLOGY, 500);

        var report = new DefaultResearchReportBuilder().build(analyzed);

        assertEquals(result, report.structuredAnalysis());
        assertEquals(List.of("A legal proposition"), report.analysis());
        assertTrue(report.requiresScholarJudgment());
        assertTrue(report.preliminarySynthesis().contains("not a ruling"));
    }

    private static ResearchAnalysisResult result(
            String methodologyId, String version,
            List<Claim> claims, List<ResearchArgument> arguments,
            List<ResearchConflict> conflicts, ResearchTokenUsage usage) {
        return new ResearchAnalysisResult(methodologyId, version,
                ResearchAnalysisResult.Status.COMPLETE,
                claims, arguments, conflicts, List.of("Position A"), List.of("Usul note"),
                List.of(), List.of("Uncertainty note"), usage);
    }

    private static ResearchState state(List<Evidence> evidence) {
        return new ResearchState("case-1", ResearchPhase.ANALYSIS,
                new Masalah("question", "taswir", List.of(), List.of()),
                List.of(), evidence, List.of(), List.of(), List.of(),
                List.of(), List.of(), null, 1, 0);
    }

    private static Evidence evidence(String id) {
        return new Evidence(id, EvidenceType.FIQH, "source text", null,
                new SourceReference(id, "Fiqh source", "Author", null,
                        null, null, null, "page 1", Map.of()),
                EvidenceStatus.PRIMARY_SOURCE, 0.9, List.of(), List.of(),
                Map.of("sourceId", id));
    }
}
