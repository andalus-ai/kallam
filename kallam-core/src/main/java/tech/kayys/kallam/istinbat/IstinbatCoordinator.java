package tech.kayys.kallam.istinbat;

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
                request.caseId(),
                ResearchPhase.PLANNING,
                masalah,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                null,
                0,
                0);

        var plan = planner.plan(request);

        for (int round = 0; round < plan.maxRounds(); round++) {
            state = state.nextRound().withPhase(ResearchPhase.RETRIEVAL);

            for (var task : plan.tasks()) {
                if (state.toolCalls() >= plan.maxToolCalls()) break;

                var specialist = specialists.get(task.specialist());
                if (specialist == null) continue;

                var evidence = specialist.research(request, state, task);
                int remaining = plan.maxToolCalls() - state.toolCalls();
                if (evidence.size() > remaining) {
                    evidence = evidence.subList(0, remaining);
                }
                state = state.addEvidence(evidence);
            }

            state = analyzer.analyze(state);
            state = state.withCritique(critic.critique(state));

            if (state.critique().readyForScholarReview()) break;
            if (state.critique().requiredResearch().isEmpty()) break;
        }

        state = state.withPhase(ResearchPhase.REPORT);
        return reportBuilder.build(state);
    }
}
