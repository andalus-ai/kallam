package tech.kayys.kallam.andalus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.kayys.andalus.harness.inference.InferenceConstraints;
import tech.kayys.andalus.harness.inference.InferenceInput;
import tech.kayys.andalus.harness.inference.InferenceMetadata;
import tech.kayys.andalus.harness.inference.InferencePlan;
import tech.kayys.andalus.harness.inference.InferenceRequest;
import tech.kayys.andalus.harness.inference.InferenceResult;
import tech.kayys.andalus.harness.inference.InferenceRuntime;
import tech.kayys.kallam.istinbat.Claim;
import tech.kayys.kallam.istinbat.ResearchAnalysisResult;
import tech.kayys.kallam.istinbat.ResearchAnalyzer;
import tech.kayys.kallam.istinbat.ResearchArgument;
import tech.kayys.kallam.istinbat.ResearchConflict;
import tech.kayys.kallam.istinbat.ResearchMethodology;
import tech.kayys.kallam.istinbat.ResearchState;
import tech.kayys.kallam.istinbat.ResearchTokenUsage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Optional adapter from Kallam's scholarly-analysis port to the Andalus inference runtime.
 *
 * <p>Kallam owns the methodology and output contract. Andalus owns inference execution.
 * This adapter never creates source evidence: model arguments and conflicts must cite IDs
 * already present in the supplied research state.</p>
 */
public final class AndalusInferenceResearchAnalyzer implements ResearchAnalyzer {

    public static final String PROMPT_VERSION = "kallam-istinbat-analysis-v1";
    public static final String SCHEMA_VERSION = "kallam-research-analysis-v1";
    public static final String REASONING_TOKENS_METADATA_KEY = InferenceMetadata.REASONING_TOKENS;
    public static final String REASONING_BUDGET_ATTRIBUTE_KEY = InferenceMetadata.REASONING_TOKEN_BUDGET;
    private static final int MAX_INPUT_TOKENS = 8192;
    private static final int MAX_OUTPUT_TOKENS = 4096;
    private static final Set<String> ROOT_FIELDS = Set.of(
            "schemaVersion", "methodologyId", "methodologyVersion", "status", "claims", "arguments",
            "conflicts", "positions", "usulFindings", "unresolvedQuestions", "uncertaintyNotes");

    private final InferenceRuntime runtime;
    private final String modelId;
    private final Optional<String> providerId;
    private final ObjectMapper mapper;
    private final Duration timeout;

    public AndalusInferenceResearchAnalyzer(InferenceRuntime runtime, String modelId) {
        this(runtime, modelId, Optional.empty(), new ObjectMapper(), Duration.ofSeconds(45));
    }

    public AndalusInferenceResearchAnalyzer(
            InferenceRuntime runtime, String modelId, ObjectMapper mapper, Duration timeout) {
        this(runtime, modelId, Optional.empty(), mapper, timeout);
    }

    public AndalusInferenceResearchAnalyzer(
            InferenceRuntime runtime,
            String modelId,
            Optional<String> providerId,
            ObjectMapper mapper,
            Duration timeout) {
        this.runtime = java.util.Objects.requireNonNull(runtime, "runtime");
        this.modelId = requireText(modelId, "modelId");
        this.providerId = providerId == null ? Optional.empty() : providerId
                .map(value -> requireText(value, "providerId"));
        this.mapper = java.util.Objects.requireNonNull(mapper, "mapper");
        this.timeout = java.util.Objects.requireNonNull(timeout, "timeout");
        if (timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException("timeout must be positive");
        }
    }

