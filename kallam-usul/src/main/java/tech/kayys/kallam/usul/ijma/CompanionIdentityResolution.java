package tech.kayys.kallam.usul.ijma;

import java.util.List;
import java.util.Objects;

/**
 * Review record for a name-to-identity lookup. Acceptance links a lookup to one
 * existing identity; it never merges or deletes identity records.
 */
public record CompanionIdentityResolution(
        String resolutionId,
        String observedName,
        CompanionNameVariant.NameKind nameKind,
        List<CompanionId> candidateCompanionIds,
        ResolutionStatus status,
        CompanionId selectedCompanionId,
        List<String> supportingCitationIds,
        String reviewerReference,
        String rationale) {

    public enum ResolutionStatus { CANDIDATES_PROPOSED, REVIEW_REQUIRED, AMBIGUOUS, MATCH_ACCEPTED, NO_MATCH }

    public CompanionIdentityResolution {
        resolutionId = required(resolutionId, "resolutionId");
        observedName = required(observedName, "observedName");
        Objects.requireNonNull(nameKind, "nameKind");
        candidateCompanionIds = List.copyOf(Objects.requireNonNull(candidateCompanionIds, "candidateCompanionIds"))
                .stream().distinct().toList();
        if (candidateCompanionIds.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("candidateCompanionIds must not contain null");
        Objects.requireNonNull(status, "status");
        supportingCitationIds = normalize(supportingCitationIds, "supportingCitationIds");
        reviewerReference = optional(reviewerReference);
        rationale = optional(rationale);
        if (status == ResolutionStatus.MATCH_ACCEPTED) {
            if (selectedCompanionId == null || !candidateCompanionIds.contains(selectedCompanionId)
                    || supportingCitationIds.isEmpty() || reviewerReference == null || rationale == null)
                throw new IllegalArgumentException("accepted match requires a selected candidate, citations, reviewer and rationale");
        } else if (selectedCompanionId != null) {
            throw new IllegalArgumentException("only MATCH_ACCEPTED may select a Companion identity");
        }
        if (status == ResolutionStatus.NO_MATCH && candidateCompanionIds.size() > 0)
            throw new IllegalArgumentException("NO_MATCH cannot retain positive identity candidates");
        if (status == ResolutionStatus.AMBIGUOUS && candidateCompanionIds.size() < 2)
            throw new IllegalArgumentException("AMBIGUOUS requires at least two candidates");
    }

    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optional(String v) { return v == null || v.isBlank() ? null : v.trim(); }
    private static List<String> normalize(List<String> values, String field) {
        Objects.requireNonNull(values, field);
        if (values.stream().anyMatch(v -> v == null || v.isBlank()))
            throw new IllegalArgumentException(field + " must not contain blank values");
        return values.stream().map(String::trim).distinct().toList();
    }
}
