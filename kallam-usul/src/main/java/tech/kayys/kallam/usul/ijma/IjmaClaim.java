package tech.kayys.kallam.usul.ijma;
import java.util.List;
import java.util.Objects;

/** A researchable ijma' claim. A claim is not presumed established merely because it is recorded. */
public record IjmaClaim(String claimId, String issue, String proposition, IjmaScope scope, IjmaType type,
        IjmaAssessmentStatus status, String methodologyProfileId,
        List<IjmaEvidenceReference> evidence, String reviewerReference) {
    public IjmaClaim {
        claimId = required(claimId, "claimId");
        issue = required(issue, "issue");
        proposition = required(proposition, "proposition");
        Objects.requireNonNull(scope, "scope");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(status, "status");
        methodologyProfileId = optional(methodologyProfileId);
        evidence = List.copyOf(Objects.requireNonNull(evidence, "evidence"));
        reviewerReference = optional(reviewerReference);
        if ((status == IjmaAssessmentStatus.REPORTED || status == IjmaAssessmentStatus.DISPUTED
                || status == IjmaAssessmentStatus.VERIFIED_WITHIN_METHODOLOGY) && evidence.isEmpty())
            throw new IllegalArgumentException("reported, disputed or verified claims require evidence references");
        if (status == IjmaAssessmentStatus.VERIFIED_WITHIN_METHODOLOGY
                && (methodologyProfileId == null || reviewerReference == null))
            throw new IllegalArgumentException("methodology verification requires a methodology profile and reviewer");
    }
    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optional(String v) { return v == null || v.isBlank() ? null : v.trim(); }
}
