package tech.kayys.kallam.language.arabic.quran;
import java.util.Objects;
/** Metadata for a tariq route within one riwayah. */
public record TariqDefinition(TariqId id, RiwayahId riwayahId, String arabicName,
        String transliteration, String authorityReference) {
    public TariqDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(riwayahId, "riwayahId");
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
