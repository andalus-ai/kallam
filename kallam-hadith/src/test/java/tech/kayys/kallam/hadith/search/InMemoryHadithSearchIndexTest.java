package tech.kayys.kallam.hadith.search;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.hadith.domain.*;
import tech.kayys.kallam.hadith.port.HadithSearchQuery;

class InMemoryHadithSearchIndexTest {
    private HadithOccurrence occurrence(String id, String matn, String isnad, String language) {
        return new HadithOccurrence(new OccurrenceId(id), new HadithId("narration-" + id),
                new CollectionId("collection"), new EditionId("edition"), null, null, id, null, null,
                matn, isnad, language, "locator/" + id, "batch");
    }

    @Test void exactSearchUsesOriginalSourceText() {
        InMemoryHadithSearchIndex index = new InMemoryHadithSearchIndex();
        index.index(occurrence("1", "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ", "حدثنا فلان", "ar"));
        var hits = index.search(new HadithSearchQuery("إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ", "ar",
                HadithSearchQuery.SearchMode.EXACT, 10));
        assertEquals(1, hits.size());
        assertEquals("EXACT", hits.getFirst().matchType());
    }

    @Test void normalizedSearchFindsDiacriticAndAlefVariants() {
        InMemoryHadithSearchIndex index = new InMemoryHadithSearchIndex();
        index.index(occurrence("1", "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ", null, "ar"));
        var hits = index.search(new HadithSearchQuery("انما الاعمال بالنيات", "ar",
                HadithSearchQuery.SearchMode.NORMALIZED, 10));
        assertEquals(1, hits.size());
        assertEquals("NORMALIZED", hits.getFirst().matchType());
    }

    @Test void lexicalSearchCanMatchMatnOrIsnadTerms() {
        InMemoryHadithSearchIndex index = new InMemoryHadithSearchIndex();
        index.index(occurrence("1", "متن الحديث", "حدثنا محمد عن مالك", "ar"));
        var hits = index.search(new HadithSearchQuery("محمد مالك", "ar",
                HadithSearchQuery.SearchMode.LEXICAL, 10));
        assertEquals(1, hits.size());
        assertEquals(1.0, hits.getFirst().score());
    }

    @Test void filtersLanguageAndSupportsRemovalAndReplacement() {
        InMemoryHadithSearchIndex index = new InMemoryHadithSearchIndex();
        index.index(occurrence("1", "السلام عليكم", null, "ar"));
        index.index(occurrence("2", "peace be upon you", null, "en"));
        assertTrue(index.search(new HadithSearchQuery("السلام", "en", HadithSearchQuery.SearchMode.LEXICAL, 10)).isEmpty());
        assertTrue(index.remove(new OccurrenceId("1")));
        assertFalse(index.remove(new OccurrenceId("1")));
        assertEquals(1, index.size());
    }

    @Test void semanticModeFailsExplicitlyRatherThanPretendingToBeSemantic() {
        InMemoryHadithSearchIndex index = new InMemoryHadithSearchIndex();
        assertThrows(UnsupportedOperationException.class, () -> index.search(
                new HadithSearchQuery("حديث", "ar", HadithSearchQuery.SearchMode.SEMANTIC, 10)));
    }
}
