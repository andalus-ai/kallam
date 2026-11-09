package tech.kayys.kallam.language.arabic.analysis;
import java.util.List;
import tech.kayys.kallam.language.arabic.text.ArabicToken;
import tech.kayys.kallam.language.arabic.morphology.MorphologicalAnalysis;
import tech.kayys.kallam.language.arabic.syntax.SyntacticDependency;
import tech.kayys.kallam.language.arabic.i3rab.I3rabAnnotation;
/** Immutable multi-candidate output. No candidate is implicitly authoritative. */
public record ArabicAnalysisResult(String documentId,List<ArabicToken> tokens,List<MorphologicalAnalysis> morphology,List<SyntacticDependency> syntax,List<I3rabAnnotation> i3rab,AnalysisProvenance provenance) {
 public ArabicAnalysisResult {if(documentId==null||documentId.isBlank()||provenance==null)throw new IllegalArgumentException("document and provenance required");tokens=tokens==null?List.of():List.copyOf(tokens);morphology=morphology==null?List.of():List.copyOf(morphology);syntax=syntax==null?List.of():List.copyOf(syntax);i3rab=i3rab==null?List.of():List.copyOf(i3rab);var ids=tokens.stream().map(ArabicToken::id).collect(java.util.stream.Collectors.toSet());if(morphology.stream().anyMatch(a->!ids.contains(a.tokenId()))||i3rab.stream().anyMatch(a->!ids.contains(a.tokenId()))||syntax.stream().anyMatch(a->!ids.contains(a.dependentTokenId())||(a.headTokenId()!=null&&!ids.contains(a.headTokenId()))))throw new IllegalArgumentException("analysis references unknown token");}
}