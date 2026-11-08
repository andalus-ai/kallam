package tech.kayys.kallam.hadith.domain;

import java.util.Objects;

/**
 * One source-attested occurrence. Original text is preserved verbatim;
 * normalized text is an optional search projection and never replaces it.
 */
public record HadithOccurrence(
        OccurrenceId id,
        HadithId hadithId,
        CollectionId collectionId,
        EditionId editionId,
        String bookReference,
        String chapterReference,
        String sourceNumber,
        String volume,
        String page,
        String originalArabicMatn,
        String originalIsnadText,
        String language,
        String sourceLocator,
        String importBatchId) {
    public HadithOccurrence {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(hadithId, "hadithId");
        Objects.requireNonNull(collectionId, "collectionId");
        Objects.requireNonNull(editionId, "editionId");
        originalArabicMatn = required(originalArabicMatn, "originalArabicMatn");
        bookReference = clean(bookReference);
        chapterReference = clean(chapterReference);
        sourceNumber = clean(sourceNumber);
        volume = clean(volume);
        page = clean(page);
        originalIsnadText = clean(originalIsnadText);
        language = language == null || language.isBlank() ? "ar" : language.trim();
        sourceLocator = clean(sourceLocator);
        importBatchId = clean(importBatchId);
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value;
    }
    private static String clean(String value) { return value == null ? null : value.trim(); }
}
