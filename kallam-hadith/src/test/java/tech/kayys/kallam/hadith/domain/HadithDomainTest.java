package tech.kayys.kallam.hadith.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HadithDomainTest {
    @Test
    void occurrenceKeepsEditionSpecificNumberingAndOriginalText() {
        var occurrence = new HadithOccurrence(
                new OccurrenceId("occ-1"), new HadithId("hadith-1"),
                new CollectionId("bukhari"), new EditionId("bukhari-edition-1"),
                "Book 1", "Chapter 1", "1", "1", "2",
                "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ", "chain as printed", "ar",
                "vol. 1, p. 2", "import-1");

        assertEquals("1", occurrence.sourceNumber());
        assertEquals("إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ", occurrence.originalArabicMatn());
        assertEquals("chain as printed", occurrence.originalIsnadText());
    }

    @Test
    void gradingIsAttributedAndAttributionStateIsIndependent() {
        var record = new GradingRecord("grade-1", new HadithId("hadith-1"), null,
                "Sahih", "Scholar A", "Work A", "vol. 2, p. 10",
                "Reason recorded by the source", AttributionStatus.SOURCE_LOCATED);

        assertEquals("Sahih", record.gradingTerm());
        assertEquals(AttributionStatus.SOURCE_LOCATED, record.attributionStatus());
    }

    @Test
    void rejectsBlankOriginalMatn() {
        assertThrows(IllegalArgumentException.class, () -> new HadithOccurrence(
                new OccurrenceId("occ-1"), new HadithId("hadith-1"),
                new CollectionId("collection"), new EditionId("edition"),
                null, null, null, null, null, "  ", null, "ar", null, null));
    }
}
