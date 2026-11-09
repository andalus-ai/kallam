package tech.kayys.kallam.language.arabic.quran;

/** Stable identifier for a specific Qur'anic text edition or digital source. */
public record EditionId(String value) {
    public EditionId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        value = value.trim();
    }
}
