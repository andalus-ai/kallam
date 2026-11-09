package tech.kayys.kallam.language.arabic.i3rab;
import java.util.List;
import tech.kayys.kallam.language.arabic.analysis.AnalysisProvenance;
/** Evidence-linked I'rab; labels remain open vocabulary for differing grammatical traditions. */
public record I3rabAnnotation(String id,String tokenId,String grammaticalRole,String framework,String caseOrMood,String governingElement,List<String> evidenceReferences,double confidence,AnalysisProvenance provenance) {
 public I3rabAnnotation {if(blank(id)||blank(tokenId)||blank(grammaticalRole)||blank(framework)||provenance==null)throw new IllegalArgumentException("I'rab identity and provenance required");caseOrMood=empty(caseOrMood);governingElement=empty(governingElement);evidenceReferences=evidenceReferences==null?List.of():List.copyOf(evidenceReferences);if(evidenceReferences.stream().anyMatch(s->s==null||s.isBlank()))throw new IllegalArgumentException("blank evidence reference");if(!Double.isFinite(confidence)||confidence<0||confidence>1)throw new IllegalArgumentException("confidence must be in [0,1]");}
 private static boolean blank(String s){return s==null||s.isBlank();}private static String empty(String s){return s==null?"":s;}
}