package tech.kayys.kallam.quran;
/** Stable identifier for a riwayah, distinct from its parent Qira'at. */
public record RiwayahId(String value) {
    public RiwayahId { value = required(value); }
    private static String required(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        return value.trim();
    }
}
