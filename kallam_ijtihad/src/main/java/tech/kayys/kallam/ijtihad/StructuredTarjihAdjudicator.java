package tech.kayys.kallam.ijtihad;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import tech.kayys.kallam.istinbat.Evidence;

/**
 * Resolves only the conflicts that are honestly a lookup, not an
 * interpretive judgment wearing a lookup's clothes:
 *
 * <ul>
 *   <li><b>Hadith tier</b>: if every conflicting finding cites evidence
 *       with a known {@code grading} (sahih/hasan/da'if, read from
 *       {@code Evidence.provenance()}), the higher grading wins. Equal or
 *       unrecognised gradings return empty - matn/fiqh-level tarjih between
 *       two equally-graded hadith is not a grading comparison.</li>
 *   <li><b>Qur'an tier</b>: always returns empty. Naskh, takhsis, and jam'
 *       between ayat are interpretive by nature (see the tarjih rule
 *       cards) - there is no honest structured shortcut here, and
 *       pretending otherwise would be worse than not automating it.</li>
 *   <li><b>Ijma' and qiyas tiers</b>: always returns empty for the same
 *       reason - whether a claimed ijma' is sound, or whether one qiyas's
 *       illah is stronger than another's, is scholarly judgment.</li>
 * </ul>
 *
 * A caller should compose this with an LLM-backed adjudicator (grounded in
 * the tier's tarjih rule cards) for everything this one honestly declines.
 */
public final class StructuredTarjihAdjudicator implements TarjihAdjudicator {

    private static final List<String> HADITH_GRADING_LADDER = List.of(
            "da'if", "hasan", "sahih"); // ascending strength; index = rank

    @Override
    public Optional<TarjihResolution> adjudicate(
            EvidenceTier tier,
            List<ExpertFinding> conflicting,
            MasalahContext context,
            List<RuleCard> applicableTarjihCards) {
        if (tier != EvidenceTier.HADITH) {
            return Optional.empty();
        }
        return adjudicateByGrading(conflicting, context);
    }

    private Optional<TarjihResolution> adjudicateByGrading(List<ExpertFinding> conflicting, MasalahContext context) {
        Map<String, Evidence> evidenceById = context.evidence().stream()
                .collect(java.util.stream.Collectors.toMap(Evidence::id, e -> e, (a, b) -> a));

        ExpertFinding best = null;
        int bestRank = -1;
        boolean tie = false;

        for (ExpertFinding finding : conflicting) {
            Integer rank = findingGradingRank(finding, evidenceById);
            if (rank == null) {
                return Optional.empty(); // any un-gradeable finding forfeits the shortcut
            }
            if (rank > bestRank) {
                best = finding;
                bestRank = rank;
                tie = false;
            } else if (rank == bestRank) {
                tie = true;
            }
        }

        if (best == null || tie) {
            return Optional.empty(); // equal grading is exactly the case that needs real tarjih
        }

        return Optional.of(new TarjihResolution(
                best,
                "hadith-grading-comparison",
                "Preferred over the alternative(s) solely on isnad grading ("
                        + HADITH_GRADING_LADDER.get(bestRank) + "); matn/fiqh-level considerations were not weighed."));
    }

    private Integer findingGradingRank(ExpertFinding finding, Map<String, Evidence> evidenceById) {
        if (finding.citedEvidenceIds().isEmpty()) {
            return null;
        }
        String grading = finding.citedEvidenceIds().stream()
                .map(evidenceById::get)
                .filter(java.util.Objects::nonNull)
                .map(e -> e.provenance() == null ? null : e.provenance().get("grading"))
                .filter(java.util.Objects::nonNull)
                .map(String::toLowerCase)
                .findFirst()
                .orElse(null);
        int rank = grading == null ? -1 : HADITH_GRADING_LADDER.indexOf(grading);
        return rank < 0 ? null : rank;
    }
}
