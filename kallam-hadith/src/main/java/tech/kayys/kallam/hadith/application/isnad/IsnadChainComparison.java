package tech.kayys.kallam.hadith.application.isnad;
import java.util.List;
import tech.kayys.kallam.hadith.domain.OccurrenceId;
public record IsnadChainComparison(OccurrenceId firstOccurrenceId, OccurrenceId secondOccurrenceId, List<SharedNarrator> sharedNarrators) {
 public IsnadChainComparison { if(firstOccurrenceId==null||secondOccurrenceId==null)throw new IllegalArgumentException("occurrence identifiers are required"); sharedNarrators=List.copyOf(sharedNarrators==null?List.of():sharedNarrators); }
}