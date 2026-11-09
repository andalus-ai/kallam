package tech.kayys.kallam.istinbat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public record ResearchState(
        String caseId,
        ResearchPhase phase,
        Masalah masalah,
        List<Claim> claims,
        List<Evidence> evidence,
        List<String> positions,
        List<String> usulFindings,
        List<String> argumentsFor,
        List<String> argumentsAgainst,
        List<String> unresolvedQuestions,
        ResearchCritique critique,
        int round,
        int toolCalls,
        ResearchAnalysisResult analysisResult,
        int reasoningTokensUsed) {

    /** Backward-compatible constructor for existing core and integration callers. */
    public ResearchState(
            String caseId, ResearchPhase phase, Masalah masalah,
            List<Claim> claims, List<Evidence> evidence, List<String> positions,
            List<String> usulFindings, List<String> argumentsFor, List<String> argumentsAgainst,
            List<String> unresolvedQuestions, ResearchCritique critique, int round, int toolCalls) {
        this(caseId, phase, masalah, claims, evidence, positions, usulFindings,
                argumentsFor, argumentsAgainst, unresolvedQuestions, critique,
                round, toolCalls, null, 0);
    }

    public ResearchState {
        claims = claims == null ? List.of() : List.copyOf(claims);
        evidence = evidence == null ? List.of() : List.copyOf(evidence);
        positions = positions == null ? List.of() : List.copyOf(positions);
        usulFindings = usulFindings == null ? List.of() : List.copyOf(usulFindings);
        argumentsFor = argumentsFor == null ? List.of() : List.copyOf(argumentsFor);
        argumentsAgainst = argumentsAgainst == null ? List.of() : List.copyOf(argumentsAgainst);
        unresolvedQuestions = unresolvedQuestions == null ? List.of() : List.copyOf(unresolvedQuestions);
        if (round < 0 || toolCalls < 0 || reasoningTokensUsed < 0) {
            throw new IllegalArgumentException("round and usage counters must not be negative");
        }
    }

    public ResearchState withPhase(ResearchPhase next) {
        return copy(next, claims, evidence, positions, usulFindings, argumentsFor,
                argumentsAgainst, unresolvedQuestions, critique, round, toolCalls,
                analysisResult, reasoningTokensUsed);
    }

    /** Adds new evidence without treating each evidence item as a tool invocation. */
    public ResearchState addEvidence(List<Evidence> additions) {
        if (additions == null || additions.isEmpty()) return this;
        var merged = new LinkedHashMap<String, Evidence>();
        evidence.forEach(item -> merged.put(item.id(), item));
        additions.stream().filter(item -> item != null && item.id() != null)
                .forEach(item -> merged.putIfAbsent(item.id(), item));
        return copy(phase, claims, new ArrayList<>(merged.values()), positions, usulFindings,
                argumentsFor, argumentsAgainst, unresolvedQuestions, critique, round,
                toolCalls, analysisResult, reasoningTokensUsed);
    }

    public ResearchState recordSpecialistInvocation() {
        return copy(phase, claims, evidence, positions, usulFindings, argumentsFor,
                argumentsAgainst, unresolvedQuestions, critique, round, toolCalls + 1,
                analysisResult, reasoningTokensUsed);
    }

    public ResearchState withCritique(ResearchCritique value) {
        // Critique requirements are not factual unknowns. Keep the two concepts separate.
        return copy(ResearchPhase.CRITIQUE, claims, evidence, positions, usulFindings,
                argumentsFor, argumentsAgainst, unresolvedQuestions, value, round,
                toolCalls, analysisResult, reasoningTokensUsed);
    }

    public ResearchState withAnalysisResult(
            ResearchAnalysisResult result,
            ResearchMethodology methodology,
            int remainingReasoningTokens) {
        if (result == null) return this;
        result.validateAgainst(this, methodology);
        int used = result.tokenUsage().reasoningTokens();
        if (used > remainingReasoningTokens) {
            throw new IllegalArgumentException("analysis exceeded remaining reasoning-token budget");
        }

        var supports = result.arguments().stream()
                .filter(a -> a.direction() == ResearchArgument.Direction.SUPPORTS)
                .map(ResearchArgument::statement).toList();
        var challenges = result.arguments().stream()
                .filter(a -> a.direction() == ResearchArgument.Direction.CHALLENGES)
                .map(ResearchArgument::statement).toList();

        var unresolved = new ArrayList<>(unresolvedQuestions);
        unresolved.addAll(result.unresolvedQuestions());
        if (methodology.structuredAnalysisRequired()
                && result.status() != ResearchAnalysisResult.Status.COMPLETE) {
            unresolved.add("Structured analysis required by methodology "
                    + methodology.id() + "@" + methodology.version()
                    + " is not complete.");
        }

        return copy(ResearchPhase.ANALYSIS,
                result.claims(), evidence, result.positions(), result.usulFindings(),
                supports, challenges, unresolved, critique, round, toolCalls,
                result, Math.addExact(reasoningTokensUsed, used));
    }

    public ResearchState addUnresolvedQuestion(String question) {
        if (question == null || question.isBlank() || unresolvedQuestions.contains(question)) return this;
        var unresolved = new ArrayList<>(unresolvedQuestions);
        unresolved.add(question);
        return copy(phase, claims, evidence, positions, usulFindings, argumentsFor,
                argumentsAgainst, unresolved, critique, round, toolCalls,
                analysisResult, reasoningTokensUsed);
    }

    public ResearchState nextRound() {
        return copy(phase, claims, evidence, positions, usulFindings, argumentsFor,
                argumentsAgainst, unresolvedQuestions, critique, round + 1, toolCalls,
                analysisResult, reasoningTokensUsed);
    }

    private ResearchState copy(
            ResearchPhase nextPhase, List<Claim> nextClaims, List<Evidence> nextEvidence,
            List<String> nextPositions, List<String> nextUsulFindings,
            List<String> nextArgumentsFor, List<String> nextArgumentsAgainst,
            List<String> nextUnresolved, ResearchCritique nextCritique,
            int nextRound, int nextToolCalls, ResearchAnalysisResult nextAnalysis,
            int nextReasoningTokens) {
        return new ResearchState(caseId, nextPhase, masalah, nextClaims, nextEvidence,
                nextPositions, nextUsulFindings, nextArgumentsFor, nextArgumentsAgainst,
                nextUnresolved, nextCritique, nextRound, nextToolCalls,
                nextAnalysis, nextReasoningTokens);
    }
}
