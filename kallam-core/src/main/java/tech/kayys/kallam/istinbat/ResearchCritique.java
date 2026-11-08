package tech.kayys.kallam.istinbat;

import java.util.List;

public record ResearchCritique(
        List<Finding> findings,
        List<String> requiredResearch,
        double completeness,
        boolean readyForScholarReview,
        List<FollowUpResearchTask> followUpTasks) {

    public ResearchCritique(
            List<Finding> findings,
            List<String> requiredResearch,
            double completeness,
            boolean readyForScholarReview) {
        this(findings, requiredResearch, completeness, readyForScholarReview, List.of());
    }

    public ResearchCritique {
        findings = findings == null ? List.of() : List.copyOf(findings);
        requiredResearch = requiredResearch == null ? List.of() : List.copyOf(requiredResearch);
        followUpTasks = followUpTasks == null ? List.of() : List.copyOf(followUpTasks);
        if (completeness < 0 || completeness > 1) {
            throw new IllegalArgumentException("completeness must be between 0 and 1");
        }
        if (readyForScholarReview && !followUpTasks.isEmpty()) {
            throw new IllegalArgumentException("ready critique cannot contain follow-up tasks");
        }
    }

    public record Finding(
            String category,
            String severity,
            String message,
            List<String> evidenceIds) {
        public Finding {
            evidenceIds = evidenceIds == null ? List.of() : List.copyOf(evidenceIds);
        }
    }
}