    @Override
    public ResearchAnalysisResult analyze(
            ResearchState state, ResearchMethodology methodology, int remainingReasoningTokens) {
        java.util.Objects.requireNonNull(state, "state");
        java.util.Objects.requireNonNull(methodology, "methodology");
        if (remainingReasoningTokens <= 0) {
            return partial(methodology, ResearchTokenUsage.none(),
                    "No reasoning-token budget remains; inference was not invoked.");
        }

        CompletableFuture<InferenceResult> execution;
        try {
            execution = runtime.execute(request(state, methodology, remainingReasoningTokens)).toCompletableFuture();
        } catch (RuntimeException invocationFailure) {
            return partial(methodology, ResearchTokenUsage.none(),
                    "Andalus inference could not be started; no model analysis was accepted ("
                            + invocationFailure.getClass().getSimpleName() + ").");
        }

        InferenceResult inference;
        try {
            inference = execution.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException timedOut) {
            execution.cancel(true);
            return partial(methodology, ResearchTokenUsage.none(),
                    "Andalus inference exceeded the configured timeout; cancellation was requested.");
        } catch (InterruptedException interrupted) {
            execution.cancel(true);
            Thread.currentThread().interrupt();
            return partial(methodology, ResearchTokenUsage.none(),
                    "Andalus inference was interrupted; cancellation was requested and no model analysis was accepted.");
        } catch (Exception failure) {
            return partial(methodology, ResearchTokenUsage.none(),
                    "Andalus inference failed; no model analysis was accepted ("
                            + failure.getClass().getSimpleName() + ").");
        }

        if (inference == null || !inference.isSuccess()) {
            String status = inference == null ? "no result" : inference.status().name();
            return partial(methodology, ResearchTokenUsage.none(),
                    "Andalus inference did not complete successfully (" + status + ").");
        }

        int reportedReasoningTokens;
        ResearchTokenUsage usage;
        try {
            requireProviderIdentity(inference);
            reportedReasoningTokens = reportedReasoningTokens(inference);
            usage = usage(inference, Math.max(0, reportedReasoningTokens));
        } catch (RuntimeException invalidUsage) {
            return partial(methodology, ResearchTokenUsage.none(),
                    "Andalus provider identity or token-usage metadata was missing or invalid; output was rejected.");
        }
        if (reportedReasoningTokens < 0) {
            return partial(methodology, usage,
                    "Provider did not report reasoning-token usage; model output was not accepted.");
        }
        if (reportedReasoningTokens > remainingReasoningTokens) {
            return partial(methodology, usage,
                    "Provider-reported reasoning-token usage exceeded the remaining Kallam budget; output was rejected.");
        }

        try {
            ResearchAnalysisResult parsed = parse(inference.output(), methodology, usage);
            parsed.validateAgainst(state, methodology);
            return parsed;
        } catch (RuntimeException malformed) {
            return partial(methodology, usage,
                    "Andalus returned malformed or invalid scholarly-analysis output; output was rejected ("
                            + malformed.getClass().getSimpleName() + ").");
        }
    }

    private InferenceRequest request(
            ResearchState state, ResearchMethodology methodology, int remainingReasoningTokens) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("systemInstruction", """
                You are an analytical assistant supporting qualified scholars conducting Fiqh research.
                Do not issue a fatwa, choose a madhhab, perform tarjih, or claim scholarly authority.
                Apply only the explicitly supplied methodology. Distinguish source evidence from inference.
                Do not invent quotations, sources, narrators, authenticity grades, or evidence identifiers.
                Every argument and conflict must cite one or more supplied evidence IDs.
                If evidence is insufficient or a point is disputed, state uncertainty and unresolved questions.
                Return one JSON object matching the supplied schema. Do not include Markdown fences.
                """);
        payload.put("task", "Analyze the current research state under the selected methodology.");
        payload.put("methodology", Map.of(
                "id", methodology.id(),
                "version", methodology.version(),
                "school", methodology.school(),
                "disciplines", methodology.disciplines(),
                "structuredAnalysisRequired", methodology.structuredAnalysisRequired()));
        payload.put("caseId", state.caseId());
        payload.put("masalah", state.masalah());
        payload.put("currentClaims", state.claims());
        payload.put("existingPositions", state.positions());
        payload.put("existingUsulFindings", state.usulFindings());
        payload.put("evidence", state.evidence().stream().map(this::evidencePayload).toList());
        payload.put("remainingReasoningTokenBudget", remainingReasoningTokens);
        payload.put("promptVersion", PROMPT_VERSION);
        payload.put("schemaVersion", SCHEMA_VERSION);
        payload.put("outputSchema", schemaDescription());

