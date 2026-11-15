package tech.kayys.kallam.language.arabic.analysis;
/** Hexagonal port for deterministic, statistical or AI-assisted Arabic analysis engines. */
public interface ArabicAnalysisProvider {
 String providerId(); String providerVersion();
 boolean supports(ArabicAnalysisRequest.Task task);
 ArabicAnalysisResult analyze(ArabicAnalysisRequest request);
}