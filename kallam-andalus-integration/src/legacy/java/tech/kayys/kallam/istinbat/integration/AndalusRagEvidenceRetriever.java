package tech.kayys.kallam.istinbat.integration;

import tech.kayys.kallam.istinbat.Evidence;
import tech.kayys.kallam.istinbat.EvidenceStatus;
import tech.kayys.kallam.istinbat.EvidenceType;
import tech.kayys.kallam.istinbat.SourceReference;
import tech.kayys.andalus.rag.core.RagQuery;
import tech.kayys.andalus.rag.core.RagScoredChunk;
import tech.kayys.andalus.rag.core.spi.Retriever;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Thin anti-corruption adapter between Andalus RAG and Kallam's evidence model.
 *
 * The RAG runtime remains responsible for retrieval. Kallam is responsible for
 * epistemic classification and scholarly provenance.
 */
public final class AndalusRagEvidenceRetriever implements EvidenceRetriever {

    private final Retriever retriever;

    public AndalusRagEvidenceRetriever(Retriever retriever) {
        this.retriever = java.util.Objects.requireNonNull(retriever);
    }

    @Override
    public List<Evidence> retrieve(
            String query, int topK, String specialist, boolean primarySourceOnly) {

        if (query == null || query.isBlank() || topK <= 0) return List.of();

        Map<String, Object> filters = primarySourceOnly
                ? Map.of("kallam.primarySource", true)
                : Map.of();

        List<RagScoredChunk> hits = retriever.retrieve(
                new RagQuery(query, topK, 0.0, filters));

        var result = new ArrayList<Evidence>(hits.size());

        for (RagScoredChunk hit : hits) {
            if (hit == null || hit.chunk() == null) continue;

            var chunk = hit.chunk();
            var md = chunk.metadata();

            String sourceId = string(md, "sourceId", chunk.documentId());
            String work = string(md, "work", string(md, "title", chunk.documentId()));
            String author = string(md, "author", null);
            String edition = string(md, "edition", null);
            String volume = string(md, "volume", null);
            String page = string(md, "page", null);
            String chapter = string(md, "chapter", null);
            String locator = string(md, "locator", "chunk:" + chunk.chunkIndex());

            EvidenceType type = mapType(specialist, md);
            EvidenceStatus status = primarySourceOnly
                    ? EvidenceStatus.PRIMARY_SOURCE
                    : mapStatus(md);

            var source = new SourceReference(
                    sourceId, work, author, edition, volume, page, chapter, locator,
                    Map.of("documentId", chunk.documentId(),
                           "chunkId", chunk.id()));

            result.add(new Evidence(
                    "E-" + chunk.id(),
                    type,
                    chunk.text(),
                    string(md, "translation", null),
                    source,
                    status,
                    clamp(hit.score()),
                    List.of(),
                    List.of(),
                    Map.of(
                        "retriever", "andalus-rag",
                        "specialist", specialist,
                        "documentId", chunk.documentId(),
                        "chunkIndex", Integer.toString(chunk.chunkIndex())
                    )));
        }

        return List.copyOf(result);
    }

    private static EvidenceType mapType(String specialist, Map<String, Object> md) {
        String explicit = string(md, "evidenceType", null);
        if (explicit != null) {
            try { return EvidenceType.valueOf(explicit.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}
        }
        return switch (specialist.toLowerCase()) {
            case "quran" -> EvidenceType.QURAN;
            case "hadith" -> EvidenceType.HADITH;
            case "tafsir" -> EvidenceType.TAFSIR;
            case "fiqh", "madhhab" -> EvidenceType.FIQH;
            case "usul" -> EvidenceType.USUL;
            default -> EvidenceType.CONTEMPORARY_RESEARCH;
        };
    }

    private static EvidenceStatus mapStatus(Map<String, Object> md) {
        String status = string(md, "evidenceStatus", null);
        if (status != null) {
            try { return EvidenceStatus.valueOf(status.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}
        }
        return EvidenceStatus.UNVERIFIED;
    }

    private static String string(Map<String, Object> md, String key, String fallback) {
        Object value = md == null ? null : md.get(key);
        return value == null ? fallback : String.valueOf(value);
    }

    private static double clamp(double score) {
        return Math.max(0.0, Math.min(1.0, score));
    }
}
