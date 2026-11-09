package tech.kayys.kallam.language.arabic.quran;
/** Half-open range measured in Unicode code points, never UTF-16 char offsets. */
public record CodePointRange(int startInclusive, int endExclusive) {
    public CodePointRange {
        if (startInclusive < 0) throw new IllegalArgumentException("startInclusive must not be negative");
        if (endExclusive <= startInclusive) throw new IllegalArgumentException("range must not be empty");
    }
    public boolean fits(String text) {
        return text != null && endExclusive <= text.codePointCount(0, text.length());
    }
}
