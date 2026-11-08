package tech.kayys.kallam.hadith.domain;

/** Identifier for a specific published or digitized edition. */
public record EditionId(String value) {
    public EditionId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        value = value.trim();
    }
}
