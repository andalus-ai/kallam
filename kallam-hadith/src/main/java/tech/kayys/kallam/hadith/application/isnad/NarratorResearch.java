package tech.kayys.kallam.hadith.application.isnad;
import java.util.List;
import tech.kayys.kallam.hadith.domain.isnad.*;
public record NarratorResearch(IsnadLink link, Narrator narrator, List<NarratorEvaluation> evaluations) {
 public NarratorResearch { if(link==null||narrator==null)throw new IllegalArgumentException("link and narrator are required"); evaluations=List.copyOf(evaluations==null?List.of():evaluations); }
}