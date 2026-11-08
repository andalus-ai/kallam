package tech.kayys.kallam.hadith.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ArabicTextNormalizerTest {
    @Test void removesDiacriticsAndNormalizesAlefAndTatweel() {
        assertEquals("انما الاعمال بالنيات", ArabicTextNormalizer.normalize("إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ"));
        assertEquals("السلام عليكم", ArabicTextNormalizer.normalize("السَّلَامُ عَلَيْكُمْ"));
        assertEquals("قال الله", ArabicTextNormalizer.normalize("قــالَ اللهُ"));
    }
}
