package tech.kayys.kallam.istinbat;

import java.util.List;

public record ResearchPlan(
        List<ResearchTask> tasks,
        int maxRounds,
        int maxToolCalls,
        int maxReasoningTokens,
        int maxEvidenceItems,
        long maxExecutionMillis) {

    public ResearchPlan(
            List<ResearchTask> tasks,
            int maxRounds,
            int maxToolCalls,
            int maxReasoningTokens) {
        this(tasks, maxRounds, maxToolCalls, maxReasoningTokens,
                Math.max(maxToolCalls, 1) * 10, 120_000L);
    }

    public ResearchPlan {
        tasks = tasks == null ? List.of() : List.copyOf(tasks);
        if (maxRounds < 1 || maxToolCalls < 1 || maxReasoningTokens < 1
                || maxEvidenceItems < 1 || maxExecutionMillis < 1) {
            throw new IllegalArgumentException("research budgets must be positive");
        }
    }

    public record ResearchTask(
            String id,
            String specialist,
            String query,
            int topK,
            boolean primarySourceOnly,
            String rationale) {

        public ResearchTask(
                String id,
                String specialist,
                String query,
                int topK,
                boolean primarySourceOnly) {
            this(id, specialist, query, topK, primarySourceOnly, "Initial research plan");
        }

        public ResearchTask {
            if (id == null || id.isBlank() || specialist == null || specialist.isBlank()
                    || query == null || query.isBlank() || topK < 1) {
                throw new IllegalArgumentException("research task fields must be valid");
            }
            rationale = rationale == null ? "" : rationale;
        }

        public static ResearchTask from(FollowUpResearchTask task) {
            return new ResearchTask(task.id(), task.specialist(), task.query(),
                    task.topK(), task.primarySourceOnly(), task.rationale());
        }
    }
}
