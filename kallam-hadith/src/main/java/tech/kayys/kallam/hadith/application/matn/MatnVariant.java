package tech.kayys.kallam.hadith.application.matn;

import java.util.List;
import tech.kayys.kallam.hadith.domain.CollectionId;
import tech.kayys.kallam.hadith.domain.EditionId;
import tech.kayys.kallam.hadith.domain.OccurrenceId;

/** One immutable, source-attested Matn representation and its search-only projection. */
public record MatnVariant(
        OccurrenceId occurrenceId,
        CollectionId collectionId,
        EditionId editionId,
        String sourceLocator,
        String originalArabicMatn,
        String normalizedMatn,
        List<MatnDifference> differencesFromBaseline) {
    public MatnVariant {
        if (occurrenceId == null || collectionId == null || editionId == null) {
            throw new IllegalArgumentException("source identifiers must not be null");
        }
        if (originalArabicMatn == null || originalArabicMatn.isBlank()) {
            throw new IllegalArgumentException("originalArabicMatn must not be blank");
        }
        differencesFromBaseline = List.copyOf(differencesFromBaseline == null ? List.of() : differencesFromBaseline);
    }
}
