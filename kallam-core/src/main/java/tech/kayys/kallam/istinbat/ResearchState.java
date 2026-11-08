package tech.kayys.kallam.istinbat;

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
    }

    public ResearchState withPhase(ResearchPhase next) {
        return new ResearchState(caseId, next, masalah, claims, evidence, positions,
                usulFindings, argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round, toolCalls);
    }

    public ResearchState addEvidence(List<Evidence> additions) {
        var merged = new java.util.ArrayList<>(evidence);
        merged.addAll(additions);
        return new ResearchState(caseId, phase, masalah, claims, merged, positions,
                usulFindings, argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round, toolCalls + additions.size());
    }

    public ResearchState withCritique(ResearchCritique value) {
        return new ResearchState(caseId, ResearchPhase.CRITIQUE, masalah, claims, evidence,
                positions, usulFindings, argumentsFor, argumentsAgainst,
                value.requiredResearch(), value, round, toolCalls);
    }

    public ResearchState nextRound() {
        return new ResearchState(caseId, phase, masalah, claims, evidence, positions,
                usulFindings, argumentsFor, argumentsAgainst, unresolvedQuestions,
                critique, round + 1, toolCalls);
    }
}
