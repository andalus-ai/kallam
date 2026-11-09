package tech.kayys.kallam.usul.ijma;

import java.util.List;
import java.util.Objects;

/**
 * A reviewable, methodology-scoped assessment of a Companion's qualification
 * for a specified research question. It is not a global ranking of Companions.
 */
public record CompanionQualificationAssessment(
        String assessmentId,
        CompanionId companionId,
        String methodologyProfileId,
        String questionScope,
        QualificationStatus status,
        List<String> criterionIds,
        List<String> biographicalAttestationIds,
        String reviewerReference,
        String rationale) {

    public enum QualificationStatus {
        NOT_ASSESSED, UNDER_REVIEW, QUALIFIED_WITHIN_SCOPE, NOT_QUALIFIED_WITHIN_SCOPE, DISPUTED
    }

    public CompanionQualificationAssessment {
        assessmentId = required(assessmentId, "assessmentId");
        Objects.requireNonNull(companionId, "companionId");
        methodologyProfileId = required(methodologyProfileId, "methodologyProfileId");
        questionScope = required(questionScope, "questionScope");
        Objects.requireNonNull(status, "status");
        criterionIds = normalized(criterionIds, "criterionIds");
        biographicalAttestationIds = normalized(biographicalAttestationIds, "biographicalAttestationIds");
        reviewerReference = optional(reviewerReference);
        rationale = optional(rationale);
        if (status == QualificationStatus.QUALIFIED_WITHIN_SCOPE
                || status == QualificationStatus.NOT_QUALIFIED_WITHIN_SCOPE
                || status == QualificationStatus.DISPUTED) {
            if (criterionIds.isEmpty() || biographicalAttestationIds.isEmpty()
                    || reviewerReference == null || rationale == null)
                throw new IllegalArgumentException("a concluded assessment requires criteria, attestations, reviewer and rationale");
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optional(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private static List<String> normalized(List<String> values, String field) {
        Objects.requireNonNull(values, field);
        if (values.stream().anyMatch(v -> v == null || v.isBlank()))
            throw new IllegalArgumentException(field + " must not contain blank values");
        return values.stream().map(String::trim).distinct().toList();
    }
}
