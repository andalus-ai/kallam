package tech.kayys.kallam.language.arabic.quran;
import java.util.Objects;
/** Metadata for a riwayah and its parent Qira'at identity. */
public record RiwayahDefinition(RiwayahId id, QiraatId qiraatId, String arabicName,
        String transliteration, String transmitterName, String authorityReference) {
    public RiwayahDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(qiraatId, "qiraatId");
        arabicName = required(arabicName, "arabicName");
        transliteration = required(transliteration, "transliteration");
        transmitterName = required(transmitterName, "transmitterName");
        authorityReference = optionalTrim(authorityReference);
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
    private static String optionalTrim(String value) { return value == null ? null : value.trim(); }
}
