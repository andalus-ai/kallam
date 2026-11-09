package tech.kayys.kallam.istinbat;

import java.util.List;
import java.util.Objects;

/** A surfaced tension for review, never an automatic reconciliation or tarjih. */
public record ResearchConflict(
        String id,
        Type type,
        Severity severity,
        String description,
        List<String> claimIds,
        List<String> evidenceIds) {

    public ResearchConflict {
        id = requireText(id, "id");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(severity, "severity");
        description = requireText(description, "description");
        claimIds = claimIds == null ? List.of() : List.copyOf(claimIds);
        evidenceIds = evidenceIds == null ? List.of() : List.copyOf(evidenceIds);
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }

    public enum Type {
        CONTRADICTORY_EVIDENCE,
        INTERPRETIVE_TENSION,
        METHODOLOGICAL_DISAGREEMENT
    }

    public enum Severity { LOW, MEDIUM, HIGH }
}
