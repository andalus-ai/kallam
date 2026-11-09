package tech.kayys.kallam.language.arabic;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class ArabicTextTest {
    @Test void preservesTheOriginalTextExactly() {
        String input = "الْعَرَبِيَّةُ";
        assertEquals(input, new ArabicText(input).value());
    }
    @Test void rejectsBlankText() {
        assertThrows(IllegalArgumentException.class, () -> new ArabicText("  "));
    }
}
