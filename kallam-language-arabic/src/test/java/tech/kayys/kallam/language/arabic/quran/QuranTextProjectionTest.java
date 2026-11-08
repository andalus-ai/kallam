package tech.kayys.kallam.language.arabic.quran;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
class QuranTextProjectionTest {
    @Test void alignmentUsesCodePointsAndPreservesArabicSource() {
        String sourceText = "بِسْمِ", derivedText = "بسم";
        var source = new QuranTextRepresentation("source-1", sourceText, TextRepresentationKind.SOURCE_TRANSCRIPTION,
                "ARABIC_QURANIC", null, null);
        var derived = new QuranTextRepresentation("search-1", derivedText, TextRepresentationKind.SEARCH_NORMALIZED,
                "ARABIC_SEARCH", "search-v1", "source-1");
        var projection = new QuranTextProjection("search-v1", source, derived,
                UnicodeNormalizationPolicy.SEARCH_PROFILE_DEFINED, List.of(
                new TextAlignmentSegment(new CodePointRange(0, 1), new CodePointRange(0, 1)),
                new TextAlignmentSegment(new CodePointRange(2, 3), new CodePointRange(1, 2)),
                new TextAlignmentSegment(new CodePointRange(4, 5), new CodePointRange(2, 3))));
        assertEquals(sourceText, projection.source().text());
        assertEquals(6, sourceText.codePointCount(0, sourceText.length()));
        assertEquals(3, derivedText.codePointCount(0, derivedText.length()));
    }
    @Test void rejectsAlignmentOutsideSourceCodePointLength() {
        var source = new QuranTextRepresentation("source-1", "بِ", TextRepresentationKind.SOURCE_TRANSCRIPTION,
                "ARABIC_QURANIC", null, null);
        var derived = new QuranTextRepresentation("search-1", "ب", TextRepresentationKind.SEARCH_NORMALIZED,
                "ARABIC_SEARCH", "search-v1", "source-1");
        assertThrows(IllegalArgumentException.class, () -> new QuranTextProjection("search-v1", source, derived,
                UnicodeNormalizationPolicy.SEARCH_PROFILE_DEFINED,
                List.of(new TextAlignmentSegment(new CodePointRange(0, 3), new CodePointRange(0, 1)))));
    }
    @Test void rejectsGapsInDerivedAlignment() {
        var source = new QuranTextRepresentation("source-1", "abc", TextRepresentationKind.SOURCE_TRANSCRIPTION,
                "ARABIC_QURANIC", null, null);
        var derived = new QuranTextRepresentation("search-1", "abc", TextRepresentationKind.SEARCH_NORMALIZED,
                "ARABIC_SEARCH", "search-v1", "source-1");
        assertThrows(IllegalArgumentException.class, () -> new QuranTextProjection("search-v1", source, derived,
                UnicodeNormalizationPolicy.PRESERVE_CODE_POINTS,
                List.of(new TextAlignmentSegment(new CodePointRange(0, 1), new CodePointRange(0, 1)),
                        new TextAlignmentSegment(new CodePointRange(2, 3), new CodePointRange(2, 3)))));
    }
    @Test void codePointRangeDoesNotUseUtf16Offsets() {
        String text = "𞸀ب";
        assertEquals(2, text.codePointCount(0, text.length()));
        assertTrue(new CodePointRange(0, 2).fits(text));
        assertFalse(new CodePointRange(0, 3).fits(text));
    }
}
