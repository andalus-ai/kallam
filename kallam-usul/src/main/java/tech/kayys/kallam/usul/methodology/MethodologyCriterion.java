package tech.kayys.kallam.usul.methodology;

import java.util.List;
import java.util.Objects;

/** A sourced description of a methodological criterion, not executable legal judgment. */
public record MethodologyCriterion(String criterionId, MethodologyTopic topic, String description,
        MethodologyReviewState reviewState, List<MethodologySourceReference> sources, String reviewerReference) {
    public MethodologyCriterion {
        criterionId = required(criterionId, "criterionId");
        Objects.requireNonNull(topic, "topic");
        description = required(description, "description");
        Objects.requireNonNull(reviewState, "reviewState");
        sources = List.copyOf(Objects.requireNonNull(sources, "sources"));
        reviewerReference = optional(reviewerReference);
        if (reviewState == MethodologyReviewState.SCHOLAR_REVIEWED
                && (sources.isEmpty() || reviewerReference == null))
            throw new IllegalArgumentException("a scholar-reviewed criterion requires sources and reviewer attribution");
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
