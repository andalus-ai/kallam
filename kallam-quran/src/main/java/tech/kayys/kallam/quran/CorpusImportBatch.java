package tech.kayys.kallam.quran;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
/** Immutable provenance for one imported source artifact or release batch. */
public record CorpusImportBatch(String batchId, CorpusSourceId sourceId, String artifactSha256,
        long recordCount, Instant importedAt, String importerVersion,
        ImportVerificationStatus verificationStatus, String reviewerReference) {
    public CorpusImportBatch {
        batchId = required(batchId, "batchId");
        Objects.requireNonNull(sourceId, "sourceId");
        artifactSha256 = required(artifactSha256, "artifactSha256").toLowerCase(Locale.ROOT);
        if (!artifactSha256.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("artifactSha256 must be a 64-character SHA-256 hex digest");
        if (recordCount < 0) throw new IllegalArgumentException("recordCount must not be negative");
        Objects.requireNonNull(importedAt, "importedAt");
        importerVersion = required(importerVersion, "importerVersion");
        Objects.requireNonNull(verificationStatus, "verificationStatus");
        reviewerReference = optionalTrim(reviewerReference);
        if (verificationStatus == ImportVerificationStatus.SCHOLAR_REVIEWED && reviewerReference == null)
            throw new IllegalArgumentException("scholar-reviewed batch requires reviewerReference");
    }
    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optionalTrim(String v) { return v == null ? null : v.trim(); }
}
