package tech.kayys.kallam.istinbat;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Structured analysis returned by a reasoning adapter.
 * Source evidence remains owned by retrieval ports; analysis may only reference evidence already in state.
 */
public record ResearchAnalysisResult(
        String methodologyId,
        String methodologyVersion,
        Status status,
        List<Claim> claims,
        List<ResearchArgument> arguments,
        List<ResearchConflict> conflicts,
        List<String> positions,
        List<String> usulFindings,
        List<String> unresolvedQuestions,
        List<String> uncertaintyNotes,
        ResearchTokenUsage tokenUsage) {

    public ResearchAnalysisResult {
        methodologyId = requireText(methodologyId, "methodologyId");
        methodologyVersion = requireText(methodologyVersion, "methodologyVersion");
        Objects.requireNonNull(status, "status");
        claims = claims == null ? List.of() : List.copyOf(claims);
        arguments = arguments == null ? List.of() : List.copyOf(arguments);
        conflicts = conflicts == null ? List.of() : List.copyOf(conflicts);
        positions = positions == null ? List.of() : List.copyOf(positions);
        usulFindings = usulFindings == null ? List.of() : List.copyOf(usulFindings);
        unresolvedQuestions = unresolvedQuestions == null ? List.of() : List.copyOf(unresolvedQuestions);
        uncertaintyNotes = uncertaintyNotes == null ? List.of() : List.copyOf(uncertaintyNotes);
        tokenUsage = tokenUsage == null ? ResearchTokenUsage.none() : tokenUsage;
    }

    public void validateAgainst(ResearchState state, ResearchMethodology methodology) {
        if (!methodologyId.equals(methodology.id())
                || !methodologyVersion.equals(methodology.version())) {
            throw new IllegalArgumentException("analysis methodology does not match the research request");
        }

        Set<String> evidenceIds = new HashSet<>();
        state.evidence().forEach(e -> evidenceIds.add(e.id()));

        Set<String> claimIds = new HashSet<>();
        for (Claim claim : claims) {
            if (claim.id() == null || claim.id().isBlank() || claim.statement() == null
                    || claim.statement().isBlank() || !claimIds.add(claim.id())) {
                throw new IllegalArgumentException("analysis contains an invalid or duplicate claim");
            }
        }

        Set<String> argumentIds = new HashSet<>();
        for (ResearchArgument argument : arguments) {
            if (!argumentIds.add(argument.id())) {
                throw new IllegalArgumentException("analysis contains a duplicate argument id");
            }
            if (!claimIds.contains(argument.claimId())) {
                throw new IllegalArgumentException("argument references an unknown claim: " + argument.claimId());
            }
            requireKnownEvidence(argument.evidenceIds(), evidenceIds, "argument");
        }

        Set<String> conflictIds = new HashSet<>();
        for (ResearchConflict conflict : conflicts) {
            if (!conflictIds.add(conflict.id())) {
                throw new IllegalArgumentException("analysis contains a duplicate conflict id");
            }
            if (!claimIds.containsAll(conflict.claimIds())) {
                throw new IllegalArgumentException("conflict references an unknown claim");
            }
            requireKnownEvidence(conflict.evidenceIds(), evidenceIds, "conflict");
        }
    }

    private static void requireKnownEvidence(List<String> ids, Set<String> known, String owner) {
        for (String id : ids) {
            if (!known.contains(id)) {
                throw new IllegalArgumentException(owner + " references unknown evidence: " + id);
            }
        }
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }

    public enum Status { COMPLETE, PARTIAL, NOT_CONFIGURED }
}
