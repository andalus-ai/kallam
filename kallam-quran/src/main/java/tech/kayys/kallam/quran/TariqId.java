package tech.kayys.kallam.quran;
/** Stable identifier for a transmission route within a riwayah. */
public record TariqId(String value) {
    public TariqId { value = required(value); }
    private static String required(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
        return value.trim();
    }
}
