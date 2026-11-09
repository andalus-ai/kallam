package tech.kayys.kallam.language.arabic.quran;
import java.util.Objects;
/** Metadata for a Qira'at identity. Names are descriptive labels, not authority assertions. */
public record QiraatDefinition(QiraatId id, String arabicName, String transliteration, String authorityReference) {
    public QiraatDefinition {
        Objects.requireNonNull(id, "id");
        arabicName = required(arabicName, "arabicName");
        transliteration = required(transliteration, "transliteration");
        authorityReference = optionalTrim(authorityReference);
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optionalTrim(String value) { return value == null ? null : value.trim(); }
}
