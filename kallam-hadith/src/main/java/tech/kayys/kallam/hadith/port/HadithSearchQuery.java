package tech.kayys.kallam.hadith.port;

import java.util.Objects;

/** Provider-neutral search request; implementations may combine exact, lexical and semantic retrieval. */
public record HadithSearchQuery(
        String text,
        String language,
        SearchMode mode,
        int limit) {
    public HadithSearchQuery {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("text must not be blank");
        language = language == null || language.isBlank() ? "ar" : language.trim();
        Objects.requireNonNull(mode, "mode");
        if (limit < 1 || limit > 200) throw new IllegalArgumentException("limit must be between 1 and 200");
    }
    public enum SearchMode { EXACT, NORMALIZED, LEXICAL, SEMANTIC, HYBRID }
}
