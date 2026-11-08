package tech.kayys.kallam.hadith.domain;

/** Stable identity for a scholarly narration concept; not a collection number. */
public record HadithId(String value) {
    public HadithId {
        value = requireText(value, "value");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
