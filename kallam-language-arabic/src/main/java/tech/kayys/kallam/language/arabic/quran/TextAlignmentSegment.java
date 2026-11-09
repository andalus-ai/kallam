package tech.kayys.kallam.language.arabic.quran;
import java.util.Objects;
/** Monotonic source-to-derived alignment for a transformed text span. */
public record TextAlignmentSegment(CodePointRange sourceRange, CodePointRange derivedRange) {
    public TextAlignmentSegment {
        Objects.requireNonNull(sourceRange, "sourceRange");
        Objects.requireNonNull(derivedRange, "derivedRange");
    }
}
