package tech.kayys.kallam.istinbat;

import java.util.List;
import java.util.Objects;

/** A reasoned relationship to a claim; it is not itself source evidence. */
public record ResearchArgument(
        String id,
        String claimId,
        Direction direction,
        String statement,
        List<String> evidenceIds,
        double confidence,
        Origin origin) {

    public ResearchArgument {
        id = requireText(id, "id");
        claimId = requireText(claimId, "claimId");
        Objects.requireNonNull(direction, "direction");
        statement = requireText(statement, "statement");
        evidenceIds = evidenceIds == null ? List.of() : List.copyOf(evidenceIds);
        Objects.requireNonNull(origin, "origin");
        if (confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("confidence must be between 0 and 1");
        }
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }

    public enum Direction { SUPPORTS, CHALLENGES }

    public enum Origin {
        MODEL_INFERENCE,
        SCHOLAR_ANNOTATION,
        DETERMINISTIC_RULE
    }
}
