package tech.kayys.kallam.istinbat;

import java.util.List;

public final class DefaultResearchReportBuilder implements ResearchReportBuilder {

    @Override
    public ResearchReport build(ResearchState state) {
        String synthesis = state.critique() != null && state.critique().readyForScholarReview()
                ? "Research package is ready for scholarly review; this is not a ruling."
                : "Research remains incomplete; no definitive conclusion is produced.";

        List<String> analysis = state.analysisResult() == null
                ? state.claims().stream().map(Claim::statement).toList()
                : state.analysisResult().claims().stream().map(Claim::statement).toList();

        return new ResearchReport(
                state.caseId(),
                state.masalah().question(),
                state.masalah().taswir(),
                state.evidence(),
                state.positions(),
                analysis,
                state.argumentsAgainst(),
                state.unresolvedQuestions(),
                state.critique(),
                synthesis,
                true,
                state.analysisResult());
    }
}
