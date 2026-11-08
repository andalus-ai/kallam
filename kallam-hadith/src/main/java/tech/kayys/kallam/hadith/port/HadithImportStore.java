package tech.kayys.kallam.hadith.port;
import tech.kayys.kallam.hadith.application.HadithImportRequest;
/**
 * Persistence boundary. Implementations must commit batch metadata, collection/edition
 * metadata and every occurrence atomically, and enforce uniqueness under concurrent imports.
 */
public interface HadithImportStore {
 boolean hasImportedBatch(String batchId,String sourceChecksum);
 /** Atomic and idempotent; false means an identical batch already exists. */
 boolean persistAtomically(HadithImportRequest request);
}
