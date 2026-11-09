package tech.kayys.kallam.language.arabic;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Deterministic retrieval normalization for Arabic text. This is a search-only
 * projection; callers must retain and display the original source text.
 */
public final class ArabicTextNormalizer {
    private ArabicTextNormalizer() {}

    public static String normalize(String input) {
        if (input == null || input.isBlank()) return "";
        String value = Normalizer.normalize(input, Normalizer.Form.NFKC)
                .replace("\u0640", "")
                .replaceAll("[\\u064B-\\u065F\\u0670\\u06D6-\\u06ED]", "")
                .replaceAll("[\\u0622\\u0623\\u0625\\u0671]", "ا")
                .replace('\u0649', '\u064A')
                .toLowerCase(Locale.ROOT);
        return value.replaceAll("[^\\p{IsArabic}\\p{IsLatin}\\p{N}]+", " ")
                .trim().replaceAll("\\s+", " ");
    }
}
