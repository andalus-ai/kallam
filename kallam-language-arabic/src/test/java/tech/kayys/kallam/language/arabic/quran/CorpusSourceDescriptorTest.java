package tech.kayys.kallam.language.arabic.quran;
import static org.junit.jupiter.api.Assertions.*;
import java.net.URI;
import java.time.Instant;
import org.junit.jupiter.api.Test;
class CorpusSourceDescriptorTest {
    @Test void sourceDescriptorCapturesEditionAndDeclaredTerms() {
        var descriptor = new CorpusSourceDescriptor(new CorpusSourceId("source.example"), "Example provider",
                "Example text edition", new EditionId("edition-1"), "2026.1",
                URI.create("https://example.org/corpus"), "CC-BY-4.0",
                URI.create("https://creativecommons.org/licenses/by/4.0/"), RedistributionPolicy.ATTRIBUTION_REQUIRED);
        assertEquals("edition-1", descriptor.editionId().value());
        assertEquals(RedistributionPolicy.ATTRIBUTION_REQUIRED, descriptor.redistributionPolicy());
    }
    @Test void scholarReviewRequiresReviewerAttribution() {
        assertThrows(IllegalArgumentException.class, () -> new CorpusImportBatch("batch-1",
                new CorpusSourceId("source-1"), "a".repeat(64), 1, Instant.parse("2026-01-01T00:00:00Z"),
                "importer-1", ImportVerificationStatus.SCHOLAR_REVIEWED, null));
    }
    @Test void digestMustBeSha256Hex() {
        assertThrows(IllegalArgumentException.class, () -> new CorpusImportBatch("batch-1",
                new CorpusSourceId("source-1"), "not-a-digest", 1, Instant.parse("2026-01-01T00:00:00Z"),
                "importer-1", ImportVerificationStatus.RECEIVED, null));
    }
}
