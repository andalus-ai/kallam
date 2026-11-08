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
        int toolCalls) {

    public ResearchState {
        claims = claims == null ? List.of() : List.copyOf(claims);
        evidence = evidence == null ? List.of() : List.copyOf(evidence);
        positions = positions == null ? List.of() : List.copyOf(positions);
        usulFindings = usulFindings == null ? List.of() : List.copyOf(usulFindings);
        argumentsFor = argumentsFor == null ? List.of() : List.copyOf(argumentsFor);
        argumentsAgainst = argumentsAgainst == null ? List.of() : List.copyOf(argumentsAgainst);
        unresolvedQuestions = unresolvedQuestions == null ? List.of() : List.copyOf(unresolvedQuestions);
        if (round < 0 || toolCalls < 0) {
            throw new IllegalArgumentException("round and toolCalls must not be negative");
        }
    }

    public ResearchState withPhase(ResearchPhase next) {
        return new ResearchState(caseId, next, masalah, claims, evidence, positions,
                usulFindings, argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round, toolCalls);
    }

    /** Adds new evidence without treating each evidence item as a tool invocation. */
    public ResearchState addEvidence(List<Evidence> additions) {
        if (additions == null || additions.isEmpty()) return this;
        var merged = new LinkedHashMap<String, Evidence>();
        evidence.forEach(item -> merged.put(item.id(), item));
        additions.stream().filter(item -> item != null && item.id() != null)
                .forEach(item -> merged.putIfAbsent(item.id(), item));
        return new ResearchState(caseId, phase, masalah, claims,
                new ArrayList<>(merged.values()), positions, usulFindings,
                argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round, toolCalls);
    }

    public ResearchState recordSpecialistInvocation() {
        return new ResearchState(caseId, phase, masalah, claims, evidence, positions,
                usulFindings, argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round, toolCalls + 1);
    }

    public ResearchState withCritique(ResearchCritique value) {
        // Critique requirements are not factual unknowns. Keep the two concepts separate.
        return new ResearchState(caseId, ResearchPhase.CRITIQUE, masalah, claims, evidence,
                positions, usulFindings, argumentsFor, argumentsAgainst,
                unresolvedQuestions, value, round, toolCalls);
    }

    public ResearchState nextRound() {
        return new ResearchState(caseId, phase, masalah, claims, evidence, positions,
                usulFindings, argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round + 1, toolCalls);
    }
}
