package tech.kayys.kallam.istinbat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class IstinbatCoordinator {

    private final ResearchPlanner planner;
    private final ResearchAnalyzer analyzer;
    private final ResearchCritic critic;
    private final ResearchReportBuilder reportBuilder;
    private final Map<String, ResearchSpecialist> specialists;

    public IstinbatCoordinator(
            ResearchPlanner planner,
            ResearchAnalyzer analyzer,
            ResearchCritic critic,
            ResearchReportBuilder reportBuilder,
            List<ResearchSpecialist> specialists) {
        this.planner = planner;
        this.analyzer = analyzer;
        this.critic = critic;
        this.reportBuilder = reportBuilder;
        this.specialists = specialists.stream()
                .collect(java.util.stream.Collectors.toUnmodifiableMap(
                        ResearchSpecialist::id, s -> s));
    }

    public ResearchReport research(ResearchRequest request) {
        var masalah = new Masalah(
                request.question(),
                "Taswir requires scholarly/factual construction in the model/runtime adapter.",
                List.of(),
                List.of());

        var state = new ResearchState(
                request.caseId(), ResearchPhase.PLANNING, masalah,
                List.of(), List.of(), List.of(), List.of(),
                List.of(), List.of(), masalah.unresolvedFacts(), null, 0, 0);

        var plan = planner.plan(request);
        List<ResearchPlan.ResearchTask> pendingTasks = plan.tasks();
        long startedAt = System.nanoTime();

        for (int round = 0; round < plan.maxRounds(); round++) {
            if (pendingTasks.isEmpty()
                    || state.toolCalls() >= plan.maxToolCalls()
                    || state.evidence().size() >= plan.maxEvidenceItems()
                    || elapsedMillis(startedAt) >= plan.maxExecutionMillis()) {
                break;
            }

            state = state.nextRound().withPhase(ResearchPhase.RETRIEVAL);
            int evidenceBeforeRound = state.evidence().size();

            for (var task : pendingTasks) {
                if (state.toolCalls() >= plan.maxToolCalls()
                        || state.evidence().size() >= plan.maxEvidenceItems()
                        || elapsedMillis(startedAt) >= plan.maxExecutionMillis()) {
                    break;
                }

                var specialist = specialists.get(task.specialist());
                if (specialist == null) continue;

                var retrieved = specialist.research(request, state, task);
                state = state.recordSpecialistInvocation();

                if (retrieved == null || retrieved.isEmpty()) continue;
                int remainingEvidence = plan.maxEvidenceItems() - state.evidence().size();
                var bounded = new ArrayList<Evidence>();
                for (var item : retrieved) {
                    if (item != null && bounded.size() < remainingEvidence) bounded.add(item);
                }
                state = state.addEvidence(bounded);
            }

            state = analyzer.analyze(state);
            state = state.withCritique(critic.critique(state));

            if (state.critique().readyForScholarReview()) break;

            // Follow-up work is derived from this round's critique, never replayed
            // from the initial plan. Legacy prose-only requirements are not executable.
            pendingTasks = state.critique().followUpTasks().stream()
                    .map(ResearchPlan.ResearchTask::from)
                    .toList();

            if (pendingTasks.isEmpty()) break;
            if (state.evidence().size() == evidenceBeforeRound) break;
        }

        state = state.withPhase(ResearchPhase.REPORT);
        return reportBuilder.build(state);
    }

    private static long elapsedMillis(long startedAt) {
        return (System.nanoTime() - startedAt) / 1_000_000L;
    }
}
