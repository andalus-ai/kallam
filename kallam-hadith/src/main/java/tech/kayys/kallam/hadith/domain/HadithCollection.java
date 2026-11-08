package tech.kayys.kallam.hadith.domain;

import java.util.Objects;

/** Bibliographic identity of a Hadith work, independent of any edition. */
public record HadithCollection(
        CollectionId id,
        String title,
        String compiler,
        String originalLanguage) {
    public HadithCollection {
        Objects.requireNonNull(id, "id");
        title = required(title, "title");
        compiler = optionalTrim(compiler);
        originalLanguage = optionalTrim(originalLanguage);
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optionalTrim(String value) { return value == null ? null : value.trim(); }
}
