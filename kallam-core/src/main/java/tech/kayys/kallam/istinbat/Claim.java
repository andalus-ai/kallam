package tech.kayys.kallam.istinbat;

public record Claim(
        String id,
        String statement,
        boolean factual,
        boolean unresolved) {
}
