package tech.kayys.kallam.language.arabic.analysis;
/** Provider trace; confidence scores must not be interpreted as scholarly certainty. */
public record AnalysisProvenance(String providerId,String providerVersion,String method,String modelOrRuleSet) {
 public AnalysisProvenance {if(blank(providerId)||blank(providerVersion)||blank(method))throw new IllegalArgumentException("provider provenance is required");modelOrRuleSet=modelOrRuleSet==null?"":modelOrRuleSet;}
 private static boolean blank(String s){return s==null||s.isBlank();}
}