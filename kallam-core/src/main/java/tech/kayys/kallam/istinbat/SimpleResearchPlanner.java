package tech.kayys.kallam.istinbat;

import java.util.List;

public final class SimpleResearchPlanner implements ResearchPlanner {

    @Override
    public ResearchPlan plan(ResearchRequest request) {
        var b = request.budget();
        return new ResearchPlan(
                List.of(
                        new ResearchPlan.ResearchTask("quran", "quran", request.question(), 5, true),
                        new ResearchPlan.ResearchTask("hadith", "hadith", request.question(), 5, true),
                        new ResearchPlan.ResearchTask("fiqh", "fiqh", request.question(), 5, false),
                        new ResearchPlan.ResearchTask("usul", "usul", request.question(), 5, false)
                ),
                b.maxRounds(),
                b.maxToolCalls(),
                b.maxReasoningTokens());
    }
}
