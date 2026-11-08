package tech.kayys.kallam.language.arabic.quran;
/** Stable identifier for a Qira'at tradition; it carries no implicit scholarly ranking. */
public record QiraatId(String value) {
    public QiraatId { value = required(value); }
    private static String required(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        return value.trim();
    }
}
