package tech.kayys.kallam.quran;
import java.util.Objects;
/** A source-edition verse text explicitly associated with a Qira'at transmission path. */
public record QuranVerseReadingVariant(QuranVerseText verseText, ReadingPath readingPath,
        ReadingVariantAttestation attestation) {
    public QuranVerseReadingVariant {
        Objects.requireNonNull(verseText, "verseText");
        Objects.requireNonNull(readingPath, "readingPath");
        Objects.requireNonNull(attestation, "attestation");
        if (!verseText.reference().equals(attestation.verseReference()))
            throw new IllegalArgumentException("attestation verse must match the verse text");
        if (!readingPath.equals(attestation.readingPath()))
            throw new IllegalArgumentException("attestation reading path must match the variant");
    }
}
