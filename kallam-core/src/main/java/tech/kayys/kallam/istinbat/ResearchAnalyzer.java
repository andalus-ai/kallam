package tech.kayys.kallam.istinbat;

/**
 * Port for structured analysis. Implementations may call an LLM or deterministic
 * analysis engine, but must return references to existing evidence only.
 */
public interface ResearchAnalyzer {

    ResearchAnalysisResult analyze(
            ResearchState state,
            ResearchMethodology methodology,
            int remainingReasoningTokens);
}
