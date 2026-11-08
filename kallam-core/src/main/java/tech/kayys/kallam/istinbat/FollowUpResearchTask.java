package tech.kayys.kallam.istinbat;

import java.util.Objects;

/**
 * An actionable, bounded research request emitted by critique.
 * The specialist identifier is resolved only against Kallam's registered ports.
 */
public record FollowUpResearchTask(
        String id,
        String specialist,
        String objective,
        String query,
        int topK,
        boolean primarySourceOnly,
        String rationale) {

    public FollowUpResearchTask {
        id = requireText(id, "id");
        specialist = requireText(specialist, "specialist");
        objective = requireText(objective, "objective");
        query = requireText(query, "query");
        rationale = rationale == null ? "" : rationale;
        if (topK < 1) {
            throw new IllegalArgumentException("topK must be positive");
        }
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
