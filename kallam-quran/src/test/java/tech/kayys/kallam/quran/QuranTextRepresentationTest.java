package tech.kayys.kallam.quran;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class QuranTextRepresentationTest {
    @Test void sourceTextIsPreservedExactly() {
        String source = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ";
        var representation = new QuranTextRepresentation("edition-1:1:1", source,
                TextRepresentationKind.SOURCE_TRANSCRIPTION, "UTHMANI", null, null);
        assertEquals(source, representation.text());
        assertEquals(TextRepresentationKind.SOURCE_TRANSCRIPTION, representation.kind());
    }
    @Test void derivedSearchTextMustPointBackToItsSource() {
        assertThrows(IllegalArgumentException.class, () -> new QuranTextRepresentation("search-1", "الحمد لله",
                TextRepresentationKind.SEARCH_NORMALIZED, "ARABIC", "norm-v1", null));
    }
    @Test void derivedRepresentationRetainsItsSourceLink() {
        var representation = new QuranTextRepresentation("search-1", "الحمد لله",
                TextRepresentationKind.SEARCH_NORMALIZED, "ARABIC", "norm-v1", "source-1");
        assertEquals("source-1", representation.sourceRepresentationId());
    }
    @Test void verseReferenceValidatesCanonicalCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> new VerseReference(115, 1));
        assertThrows(IllegalArgumentException.class, () -> new VerseReference(1, 0));
    }
}
