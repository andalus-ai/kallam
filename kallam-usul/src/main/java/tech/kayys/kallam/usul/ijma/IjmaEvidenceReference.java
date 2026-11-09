package tech.kayys.kallam.usul.ijma;

/** A source locator supporting or challenging an ijma' claim; metadata is not proof by itself. */
public record IjmaEvidenceReference(String sourceId, String locator, EvidenceRole role,
        EvidenceStatus status, String reviewerReference) {
    public enum EvidenceRole { CONSENSUS_REPORT, COMPANION_POSITION, DISSENT_REPORT, METHODOLOGY_DISCUSSION }
    public enum EvidenceStatus { UNVERIFIED, SOURCE_LOCATED, SOURCE_VERIFIED, SCHOLAR_REVIEWED }
    public IjmaEvidenceReference {
        sourceId = required(sourceId, "sourceId");
        locator = required(locator, "locator");
        if (role == null || status == null) throw new IllegalArgumentException("role and status are required");
        reviewerReference = optional(reviewerReference);
        if (status == EvidenceStatus.SCHOLAR_REVIEWED && reviewerReference == null)
            throw new IllegalArgumentException("scholar-reviewed evidence requires reviewerReference");
    }
    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optional(String v) { return v == null || v.isBlank() ? null : v.trim(); }
}
