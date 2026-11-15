package tech.kayys.kallam.language.arabic.morphology;
import java.util.List;
import tech.kayys.kallam.language.arabic.analysis.AnalysisProvenance;
/** One candidate analysis; ambiguity is represented by multiple instances. */
public record MorphologicalAnalysis(String id,String tokenId,String lemma,String root,String pattern,String partOfSpeech,List<String> morphemes,List<String> features,double confidence,AnalysisProvenance provenance) {
 public MorphologicalAnalysis {if(blank(id)||blank(tokenId)||blank(partOfSpeech)||provenance==null)throw new IllegalArgumentException("analysis identity and provenance are required");lemma=empty(lemma);root=empty(root);pattern=empty(pattern);morphemes=morphemes==null?List.of():List.copyOf(morphemes);features=features==null?List.of():List.copyOf(features);if(!Double.isFinite(confidence)||confidence<0||confidence>1)throw new IllegalArgumentException("confidence must be in [0,1]");}
 private static boolean blank(String s){return s==null||s.isBlank();} private static String empty(String s){return s==null?"":s;}
}