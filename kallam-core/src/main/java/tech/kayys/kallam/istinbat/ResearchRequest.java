package tech.kayys.kallam.istinbat;

import java.util.Objects;

public record ResearchRequest(
        String caseId,
        String question,
        String language,
        ResearchBudget budget,
        ResearchMethodology methodology) {

    /** Backward-compatible constructor for callers that have not selected a methodology. */
    public ResearchRequest(String caseId, String question, String language, ResearchBudget budget) {
        this(caseId, question, language, budget, ResearchMethodology.unspecified());
    }

    public ResearchRequest {
        if (caseId == null || caseId.isBlank()) {
            throw new IllegalArgumentException("caseId must not be blank");
        }
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("question must not be blank");
        }
        language = language == null || language.isBlank() ? "ar" : language;
        budget = budget == null ? ResearchBudget.defaults() : budget;
        methodology = methodology == null ? ResearchMethodology.unspecified() : methodology;
    }

    public record ResearchBudget(
            int maxRounds,
            int maxToolCalls,
            int maxReasoningTokens) {

        public ResearchBudget {
            if (maxRounds < 1 || maxToolCalls < 1 || maxReasoningTokens < 1) {
                throw new IllegalArgumentException("research budget values must be positive");
            }
        }

        public static ResearchBudget defaults() {
            return new ResearchBudget(2, 12, 2500);
        }
    }
}
