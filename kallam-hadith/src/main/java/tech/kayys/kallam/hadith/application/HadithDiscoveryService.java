package tech.kayys.kallam.hadith.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tech.kayys.kallam.hadith.domain.HadithOccurrence;
import tech.kayys.kallam.hadith.port.HadithCorpus;
import tech.kayys.kallam.hadith.port.HadithSearch;
import tech.kayys.kallam.hadith.port.HadithSearchQuery;

/**
 * Coordinates retrieval and source resolution. A retrieval hit is never promoted
 * to a verified narration identity or authenticity judgment.
 */
public final class HadithDiscoveryService {
    private final HadithSearch search;
    private final HadithCorpus corpus;

    public HadithDiscoveryService(HadithSearch search, HadithCorpus corpus) {
        this.search = Objects.requireNonNull(search, "search");
        this.corpus = Objects.requireNonNull(corpus, "corpus");
    }

    public List<HadithDiscoveryResult> discover(HadithSearchQuery query) {
        Objects.requireNonNull(query, "query");
        var results = new ArrayList<HadithDiscoveryResult>();
        for (var hit : search.search(query)) {
            var occurrence = corpus.findOccurrence(hit.occurrenceId()).orElse(null);
            if (occurrence == null) continue; // stale index hit; never fabricate a source record

            var collection = corpus.findCollection(occurrence.collectionId()).orElse(null);
            var edition = corpus.findEdition(occurrence.editionId()).orElse(null);
            if (collection == null || edition == null) continue; // incomplete source linkage

            results.add(new HadithDiscoveryResult(occurrence, collection, edition, hit));
        }
        return List.copyOf(results);
    }
}
