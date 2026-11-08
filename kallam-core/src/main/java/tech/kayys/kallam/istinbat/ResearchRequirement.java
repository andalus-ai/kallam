package tech.kayys.kallam.istinbat;

import java.util.Objects;

/** An explicit evidence expectation supplied by the selected methodology/profile. */
public record ResearchRequirement(
        String id,
        EvidenceType evidenceType,
        String specialist,
        String objective,
        boolean primarySourceOnly) {

    public ResearchRequirement {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(evidenceType, "evidenceType");
        Objects.requireNonNull(specialist, "specialist");
        Objects.requireNonNull(objective, "objective");
        if (id.isBlank() || specialist.isBlank() || objective.isBlank()) {
            throw new IllegalArgumentException("requirement fields must not be blank");
        }
    }
}
