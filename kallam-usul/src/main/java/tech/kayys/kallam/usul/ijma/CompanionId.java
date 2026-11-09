package tech.kayys.kallam.usul.ijma;
/** Stable reference to a Companion identity in a curated scholarly authority catalogue. */
public record CompanionId(String value) {
    public CompanionId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        value = value.trim();
    }
}