        InferenceConstraints constraints = new InferenceConstraints(
                MAX_INPUT_TOKENS,
                MAX_OUTPUT_TOKENS,
                (long) MAX_INPUT_TOKENS + MAX_OUTPUT_TOKENS,
                timeout.toMillis(),
                Optional.empty(),
                Map.of(REASONING_BUDGET_ATTRIBUTE_KEY, remainingReasoningTokens));
        InferencePlan plan = new InferencePlan(
                java.util.UUID.randomUUID().toString(),
                "kallam.istinbat.structured-analysis",
                modelId,
                providerId,
                Set.of("text-generation", "structured-json"),
                constraints,
                "DIRECT",
                Map.of("kallam.prompt.version", PROMPT_VERSION,
                        "kallam.schema.version", SCHEMA_VERSION,
                        "kallam.case.id", state.caseId(),
                        REASONING_BUDGET_ATTRIBUTE_KEY, remainingReasoningTokens));
        return new InferenceRequest(plan, InferenceInput.of(payload));
    }

    private Map<String, Object> evidencePayload(tech.kayys.kallam.istinbat.Evidence evidence) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", evidence.id());
        item.put("type", evidence.type() == null ? null : evidence.type().name());
        item.put("arabicText", evidence.arabicText());
        item.put("translation", evidence.translation());
        item.put("source", evidence.source());
        item.put("status", evidence.status() == null ? null : evidence.status().name());
        item.put("relevance", evidence.relevance());
        item.put("provenance", evidence.provenance());
        return item;
    }

    private ResearchAnalysisResult parse(
            Object output, ResearchMethodology methodology, ResearchTokenUsage usage) {
        JsonNode root;
        try {
            root = output instanceof String text ? mapper.readTree(text) : mapper.valueToTree(output);
        } catch (Exception invalidJson) {
            throw new IllegalArgumentException("output is not valid JSON", invalidJson);
        }
        requireObject(root, "root");
        rejectUnknown(root, ROOT_FIELDS, "root");

        String schemaVersion = requiredText(root, "schemaVersion");
        if (!SCHEMA_VERSION.equals(schemaVersion)) {
            throw new IllegalArgumentException("unsupported analysis schema version: " + schemaVersion);
        }
        String methodologyId = requiredText(root, "methodologyId");
        String methodologyVersion = requiredText(root, "methodologyVersion");
        if (!methodology.id().equals(methodologyId)
                || !methodology.version().equals(methodologyVersion)) {
            throw new IllegalArgumentException("model output methodology does not match the request");
        }

        String statusText = requiredText(root, "status");
        ResearchAnalysisResult.Status status = switch (statusText) {
            case "COMPLETE" -> ResearchAnalysisResult.Status.COMPLETE;
            case "PARTIAL" -> ResearchAnalysisResult.Status.PARTIAL;
            default -> throw new IllegalArgumentException("model cannot set analysis status to " + statusText);
        };

        List<Claim> claims = new ArrayList<>();
        for (JsonNode node : array(root, "claims")) {
            requireObject(node, "claim");
            rejectUnknown(node, Set.of("id", "statement", "factual", "unresolved"), "claim");
            claims.add(new Claim(requiredText(node, "id"), requiredText(node, "statement"),
                    requiredBoolean(node, "factual"), requiredBoolean(node, "unresolved")));
        }

        List<ResearchArgument> arguments = new ArrayList<>();
        for (JsonNode node : array(root, "arguments")) {
            requireObject(node, "argument");
            rejectUnknown(node, Set.of("id", "claimId", "direction", "statement",
                    "evidenceIds", "confidence"), "argument");
            arguments.add(new ResearchArgument(
                    requiredText(node, "id"),
                    requiredText(node, "claimId"),
                    ResearchArgument.Direction.valueOf(requiredText(node, "direction")),
                    requiredText(node, "statement"),
                    stringArray(node, "evidenceIds"),
                    requiredNumber(node, "confidence"),
                    ResearchArgument.Origin.MODEL_INFERENCE));
        }

        List<ResearchConflict> conflicts = new ArrayList<>();
        for (JsonNode node : array(root, "conflicts")) {
            requireObject(node, "conflict");
            rejectUnknown(node, Set.of("id", "type", "severity", "description",
                    "claimIds", "evidenceIds"), "conflict");
            conflicts.add(new ResearchConflict(
                    requiredText(node, "id"),
                    ResearchConflict.Type.valueOf(requiredText(node, "type")),
                    ResearchConflict.Severity.valueOf(requiredText(node, "severity")),
                    requiredText(node, "description"),
                    stringArray(node, "claimIds"),
                    stringArray(node, "evidenceIds")));
        }

        return new ResearchAnalysisResult(
                methodologyId, methodologyVersion, status, claims, arguments, conflicts,
                stringArray(root, "positions"), stringArray(root, "usulFindings"),
                stringArray(root, "unresolvedQuestions"), stringArray(root, "uncertaintyNotes"), usage);
    }

    private static ResearchAnalysisResult partial(
            ResearchMethodology methodology, ResearchTokenUsage usage, String note) {
        return new ResearchAnalysisResult(methodology.id(), methodology.version(),
                ResearchAnalysisResult.Status.PARTIAL, List.of(), List.of(), List.of(),
                List.of(), List.of(), List.of(), List.of(note), usage);
    }

    private static ResearchTokenUsage usage(InferenceResult result, int reasoningTokens) {
        if (result == null) return ResearchTokenUsage.none();
        int input = Math.toIntExact(result.usage().inputTokens());
        int output = Math.toIntExact(result.usage().outputTokens());
        return new ResearchTokenUsage(input, output, reasoningTokens);
    }

    private static void requireProviderIdentity(InferenceResult result) {
        if (result.modelId().isEmpty() || result.modelId().get().isBlank()
                || result.providerId().isEmpty() || result.providerId().get().isBlank()) {
            throw new IllegalArgumentException("provider and model identity are required");
        }
    }

    private static int reportedReasoningTokens(InferenceResult result) {
        Object value = result.metadata().get(REASONING_TOKENS_METADATA_KEY);
        // Read the pre-standardization key for compatibility with older provider adapters.
        if (value == null) value = result.metadata().get("kallam.usage.reasoningTokens");
        if (!(value instanceof Number number)) return -1;
        long tokens = number.longValue();
        if (tokens < 0 || tokens > Integer.MAX_VALUE || number.doubleValue() != tokens) {
            throw new IllegalArgumentException("invalid provider reasoningTokens metadata");
        }
        return (int) tokens;
    }

    private static String schemaDescription() {
        return """
                {
                  "schemaVersion":"kallam-research-analysis-v1",
                  "methodologyId":"string",
                  "methodologyVersion":"string",
                  "status":"COMPLETE|PARTIAL",
                  "claims":[{"id":"string","statement":"string","factual":false,"unresolved":false}],
                  "arguments":[{"id":"string","claimId":"string","direction":"SUPPORTS|CHALLENGES",
                    "statement":"string","evidenceIds":["existing-id"],"confidence":0.0}],
                  "conflicts":[{"id":"string","type":"CONTRADICTORY_EVIDENCE|INTERPRETIVE_TENSION|METHODOLOGICAL_DISAGREEMENT",
                    "severity":"LOW|MEDIUM|HIGH","description":"string","claimIds":["claim-id"],
                    "evidenceIds":["existing-id"]}],
                  "positions":["string"],"usulFindings":["string"],
                  "unresolvedQuestions":["string"],"uncertaintyNotes":["string"]
                }
                """;
    }

    private static JsonNode array(JsonNode parent, String field) {
        JsonNode value = parent.get(field);
        if (value == null || value.isNull()) return com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.arrayNode();
        if (!value.isArray()) throw new IllegalArgumentException(field + " must be an array");
        return value;
    }

    private static List<String> stringArray(JsonNode parent, String field) {
        List<String> values = new ArrayList<>();
        for (JsonNode value : array(parent, field)) {
            if (!value.isTextual() || value.asText().isBlank()) {
                throw new IllegalArgumentException(field + " must contain nonblank strings");
            }
            values.add(value.asText());
        }
        return List.copyOf(values);
    }

    private static void requireObject(JsonNode node, String name) {
        if (node == null || !node.isObject()) throw new IllegalArgumentException(name + " must be an object");
    }

    private static void rejectUnknown(JsonNode node, Set<String> allowed, String name) {
        node.fieldNames().forEachRemaining(field -> {
            if (!allowed.contains(field)) throw new IllegalArgumentException("unknown " + name + " field: " + field);
        });
    }

    private static String requiredText(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || !value.isTextual() || value.asText().isBlank()) {
            throw new IllegalArgumentException(field + " must be a nonblank string");
        }
        return value.asText();
    }

    private static boolean requiredBoolean(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || !value.isBoolean()) throw new IllegalArgumentException(field + " must be boolean");
        return value.asBoolean();
    }

    private static double requiredNumber(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || !value.isNumber() || !Double.isFinite(value.doubleValue())) {
            throw new IllegalArgumentException(field + " must be a finite number");
        }
        return value.doubleValue();
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value;
    }
}
