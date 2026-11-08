package tech.kayys.kallam.hadith.port;
import tech.kayys.kallam.hadith.application.HadithImportRequest;
/**
 * Persistence boundary. Implementations must commit batch metadata, collection/edition
 * metadata and every occurrence atomically, and enforce uniqueness under concurrent imports.
 */
public interface HadithImportStore {
 enum WriteResult { STORED, ALREADY_PRESENT, CHECKSUM_CONFLICT }
 /**
  * Atomically inserts the batch. A matching batch ID and checksum is ALREADY_PRESENT;
  * a matching ID with a different checksum is CHECKSUM_CONFLICT. Implementations must
  * enforce this result under concurrent writers, preferably with a unique database constraint.
  */
 WriteResult persistAtomically(HadithImportRequest request);
}
