package tech.kayys.kallam.istinbat;

import java.util.List;

public final class SimpleResearchPlanner implements ResearchPlanner {

    private final List<ResearchPlan.ResearchTask> initialTasks;

    /**
     * Compatibility starter profile. Production wiring should provide the task
     * list selected by the case's explicit methodology and research disciplines.
     */
    public SimpleResearchPlanner() {
        this(List.of(
                new ResearchPlan.ResearchTask("quran", "quran", "question", 5, true),
                new ResearchPlan.ResearchTask("hadith", "hadith", "question", 5, true),
                new ResearchPlan.ResearchTask("fiqh", "fiqh", "question", 5, false),
                new ResearchPlan.ResearchTask("usul", "usul", "question", 5, false)));
    }

    public SimpleResearchPlanner(List<ResearchPlan.ResearchTask> initialTasks) {
        this.initialTasks = initialTasks == null ? List.of() : List.copyOf(initialTasks);
    }

    @Override
    public ResearchPlan plan(ResearchRequest request) {
        var b = request.budget();
        var tasks = initialTasks.stream()
                .map(task -> new ResearchPlan.ResearchTask(
                        task.id(), task.specialist(),
                        task.query().equals("question") ? request.question() : task.query(),
                        task.topK(), task.primarySourceOnly(), task.rationale()))
                .toList();

        return new ResearchPlan(
                tasks,
                b.maxRounds(),
                b.maxToolCalls(),
                b.maxReasoningTokens());
    }
}
