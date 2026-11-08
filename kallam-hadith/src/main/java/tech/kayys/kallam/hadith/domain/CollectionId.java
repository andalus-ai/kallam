package tech.kayys.kallam.hadith.domain;

/** Stable identifier for a compiled Hadith collection. */
public record CollectionId(String value) {
    public CollectionId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        value = value.trim();
    }
}
