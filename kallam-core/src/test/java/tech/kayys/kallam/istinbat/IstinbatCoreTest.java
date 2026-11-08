package tech.kayys.kallam.istinbat;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IstinbatCoreTest {

    @Test
    void evidenceRejectsInvalidRelevance() {
        assertThrows(IllegalArgumentException.class, () ->
            new Evidence(
                "E1", EvidenceType.QURAN, "النص", null,
                new SourceReference("Q1", "Quran", null, null, null, null, null, "2:275", null),
                EvidenceStatus.PRIMARY_SOURCE, 1.5, List.of(), List.of(), null));
    }

    @Test
    void plannerCreatesBudgetedSpecialistTasks() {
        var request = new ResearchRequest(
                "case-1", "Research a mas'alah", "ar",
                new ResearchRequest.ResearchBudget(2, 10, 2000));

        var plan = new SimpleResearchPlanner().plan(request);

        assertEquals(2, plan.maxRounds());
        assertEquals(10, plan.maxToolCalls());
        assertEquals(4, plan.tasks().size());
    }

    @Test
    void emptyResearchIsNotReadyForScholarReview() {
        var state = new ResearchState(
                "case-1", ResearchPhase.ANALYSIS,
                new Masalah("Q", "T", List.of(), List.of()),
                List.of(), List.of(), List.of(), List.of(),
                List.of(), List.of(), List.of(), null, 1, 0);

        var critique = new BasicResearchCritic().critique(state);

        assertFalse(critique.readyForScholarReview());
        assertEquals(0.0, critique.completeness());
    }
}
