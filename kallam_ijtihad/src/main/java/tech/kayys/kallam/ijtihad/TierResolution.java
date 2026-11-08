package tech.kayys.kallam.ijtihad;

import java.util.List;

/**
 * The outcome of tier gating: which tier governs (or none, if every tier
 * was silent), which findings belong to it, and - just as important for
 * audit purposes - the log of *why* each earlier tier was or wasn't
 * escalated past. A scholar reviewing "qiyas was used here" should be able
 * to see, in one place, that Qur'an and hadith were genuinely checked and
 * found silent, not skipped.
 */
public record TierResolution(
        EvidenceTier governingTier,
        List<ExpertFinding> governingFindings,
        List<String> escalationLog) {

    public TierResolution {
        governingFindings = governingFindings == null ? List.of() : List.copyOf(governingFindings);
        escalationLog = escalationLog == null ? List.of() : List.copyOf(escalationLog);
    }

    public boolean isFullyUnresolved() {
        return governingTier == null;
    }
}
