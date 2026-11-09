package tech.kayys.kallam.quran;

/** Describes why a text representation exists; representations are never interchangeable by default. */
public enum TextRepresentationKind {
    SOURCE_TRANSCRIPTION,
    UTHMANI_ORTHOGRAPHY,
    READING_SPECIFIC,
    DISPLAY,
    SEARCH_NORMALIZED,
    ANALYSIS
}
