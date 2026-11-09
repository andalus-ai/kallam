package tech.kayys.kallam.quran;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class QuranVerseReadingVariantTest {
    private ReadingPath path() {
        return new ReadingPath(new QiraatId("qiraat.example"), new RiwayahId("riwayah.example"), new TariqId("tariq.example"));
    }
    private QuranVerseText verse() {
        return new QuranVerseText(new VerseReference(1, 1), new EditionId("edition.example"),
                new QuranTextRepresentation("edition.example:1:1", "بِسْمِ", TextRepresentationKind.SOURCE_TRANSCRIPTION,
                        "ARABIC_QURANIC", null, null), "verse/1/1", "release-1", null);
    }
    @Test void variantRequiresMatchingVerseAndReadingAttestation() {
        var path = path();
        var attestation = new ReadingVariantAttestation("attestation-1", new VerseReference(1, 1), path,
                new CorpusSourceId("source.example"), "batch-1", "verse/1/1",
                ReadingAttestationStatus.SOURCE_ATTESTED, null);
        var variant = new QuranVerseReadingVariant(verse(), path, attestation);
        assertEquals(new VerseReference(1, 1), variant.verseText().reference());
        assertEquals(path, variant.readingPath());
    }
    @Test void mismatchedVerseAttestationIsRejected() {
        var path = path();
        var attestation = new ReadingVariantAttestation("attestation-1", new VerseReference(1, 2), path,
                new CorpusSourceId("source.example"), "batch-1", "verse/1/2",
                ReadingAttestationStatus.SOURCE_ATTESTED, null);
        assertThrows(IllegalArgumentException.class, () -> new QuranVerseReadingVariant(verse(), path, attestation));
    }
    @Test void mismatchedReadingPathAttestationIsRejected() {
        var path = path();
        var other = new ReadingPath(new QiraatId("other.qiraat"), new RiwayahId("other.riwayah"), null);
        var attestation = new ReadingVariantAttestation("attestation-1", new VerseReference(1, 1), other,
                new CorpusSourceId("source.example"), "batch-1", "verse/1/1",
                ReadingAttestationStatus.SOURCE_ATTESTED, null);
        assertThrows(IllegalArgumentException.class, () -> new QuranVerseReadingVariant(verse(), path, attestation));
    }
    @Test void scholarlyReviewRequiresReviewerReference() {
        assertThrows(IllegalArgumentException.class, () -> new ReadingVariantAttestation("attestation-1",
                new VerseReference(1, 1), path(), new CorpusSourceId("source.example"), "batch-1",
                "verse/1/1", ReadingAttestationStatus.SCHOLAR_REVIEWED, null));
    }
}
