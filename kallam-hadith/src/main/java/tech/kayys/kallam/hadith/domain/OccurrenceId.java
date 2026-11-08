package tech.kayys.kallam.hadith.domain;

/** Identifier for one appearance of a narration in a particular source edition. */
public record OccurrenceId(String value) {
    public OccurrenceId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        value = value.trim();
    }
}
