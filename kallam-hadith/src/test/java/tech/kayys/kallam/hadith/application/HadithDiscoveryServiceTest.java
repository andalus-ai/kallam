package tech.kayys.kallam.hadith.application;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.hadith.domain.*;
import tech.kayys.kallam.hadith.port.*;

class HadithDiscoveryServiceTest {
    @Test
    void resolvesSearchHitsToEditionAwareSourceRecords() {
        var collectionId = new CollectionId("collection");
        var editionId = new EditionId("edition");
        var occurrenceId = new OccurrenceId("occurrence");
        var occurrence = new HadithOccurrence(occurrenceId, new HadithId("hadith"),
                collectionId, editionId, "Book 1", "Chapter 1", "1", null, null,
                "النص العربي", null, "ar", "Book 1 / Chapter 1 / 1", "batch-1");
        HadithCorpus corpus = new HadithCorpus() {
            public Optional<HadithCollection> findCollection(CollectionId id) {
                return Optional.of(new HadithCollection(collectionId, "Collection", "Compiler", "ar"));
            }
            public Optional<HadithEdition> findEdition(EditionId id) {
                return Optional.of(new HadithEdition(editionId, collectionId, "Publisher",
                        "Edition 1", "2020", null, "license", "v1"));
            }
            public Optional<HadithOccurrence> findOccurrence(OccurrenceId id) {
                return Optional.of(occurrence);
            }
            public List<HadithOccurrence> findOccurrences(HadithId id) { return List.of(occurrence); }
        };
        HadithSearch search = query -> List.of(new HadithSearchHit(occurrenceId, 0.93, "EXACT", "Exact phrase"));

        var results = new HadithDiscoveryService(search, corpus)
                .discover(new HadithSearchQuery("النص العربي", "ar",
                        HadithSearchQuery.SearchMode.EXACT, 10));

        assertEquals(1, results.size());
        assertEquals("2020", results.getFirst().edition().publicationYear());
        assertEquals("1", results.getFirst().occurrence().sourceNumber());
        assertEquals(0.93, results.getFirst().match().score());
    }

    @Test
    void discardsStaleIndexHitsInsteadOfInventingSources() {
        HadithCorpus emptyCorpus = new HadithCorpus() {
            public Optional<HadithCollection> findCollection(CollectionId id) { return Optional.empty(); }
            public Optional<HadithEdition> findEdition(EditionId id) { return Optional.empty(); }
            public Optional<HadithOccurrence> findOccurrence(OccurrenceId id) { return Optional.empty(); }
            public List<HadithOccurrence> findOccurrences(HadithId id) { return List.of(); }
        };
        HadithSearch search = query -> List.of(
                new HadithSearchHit(new OccurrenceId("stale"), 0.7, "SEMANTIC", "Candidate"));

        var results = new HadithDiscoveryService(search, emptyCorpus)
                .discover(new HadithSearchQuery("prayer", "en",
                        HadithSearchQuery.SearchMode.HYBRID, 5));

        assertTrue(results.isEmpty());
    }
}
