package tech.kayys.kallam.istinbat;

public record ResearchRequest(
        String caseId,
        String question,
        String language,
        ResearchBudget budget) {

    public ResearchRequest {
        language = language == null || language.isBlank() ? "ar" : language;
        budget = budget == null ? ResearchBudget.defaults() : budget;
    }

    public record ResearchBudget(
            int maxRounds,
            int maxToolCalls,
            int maxReasoningTokens) {

        public static ResearchBudget defaults() {
            return new ResearchBudget(2, 12, 2500);
        }
    }
}
