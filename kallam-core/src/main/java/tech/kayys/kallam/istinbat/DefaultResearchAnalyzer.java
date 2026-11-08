package tech.kayys.kallam.istinbat;

public final class DefaultResearchAnalyzer implements ResearchAnalyzer {

    @Override
    public ResearchState analyze(ResearchState state) {
        // Deliberately conservative v0.1:
        // analysis is represented as structured state and is expected
        // to be supplied by a reasoning model in the runtime adapter.
        return state.withPhase(ResearchPhase.ANALYSIS);
    }
}
