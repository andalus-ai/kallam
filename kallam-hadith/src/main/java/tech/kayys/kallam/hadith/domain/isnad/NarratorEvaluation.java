package tech.kayys.kallam.hadith.domain.isnad;
import java.time.Instant;
/** Attributed narrator evaluation; source wording and disagreement are preserved. */
public record NarratorEvaluation(String evaluationId, NarratorId narratorId, String evaluator, String sourceReference,
 String judgmentText, String methodology, Instant evaluatedAt, String notes) {
 public NarratorEvaluation {
  require(evaluationId,"evaluationId"); if(narratorId==null) throw new IllegalArgumentException("narratorId must not be null");
  require(evaluator,"evaluator"); require(sourceReference,"sourceReference"); require(judgmentText,"judgmentText");
  evaluationId=evaluationId.trim(); evaluator=evaluator.trim(); sourceReference=sourceReference.trim(); judgmentText=judgmentText.trim();
  methodology=clean(methodology); notes=clean(notes);
 }
 private static void require(String v,String f){if(v==null||v.isBlank())throw new IllegalArgumentException(f+" must not be blank");}
 private static String clean(String v){return v==null?null:v.trim();}
}