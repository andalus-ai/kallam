package tech.kayys.kallam.istinbat;

import java.util.List;

/**
 * Safe default when no model-backed analysis adapter is configured.
 * It deliberately does not invent claims, arguments, or scholarly positions.
 */
public final class DefaultResearchAnalyzer implements ResearchAnalyzer {

    @Override
    public ResearchAnalysisResult analyze(
            ResearchState state,
            ResearchMethodology methodology,
            int remainingReasoningTokens) {
        return new ResearchAnalysisResult(
                methodology.id(),
                methodology.version(),
                ResearchAnalysisResult.Status.NOT_CONFIGURED,
                List.of(), List.of(), List.of(), List.of(), List.of(),
                List.of(), List.of(), ResearchTokenUsage.none());
    }
}
