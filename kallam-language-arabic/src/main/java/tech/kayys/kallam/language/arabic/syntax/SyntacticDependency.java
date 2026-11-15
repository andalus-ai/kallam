package tech.kayys.kallam.language.arabic.syntax;
import tech.kayys.kallam.language.arabic.analysis.AnalysisProvenance;
/** Directed head-to-dependent relation; null head denotes a sentence root. */
public record SyntacticDependency(String id,String headTokenId,String dependentTokenId,String relation,String framework,AnalysisProvenance provenance) {
 public SyntacticDependency {if(blank(id)||blank(dependentTokenId)||blank(relation)||blank(framework)||provenance==null||dependentTokenId.equals(headTokenId))throw new IllegalArgumentException("invalid dependency");}
 private static boolean blank(String s){return s==null||s.isBlank();}
}