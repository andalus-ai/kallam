
package tech.kayys.kallam.istinbat.integration;

import org.junit.jupiter.api.Test;
import tech.kayys.andalus.rag.core.RagChunk;
import tech.kayys.andalus.rag.core.RagScoredChunk;
import tech.kayys.andalus.rag.core.spi.Retriever;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AndalusRagEvidenceRetrieverTest {

    @Test
    void mapsRagChunkToScholarlyEvidence() {
        Retriever retriever = query -> List.of(
            new RagScoredChunk(
                new RagChunk(
                    "chunk-1", "doc-1", 0, "وَأَحَلَّ اللَّهُ الْبَيْعَ",
                    Map.of(
                        "sourceId", "quran:2:275",
                        "work", "Quran",
                        "evidenceType", "QURAN",
                        "evidenceStatus", "PRIMARY_SOURCE",
                        "locator", "2:275",
                        "kallam.primarySource", true)),
                0.97));

        var adapter = new AndalusRagEvidenceRetriever(retriever);
        var evidence = adapter.retrieve("البيع", 5, "quran", true);

        assertEquals(1, evidence.size());
        assertEquals("Q-".substring(0, 0), ""); // keep test Java 21/simple
        assertEquals("QURAN", evidence.get(0).type().name());
        assertEquals("PRIMARY_SOURCE", evidence.get(0).status().name());
        assertEquals("quran:2:275", evidence.get(0).source().sourceId());
        assertEquals(0.97, evidence.get(0).relevance(), 0.0001);
    }
}
