package tech.kayys.kallam.hadith.application.matn;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.hadith.domain.*;
import tech.kayys.kallam.hadith.port.HadithCorpus;

class MatnVariantComparisonServiceTest {
    private static final HadithId HADITH = new HadithId("narration-1");
    private static final CollectionId COLLECTION = new CollectionId("collection-a");
    private static final EditionId EDITION_A = new EditionId("edition-a");
    private static final EditionId EDITION_B = new EditionId("edition-b");

    @Test
    void preservesOriginalTextAndFindsLexicalInsertion() {
        var first = occurrence("occ-a", EDITION_A, "قال رسول الله");
        var second = occurrence("occ-b", EDITION_B, "قال رسول الله صلى الله عليه وسلم");
        var result = service(first, second).compare(HADITH);

        assertEquals(new OccurrenceId("occ-a"), result.baselineOccurrenceId());
        assertEquals("قال رسول الله", result.variants().get(0).originalArabicMatn());
        assertEquals("قال رسول الله صلى الله عليه وسلم", result.variants().get(1).originalArabicMatn());
        assertTrue(result.variants().get(1).differencesFromBaseline().stream()
                .anyMatch(d -> d.operation() == MatnDifference.Operation.INSERTION));
    }

    @Test
    void diacriticAndAlefOrthographyDifferencesNormalizeToSameProjection() {
        var first = occurrence("occ-a", EDITION_A, "إِنَّ اللَّهَ");
        var second = occurrence("occ-b", EDITION_B, "ان الله");
        var result = service(first, second).compare(HADITH);
        assertEquals(result.variants().get(0).normalizedMatn(), result.variants().get(1).normalizedMatn());
        assertTrue(result.variants().get(1).differencesFromBaseline().isEmpty());
        assertNotEquals(result.variants().get(0).originalArabicMatn(), result.variants().get(1).originalArabicMatn());
    }

    @Test
    void deterministicOrderingAndEmptyCorpusAreSupported() {
        var result = service(occurrence("z", EDITION_B, "متن"), occurrence("a", EDITION_A, "متن"))
                .compare(HADITH);
        assertEquals("a", result.variants().get(0).occurrenceId().value());
        assertTrue(new MatnVariantComparisonService(emptyCorpus()).compare(HADITH).variants().isEmpty());
    }

    private static MatnVariantComparisonService service(HadithOccurrence... occurrences) {
        List<HadithOccurrence> list = List.of(occurrences);
        return new MatnVariantComparisonService(new HadithCorpus() {
            public Optional<HadithCollection> findCollection(CollectionId id) { return Optional.empty(); }
            public Optional<HadithEdition> findEdition(EditionId id) { return Optional.empty(); }
            public Optional<HadithOccurrence> findOccurrence(OccurrenceId id) {
                return list.stream().filter(o -> o.id().equals(id)).findFirst();
            }
            public List<HadithOccurrence> findOccurrences(HadithId id) {
                return HADITH.equals(id) ? list : List.of();
            }
        });
    }

    private static HadithCorpus emptyCorpus() {
        return new HadithCorpus() {
            public Optional<HadithCollection> findCollection(CollectionId id) { return Optional.empty(); }
            public Optional<HadithEdition> findEdition(EditionId id) { return Optional.empty(); }
            public Optional<HadithOccurrence> findOccurrence(OccurrenceId id) { return Optional.empty(); }
            public List<HadithOccurrence> findOccurrences(HadithId id) { return List.of(); }
        };
    }

    private static HadithOccurrence occurrence(String id, EditionId edition, String matn) {
        return new HadithOccurrence(new OccurrenceId(id), HADITH, COLLECTION, edition,
                null, null, null, null, null, matn, null, "ar", "source:" + id, "batch-1");
    }
}
