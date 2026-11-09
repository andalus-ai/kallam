package tech.kayys.kallam.hadith.application.isnad;
import java.util.List;
import tech.kayys.kallam.hadith.domain.isnad.*;
/** Inspectable findings; diagnostics do not constitute an automatic continuity or authenticity ruling. */
public record IsnadResearchReport(IsnadChain chain, List<NarratorResearch> narratorResearch, List<IsnadLink> unresolvedLinks,
 List<Integer> missingPositions, List<Integer> ambiguousPositions, List<Integer> unassessedPositions) {
 public IsnadResearchReport {
  if(chain==null)throw new IllegalArgumentException("chain must not be null");
  narratorResearch=List.copyOf(narratorResearch==null?List.of():narratorResearch);
  unresolvedLinks=List.copyOf(unresolvedLinks==null?List.of():unresolvedLinks);
  missingPositions=List.copyOf(missingPositions==null?List.of():missingPositions);
  ambiguousPositions=List.copyOf(ambiguousPositions==null?List.of():ambiguousPositions);
  unassessedPositions=List.copyOf(unassessedPositions==null?List.of():unassessedPositions);
 }
}