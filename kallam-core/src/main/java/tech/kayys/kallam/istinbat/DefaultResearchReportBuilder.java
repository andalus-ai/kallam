package tech.kayys.kallam.istinbat;

import java.util.List;

public final class DefaultResearchReportBuilder implements ResearchReportBuilder {

    @Override
    public ResearchReport build(ResearchState state) {
        String synthesis = state.critique() != null && state.critique().readyForScholarReview()
                ? "Research package is ready for scholarly review."
                : "Research remains incomplete; no definitive conclusion is produced.";

        return new ResearchReport(
                state.caseId(),
                state.masalah().question(),
                state.masalah().taswir(),
                state.evidence(),
                state.positions(),
                state.usulFindings(),
                state.argumentsAgainst(),
                state.unresolvedQuestions(),
                state.critique(),
                synthesis,
                true);
    }
}
