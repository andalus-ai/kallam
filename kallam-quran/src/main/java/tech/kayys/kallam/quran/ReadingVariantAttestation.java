package tech.kayys.kallam.quran;
import java.util.Objects;
/** Provenance and review record for associating a verse text with a specific reading path. */
public record ReadingVariantAttestation(String attestationId, VerseReference verseReference,
        ReadingPath readingPath, CorpusSourceId sourceId, String importBatchId, String sourceLocator,
        ReadingAttestationStatus status, String reviewerReference) {
    public ReadingVariantAttestation {
        attestationId = required(attestationId, "attestationId");
        Objects.requireNonNull(verseReference, "verseReference");
        Objects.requireNonNull(readingPath, "readingPath");
        Objects.requireNonNull(sourceId, "sourceId");
        importBatchId = required(importBatchId, "importBatchId");
        sourceLocator = required(sourceLocator, "sourceLocator");
        Objects.requireNonNull(status, "status");
        reviewerReference = optionalTrim(reviewerReference);
        if (status == ReadingAttestationStatus.SCHOLAR_REVIEWED && reviewerReference == null)
            throw new IllegalArgumentException("scholar-reviewed attestation requires reviewerReference");
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optionalTrim(String value) { return value == null ? null : value.trim(); }
}
