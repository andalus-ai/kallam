package tech.kayys.kallam.language.arabic;

/** Immutable Arabic-language input. The original string is retained exactly as supplied. */
public record ArabicText(String value) {
    public ArabicText {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("value must not be blank");
    }
}
