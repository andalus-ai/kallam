package tech.kayys.kallam.ijtihad;

import java.util.List;

/**
 * The orchestrator's output. {@code status} is never silently upgraded to
 * RESOLVED by this module - {@code NEEDS_SCHOLAR_TARJIH} and
 * {@code UNRESOLVED} are first-class outcomes precisely so a scholar sees
 * "the system could not determine this" rather than a confident-looking
 * answer papering over an unresolved conflict.
 */
public record IstinbatDetermination(
        Status status,
        ExpertFinding determiningFinding,
        List<ExpertOpinionResult> allOpinions,
        TierResolution tierResolution,
        List<String> tarjihRuleCardIdsConsidered) {

    public enum Status { RESOLVED, NEEDS_SCHOLAR_TARJIH, UNRESOLVED }

    public IstinbatDetermination {
        allOpinions = allOpinions == null ? List.of() : List.copyOf(allOpinions);
        tarjihRuleCardIdsConsidered = tarjihRuleCardIdsConsidered == null ? List.of() : List.copyOf(tarjihRuleCardIdsConsidered);
    }

    public static IstinbatDetermination resolved(
            ExpertFinding finding, List<ExpertOpinionResult> opinions, TierResolution tierResolution, List<String> tarjihCardIds) {
        return new IstinbatDetermination(Status.RESOLVED, finding, opinions, tierResolution, tarjihCardIds);
    }

    public static IstinbatDetermination needsScholarTarjih(
            List<ExpertFinding> conflicting, List<ExpertOpinionResult> opinions, TierResolution tierResolution, List<String> tarjihCardIds) {
        return new IstinbatDetermination(Status.NEEDS_SCHOLAR_TARJIH, null, opinions, tierResolution, tarjihCardIds);
    }

    public static IstinbatDetermination unresolved(List<ExpertOpinionResult> opinions, TierResolution tierResolution) {
        return new IstinbatDetermination(Status.UNRESOLVED, null, opinions, tierResolution, List.of());
    }
}
