package tech.kayys.kallam.usul.ijma;

import java.util.List;
import java.util.Objects;

/** Explicit record of incompatible or tension-bearing biographical attestations. */
public record BiographicalReportConflict(
        String conflictId,
        CompanionId subject,
        String issue,
        List<String> attestationIds,
        ConflictStatus status,
        String reviewerReference,
        String resolutionRationale) {

    public enum ConflictStatus { OPEN, UNDER_REVIEW, UNRESOLVED, RESOLVED_WITH_QUALIFICATION, RESOLVED_BY_PREFERRED_REPORT }

    public BiographicalReportConflict {
        conflictId = required(conflictId, "conflictId");
        Objects.requireNonNull(subject, "subject");
        issue = required(issue, "issue");
        Objects.requireNonNull(attestationIds, "attestationIds");
        if (attestationIds.stream().anyMatch(v -> v == null || v.isBlank()))
            throw new IllegalArgumentException("attestationIds must not contain blank values");
        attestationIds = attestationIds.stream().map(String::trim).distinct().toList();
        if (attestationIds.size() < 2)
            throw new IllegalArgumentException("a report conflict requires at least two distinct attestations");
        Objects.requireNonNull(status, "status");
        reviewerReference = optional(reviewerReference);
        resolutionRationale = optional(resolutionRationale);
        if (status == ConflictStatus.RESOLVED_WITH_QUALIFICATION
                || status == ConflictStatus.RESOLVED_BY_PREFERRED_REPORT) {
            if (reviewerReference == null || resolutionRationale == null)
                throw new IllegalArgumentException("a resolved conflict requires reviewer attribution and rationale");
        }
    }

    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optional(String v) { return v == null || v.isBlank() ? null : v.trim(); }
}
