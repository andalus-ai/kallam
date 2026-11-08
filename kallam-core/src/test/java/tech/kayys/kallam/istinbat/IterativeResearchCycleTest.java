package tech.kayys.kallam.istinbat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class IterativeResearchCycleTest {

    @Test
    void critiqueEmitsTypedFollowUpForConfiguredMethodologyGap() {
        var state = state(List.of(), 1, 0);
        var critic = new BasicResearchCritic(List.of(
                new ResearchRequirement("hadith-check", EvidenceType.HADITH,
                        "hadith", "Verify relevant hadith evidence", true)));

        var critique = critic.critique(state);

        assertEquals(1, critique.followUpTasks().size());
        assertEquals("hadith", critique.followUpTasks().getFirst().specialist());
        assertTrue(critique.followUpTasks().getFirst().primarySourceOnly());
        assertFalse(critique.readyForScholarReview());
    }

    @Test
    void coordinatorUsesCritiqueFollowUpsInsteadOfReplayingInitialPlan() {
        var plan = new ResearchPlan(
                List.of(new ResearchPlan.ResearchTask(
                        "initial-fiqh", "fiqh", "question", 5, false)),
                3, 5, 1000, 20, 10_000);
        var planner = (ResearchPlanner) request -> plan;
        var critic = new BasicResearchCritic(List.of(
                new ResearchRequirement("hadith-check", EvidenceType.HADITH,
                        "hadith", "Verify relevant hadith evidence", true)));
        var invocationCounts = new java.util.concurrent.ConcurrentHashMap<String, Integer>();
        var specialists = List.of(
                specialist("fiqh", EvidenceType.FIQH, invocationCounts),
                specialist("hadith", EvidenceType.HADITH, invocationCounts));
        var captured = new AtomicReference<ResearchState>();
        ResearchReportBuilder builder = state -> {
            captured.set(state);
            return report(state);
        };

        new IstinbatCoordinator(planner, new DefaultResearchAnalyzer(),
                critic, builder, specialists).research(request());

        assertEquals(1, invocationCounts.get("fiqh"));
        assertEquals(1, invocationCounts.get("hadith"));
        assertEquals(2, captured.get().round());
        assertEquals(2, captured.get().toolCalls());
        assertEquals(2, captured.get().evidence().size());
        assertTrue(captured.get().critique().readyForScholarReview());
    }

    @Test
    void specialistInvocationBudgetCountsCallsNotEvidenceRecords() {
        var plan = new ResearchPlan(List.of(
                new ResearchPlan.ResearchTask("many", "many", "question", 5, false)),
                2, 1, 1000, 10, 10_000);
        var threeEvidence = List.of(
                evidence("e1", EvidenceType.FIQH),
                evidence("e2", EvidenceType.FIQH),
                evidence("e3", EvidenceType.FIQH));
        var specialist = new ResearchSpecialist() {
            public String id() { return "many"; }
            public List<Evidence> research(ResearchRequest request, ResearchState state,
                                          ResearchPlan.ResearchTask task) {
                return threeEvidence;
            }
        };
        var captured = new AtomicReference<ResearchState>();
        ResearchReportBuilder builder = state -> {
            captured.set(state);
            return report(state);
        };

        new IstinbatCoordinator(r -> plan, new DefaultResearchAnalyzer(),
                new BasicResearchCritic(), builder, List.of(specialist)).research(request());

        assertEquals(1, captured.get().toolCalls());
        assertEquals(3, captured.get().evidence().size());
    }

    @Test
    void coordinatorStopsWhenRoundMakesNoEvidenceProgress() {
        var plan = new ResearchPlan(List.of(
                new ResearchPlan.ResearchTask("empty", "empty", "question", 5, false)),
                4, 10, 1000, 20, 10_000);
        var empty = new ResearchSpecialist() {
            public String id() { return "empty"; }
            public List<Evidence> research(ResearchRequest request, ResearchState state,
                                          ResearchPlan.ResearchTask task) {
                return List.of();
            }
        };
        var captured = new AtomicReference<ResearchState>();
        ResearchReportBuilder builder = state -> {
            captured.set(state);
            return report(state);
        };

        new IstinbatCoordinator(r -> plan, new DefaultResearchAnalyzer(),
                new BasicResearchCritic(), builder, List.of(empty)).research(request());

        assertEquals(1, captured.get().round());
        assertEquals(1, captured.get().toolCalls());
        assertTrue(captured.get().evidence().isEmpty());
    }

    private static ResearchSpecialist specialist(
            String id, EvidenceType type, Map<String, Integer> invocations) {
        return new ResearchSpecialist() {
            public String id() { return id; }
            public List<Evidence> research(ResearchRequest request, ResearchState state,
                                          ResearchPlan.ResearchTask task) {
                invocations.merge(id, 1, Integer::sum);
                return List.of(evidence(id + "-evidence", type));
            }
        };
    }

    private static Evidence evidence(String id, EvidenceType type) {
        return new Evidence(id, type, "source text", null,
                new SourceReference(id, "Test work", "Test author", null,
                        null, null, null, "locator", Map.of()),
                EvidenceStatus.PRIMARY_SOURCE, 0.9, List.of(), List.of(),
                Map.of("sourceId", id));
    }

    private static ResearchState state(List<Evidence> evidence, int round, int toolCalls) {
        return new ResearchState("case-1", ResearchPhase.ANALYSIS,
                new Masalah("question", "taswir", List.of(), List.of()),
                List.of(), evidence, List.of(), List.of(), List.of(),
                List.of(), List.of(), null, round, toolCalls);
    }

    private static ResearchRequest request() {
        return new ResearchRequest("case-1", "question", "en",
                new ResearchRequest.ResearchBudget(4, 10, 2000));
    }

    private static ResearchReport report(ResearchState state) {
        return new ResearchReport(state.caseId(), state.masalah().question(),
                state.masalah().taswir(), state.evidence(), state.positions(),
                state.usulFindings(), state.argumentsAgainst(), state.unresolvedQuestions(),
                state.critique(), "test", true);
    }
}
