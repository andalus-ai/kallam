package tech.kayys.kallam.quran;
import java.util.Optional;
/** Read port for declared corpus sources and immutable import batches. */
public interface CorpusSourceRegistry {
    Optional<CorpusSourceDescriptor> findSource(CorpusSourceId sourceId);
    Optional<CorpusImportBatch> findBatch(String batchId);
}
