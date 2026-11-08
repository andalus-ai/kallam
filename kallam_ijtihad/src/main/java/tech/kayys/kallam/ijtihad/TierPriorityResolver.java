package tech.kayys.kallam.ijtihad;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The one genuinely deterministic step in this module: walk
 * {@link EvidenceTier} in declaration order (Qur'an, hadith, ijma',
 * qiyas), stop at the first tier with findings above the confidence
 * threshold, and never look at a lower tier once a higher one resolves the
 * question. No LLM call, no rule card, no interpretation - this is plain
 * control flow because the escalation *decision itself* isn't
 * interpretive, even though what happens inside a tier often is.
 *
 * <p>{@code minGoverningConfidence} exists so a single low-confidence
 * finding in a high tier can't wrongly suppress escalation to a tier that
 * would actually resolve the question with more confidence - "the Qur'an
 * was checked" and "the Qur'an settles this" are different claims, and
 * only the second should stop the cascade.
 */
public final class TierPriorityResolver {

    private final double minGoverningConfidence;

    public TierPriorityResolver(double minGoverningConfidence) {
        if (minGoverningConfidence < 0.0 || minGoverningConfidence > 1.0) {
            throw new IllegalArgumentException("minGoverningConfidence must be in [0.0, 1.0]");
        }
        this.minGoverningConfidence = minGoverningConfidence;
    }

    public TierResolution resolve(List<ExpertOpinionResult> opinions) {
        Map<EvidenceTier, List<ExpertFinding>> byTier = groupByTier(opinions);
        List<String> log = new ArrayList<>();

        for (EvidenceTier tier : EvidenceTier.values()) {
            List<ExpertFinding> findings = byTier.getOrDefault(tier, List.of());
            List<ExpertFinding> aboveThreshold = findings.stream()
                    .filter(f -> f.confidenceScore() >= minGoverningConfidence)
                    .toList();

            if (aboveThreshold.isEmpty()) {
                log.add(tier + ": silent (" + findings.size() + " finding(s) below confidence threshold "
                        + minGoverningConfidence + ") - escalating");
                continue;
            }

            log.add(tier + ": resolves the question with " + aboveThreshold.size()
                    + " finding(s) at or above confidence " + minGoverningConfidence + " - stopping here");
            return new TierResolution(tier, aboveThreshold, log);
        }

        log.add("No tier - including qiyas - produced a finding above the confidence threshold; "
                + "flag for scholar ijtihad rather than asserting a determination.");
        return new TierResolution(null, List.of(), log);
    }

    private Map<EvidenceTier, List<ExpertFinding>> groupByTier(List<ExpertOpinionResult> opinions) {
        Map<EvidenceTier, List<ExpertFinding>> byTier = new EnumMap<>(EvidenceTier.class);
        for (ExpertOpinionResult opinion : opinions) {
            for (ExpertFinding finding : opinion.findings()) {
                byTier.computeIfAbsent(finding.tier(), t -> new ArrayList<>()).add(finding);
            }
        }
        return byTier;
    }
}
