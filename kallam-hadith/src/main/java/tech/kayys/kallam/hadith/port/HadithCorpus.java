package tech.kayys.kallam.hadith.port;

import java.util.List;
import java.util.Optional;
import tech.kayys.kallam.hadith.domain.CollectionId;
import tech.kayys.kallam.hadith.domain.EditionId;
import tech.kayys.kallam.hadith.domain.HadithCollection;
import tech.kayys.kallam.hadith.domain.HadithEdition;
import tech.kayys.kallam.hadith.domain.HadithId;
import tech.kayys.kallam.hadith.domain.HadithOccurrence;
import tech.kayys.kallam.hadith.domain.OccurrenceId;

/** Read port for source-attested corpus records. Implementations own storage and indexing. */
public interface HadithCorpus {
    Optional<HadithCollection> findCollection(CollectionId id);
    Optional<HadithEdition> findEdition(EditionId id);
    Optional<HadithOccurrence> findOccurrence(OccurrenceId id);
    List<HadithOccurrence> findOccurrences(HadithId hadithId);
}
