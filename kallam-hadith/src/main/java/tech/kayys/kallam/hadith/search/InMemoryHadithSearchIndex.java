package tech.kayys.kallam.hadith.search;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import tech.kayys.kallam.language.arabic.ArabicTextNormalizer;
import tech.kayys.kallam.hadith.domain.HadithOccurrence;
import tech.kayys.kallam.hadith.domain.OccurrenceId;
import tech.kayys.kallam.hadith.port.HadithSearch;
import tech.kayys.kallam.hadith.port.HadithSearchHit;
import tech.kayys.kallam.hadith.port.HadithSearchQuery;

/**
 * Small deterministic search adapter for development, tests and embedded use.
 * It is rebuildable and intentionally not a durable corpus store or semantic engine.
 */
public final class InMemoryHadithSearchIndex implements HadithSearch {
    private final Map<OccurrenceId, IndexedOccurrence> documents = new ConcurrentHashMap<>();

    /** Indexes a source occurrence while keeping its original text separate from projections. */
    public void index(HadithOccurrence occurrence) {
        Objects.requireNonNull(occurrence, "occurrence");
        documents.put(occurrence.id(), new IndexedOccurrence(
                occurrence.id(), occurrence.language(),
                occurrence.originalArabicMatn(),
                occurrence.originalIsnadText(),
                ArabicTextNormalizer.normalize(occurrence.originalArabicMatn()),
                ArabicTextNormalizer.normalize(occurrence.originalIsnadText())));
    }

    public boolean remove(OccurrenceId id) {
        return documents.remove(Objects.requireNonNull(id, "id")) != null;
    }

    public int size() { return documents.size(); }

    @Override
    public List<HadithSearchHit> search(HadithSearchQuery query) {
        Objects.requireNonNull(query, "query");
        if (query.mode() == HadithSearchQuery.SearchMode.SEMANTIC) {
            throw new UnsupportedOperationException("Semantic search requires an explicitly configured embedding adapter.");
        }
        String normalizedQuery = ArabicTextNormalizer.normalize(query.text());
        Set<String> queryTokens = tokens(normalizedQuery);
        if (normalizedQuery.isEmpty()) return List.of();

        return documents.values().stream()
                .filter(d -> d.language().equalsIgnoreCase(query.language()))
                .map(d -> score(d, query, normalizedQuery, queryTokens))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(HadithSearchHit::score).reversed()
                        .thenComparing(h -> h.occurrenceId().value()))
                .limit(query.limit())
                .toList();
    }

    private HadithSearchHit score(IndexedOccurrence d, HadithSearchQuery query,
                                  String normalizedQuery, Set<String> queryTokens) {
        boolean exact = Objects.equals(d.originalMatn(), query.text()) || Objects.equals(d.originalIsnad(), query.text());
        boolean normalized = d.normalizedMatn().equals(normalizedQuery)
                || d.normalizedIsnad().equals(normalizedQuery);
        Set<String> docTokens = tokens(d.normalizedMatn() + " " + d.normalizedIsnad());
        long matched = queryTokens.stream().filter(docTokens::contains).count();
        double lexical = queryTokens.isEmpty() ? 0.0 : (double) matched / queryTokens.size();

        return switch (query.mode()) {
            case EXACT -> exact ? hit(d, 1.0, "EXACT", "Original source text matched exactly.") : null;
            case NORMALIZED -> normalized ? hit(d, 0.98, "NORMALIZED", "Matched after deterministic Arabic normalization.") : null;
            case LEXICAL -> lexical > 0 ? hit(d, lexical, "LEXICAL", matched + " of " + queryTokens.size() + " query terms matched.") : null;
            case HYBRID -> {
                double score = exact ? 1.0 : normalized ? 0.98 : lexical * 0.75;
                if (score <= 0) yield null;
                String type = exact ? "EXACT" : normalized ? "NORMALIZED" : "LEXICAL";
                yield hit(d, score, type, "Deterministic hybrid retrieval; not a scholarly grade.");
            }
            case SEMANTIC -> throw new UnsupportedOperationException("Semantic search is not provided by this adapter.");
        };
    }

    private static HadithSearchHit hit(IndexedOccurrence d, double score, String type, String explanation) {
        return new HadithSearchHit(d.id(), score, type, explanation);
    }

    private static Set<String> tokens(String text) {
        if (text == null || text.isBlank()) return Set.of();
        return Arrays.stream(text.split(" "))
                .filter(token -> !token.isBlank())
                .collect(Collectors.toUnmodifiableSet());
    }

    private record IndexedOccurrence(OccurrenceId id, String language, String originalMatn,
                                     String originalIsnad, String normalizedMatn, String normalizedIsnad) {}
}
