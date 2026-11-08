package tech.kayys.kallam.language.arabic.quran;

import java.util.Objects;

/** Edition-aware verse text. The source representation is preserved verbatim. */
public record QuranVerseText(
        VerseReference reference,
        EditionId editionId,
        QuranTextRepresentation sourceRepresentation,
        String sourceLocator,
        String corpusVersion,
        String license) {
    public QuranVerseText {
        Objects.requireNonNull(reference, "reference");
        Objects.requireNonNull(editionId, "editionId");
        Objects.requireNonNull(sourceRepresentation, "sourceRepresentation");
        if (sourceRepresentation.kind() != TextRepresentationKind.SOURCE_TRANSCRIPTION
                && sourceRepresentation.kind() != TextRepresentationKind.UTHMANI_ORTHOGRAPHY) {
            throw new IllegalArgumentException("verse source must be source transcription or Uthmani orthography");
        }
        sourceLocator = optionalTrim(sourceLocator);
        corpusVersion = optionalTrim(corpusVersion);
        license = optionalTrim(license);
    }
    private static String optionalTrim(String value) { return value == null ? null : value.trim(); }
}
