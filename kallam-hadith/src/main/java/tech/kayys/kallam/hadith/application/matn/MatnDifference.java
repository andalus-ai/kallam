package tech.kayys.kallam.hadith.application.matn;

/** A token-level difference between a baseline Matn and one source occurrence. */
public record MatnDifference(Operation operation, int baselineTokenIndex, int variantTokenIndex,
                             String baselineToken, String variantToken) {
    public enum Operation { INSERTION, DELETION, SUBSTITUTION }

    public MatnDifference {
        if (operation == null) throw new IllegalArgumentException("operation must not be null");
        if (baselineTokenIndex < 0 || variantTokenIndex < 0) {
            throw new IllegalArgumentException("token indexes must not be negative");
        }
    }
}
