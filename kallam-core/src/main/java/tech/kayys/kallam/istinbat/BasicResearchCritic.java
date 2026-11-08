package tech.kayys.kallam.istinbat;

import java.util.ArrayList;
import java.util.List;

public final class BasicResearchCritic implements ResearchCritic {

    private final List<ResearchRequirement> requirements;

    /** Generic baseline: does not assume that every mas'alah requires Quran and Hadith retrieval. */
    public BasicResearchCritic() {
        this(List.of());
    }

    /** Requirements must be supplied by an explicit, versioned methodology/profile. */
    public BasicResearchCritic(List<ResearchRequirement> requirements) {
        this.requirements = requirements == null ? List.of() : List.copyOf(requirements);
    }

    @Override
    public ResearchCritique critique(ResearchState state) {
        var findings = new ArrayList<ResearchCritique.Finding>();
        var required = new ArrayList<String>();
        var followUps = new ArrayList<FollowUpResearchTask>();

        if (state.evidence().isEmpty()) {
            findings.add(new ResearchCritique.Finding(
                    "EVIDENCE", "HIGH", "No evidence was retrieved.", List.of()));
            required.add("Retrieve evidence relevant to the research question.");
        }

        int satisfiedRequirements = 0;
        for (var requirement : requirements) {
            boolean satisfied = state.evidence().stream()
                    .anyMatch(e -> e.type() == requirement.evidenceType());
            if (satisfied) {
                satisfiedRequirements++;
                continue;
            }

            String message = "Missing evidence requirement: " + requirement.objective();
            required.add(message);
            findings.add(new ResearchCritique.Finding(
                    "METHODOLOGY", "HIGH", message, List.of()));
            followUps.add(new FollowUpResearchTask(
                    requirement.id() + "-round-" + (state.round() + 1),
                    requirement.specialist(),
                    requirement.objective(),
                    state.masalah().question() + " — " + requirement.objective(),
                    5,
                    requirement.primarySourceOnly(),
                    "Required by the selected methodology/profile"));
        }

        if (!state.unresolvedQuestions().isEmpty()) {
            findings.add(new ResearchCritique.Finding(
                    "FACTUAL", "MEDIUM",
                    "The research contains unresolved questions.",
                    List.of()));
            required.addAll(state.unresolvedQuestions());
        }

        double completeness;
        if (state.evidence().isEmpty()) {
            completeness = 0.0;
        } else if (requirements.isEmpty()) {
            // Evidence presence is only a minimal signal, not a scholarly quality score.
            completeness = 0.5;
        } else {
            completeness = 0.5 + (0.5 * satisfiedRequirements / requirements.size());
        }
        if (!state.unresolvedQuestions().isEmpty()) {
            completeness = Math.max(0.0, completeness - 0.1);
        }
        completeness = Math.max(0.0, Math.min(1.0, completeness));

        boolean ready = !state.evidence().isEmpty()
                && required.isEmpty()
                && completeness >= 0.8;

        return new ResearchCritique(findings, required, completeness, ready, followUps);
    }
}
