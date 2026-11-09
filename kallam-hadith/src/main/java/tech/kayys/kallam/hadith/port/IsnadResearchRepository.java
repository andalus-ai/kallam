package tech.kayys.kallam.hadith.port;
import java.util.List;
import java.util.Optional;
import tech.kayys.kallam.hadith.domain.OccurrenceId;
import tech.kayys.kallam.hadith.domain.isnad.*;
/** Read port for source-linked chains, narrator authorities and attributed evaluations. */
public interface IsnadResearchRepository {
 Optional<IsnadChain> findChain(OccurrenceId occurrenceId);
 Optional<Narrator> findNarrator(NarratorId narratorId);
 List<NarratorEvaluation> findEvaluations(NarratorId narratorId);
}