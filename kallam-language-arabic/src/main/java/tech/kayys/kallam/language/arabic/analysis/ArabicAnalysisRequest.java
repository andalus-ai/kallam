package tech.kayys.kallam.language.arabic.analysis;
import java.util.Set;
import tech.kayys.kallam.language.arabic.text.ArabicDocument;
public record ArabicAnalysisRequest(ArabicDocument document,Set<Task> tasks,String profileId) {
 public ArabicAnalysisRequest {if(document==null)throw new IllegalArgumentException("document required");tasks=tasks==null?Set.of():Set.copyOf(tasks);if(tasks.isEmpty())throw new IllegalArgumentException("at least one task required");profileId=profileId==null?"default":profileId;}
 public enum Task {TOKENIZATION,MORPHOLOGY,SYNTAX,I3RAB,LEXICAL_LOOKUP,SEMANTICS,BALAGHAH}
}