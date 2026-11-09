package tech.kayys.kallam.istinbat;

/** Authoritative usage reported by the inference provider, not estimated from text length. */
public record ResearchTokenUsage(
        int inputTokens,
        int outputTokens,
        int reasoningTokens) {

    public ResearchTokenUsage {
        if (inputTokens < 0 || outputTokens < 0 || reasoningTokens < 0) {
            throw new IllegalArgumentException("token usage values must not be negative");
        }
    }

    public int totalTokens() {
        return Math.addExact(Math.addExact(inputTokens, outputTokens), reasoningTokens);
    }

    public static ResearchTokenUsage none() {
        return new ResearchTokenUsage(0, 0, 0);
    }
}
