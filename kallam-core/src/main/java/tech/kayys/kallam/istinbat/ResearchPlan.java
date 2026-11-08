package tech.kayys.kallam.istinbat;

import java.util.List;

public record ResearchPlan(
        List<ResearchTask> tasks,
        int maxRounds,
        int maxToolCalls,
        int maxReasoningTokens) {

    public ResearchPlan {
        tasks = tasks == null ? List.of() : List.copyOf(tasks);
        if (maxRounds < 1 || maxToolCalls < 1 || maxReasoningTokens < 1) {
            throw new IllegalArgumentException("research budgets must be positive");
        }
    }

    public record ResearchTask(
            String id,
            String specialist,
            String query,
            int topK,
            boolean primarySourceOnly) {
    }
}
