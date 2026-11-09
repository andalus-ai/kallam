package tech.kayys.kallam.usul.methodology;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Versioned description of a usul methodology. Profiles do not imply unanimity
 * within a madhhab, rank traditions, or issue a ruling.
 */
public record MethodologyProfile(String profileId, String version, MethodologyTradition tradition,
        String name, String description, MethodologyProfileStatus status,
        List<MethodologyCriterion> criteria, List<MethodologySourceReference> sources, String reviewerReference) {
    public MethodologyProfile {
        profileId = required(profileId, "profileId");
        version = required(version, "version");
        Objects.requireNonNull(tradition, "tradition");
        name = required(name, "name");
        description = required(description, "description");
        Objects.requireNonNull(status, "status");
        criteria = List.copyOf(Objects.requireNonNull(criteria, "criteria"));
        sources = List.copyOf(Objects.requireNonNull(sources, "sources"));
        reviewerReference = optional(reviewerReference);
        Set<String> ids = new HashSet<>();
        for (MethodologyCriterion criterion : criteria) {
            if (!ids.add(criterion.criterionId()))
                throw new IllegalArgumentException("criterion IDs must be unique within a profile");
        }
        if (status == MethodologyProfileStatus.SCHOLAR_REVIEWED || status == MethodologyProfileStatus.PUBLISHED) {
            boolean reviewed = criteria.stream().anyMatch(c -> c.reviewState() == MethodologyReviewState.SCHOLAR_REVIEWED);
            if (sources.isEmpty() || reviewerReference == null || !reviewed)
                throw new IllegalArgumentException("reviewed or published profiles require sources, a reviewer and a reviewed criterion");
        }
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
