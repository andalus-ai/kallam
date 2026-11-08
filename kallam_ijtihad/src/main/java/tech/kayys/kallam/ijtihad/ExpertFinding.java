package tech.kayys.kallam.ijtihad;

import java.util.List;

/**
 * One scored interpretive claim from an expert - the ijtihad-domain
 * counterpart of Andalus guardrails' {@code Finding(category, description,
 * start, end, confidence)}. {@code confidenceScore} is the expert's own
 * calibrated confidence, not a claim of correctness - two experts (or the
 * same expert on a re-run) can legitimately disagree, and disagreement is
 * signal for the orchestrator, not noise to average away.
 */
public record ExpertFinding(
        String claim,
        EvidenceTier tier,
        double confidenceScore,
        List<String> citedEvidenceIds,
        List<String> citedRuleCardIds,
        String reasoning) {

    public ExpertFinding {
        if (confidenceScore < 0.0 || confidenceScore > 1.0) {
            throw new IllegalArgumentException("confidenceScore must be in [0.0, 1.0]");
        }
        citedEvidenceIds = citedEvidenceIds == null ? List.of() : List.copyOf(citedEvidenceIds);
        citedRuleCardIds = citedRuleCardIds == null ? List.of() : List.copyOf(citedRuleCardIds);
    }
}
