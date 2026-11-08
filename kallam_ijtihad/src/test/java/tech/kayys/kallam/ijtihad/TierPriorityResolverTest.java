package tech.kayys.kallam.ijtihad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The tier cascade is the one piece of this module that must never be
 * wrong in a way that's hard to notice - a bug here means qiyas silently
 * outranking a Qur'anic finding. Pure function, no mocking required.
 */
class TierPriorityResolverTest {

    private final TierPriorityResolver resolver = new TierPriorityResolver(0.6);

    private ExpertFinding finding(EvidenceTier tier, double confidence) {
        return new ExpertFinding("claim", tier, confidence, List.of(), List.of(), "reasoning");
    }

    @Test
    void quranGovernsWhenPresentEvenIfLowerTiersAlsoHaveFindings() {
        var opinions = List.of(
                new ExpertOpinionResult("balaghah", List.of(finding(EvidenceTier.QURAN, 0.9))),
                new ExpertOpinionResult("hadith", List.of(finding(EvidenceTier.HADITH, 0.95))),
                new ExpertOpinionResult("madhhab", List.of(finding(EvidenceTier.QIYAS, 0.99))));

        TierResolution result = resolver.resolve(opinions);

        assertEquals(EvidenceTier.QURAN, result.governingTier());
        assertEquals(1, result.governingFindings().size());
    }

    @Test
    void escalatesToHadithWhenQuranBelowConfidenceThreshold() {
        var opinions = List.of(
                new ExpertOpinionResult("balaghah", List.of(finding(EvidenceTier.QURAN, 0.4))),
                new ExpertOpinionResult("hadith", List.of(finding(EvidenceTier.HADITH, 0.8))));

        TierResolution result = resolver.resolve(opinions);

        assertEquals(EvidenceTier.HADITH, result.governingTier());
        assertTrue(result.escalationLog().get(0).contains("silent"));
    }

    @Test
    void qiyasOnlyGovernsWhenEveryHigherTierIsSilent() {
        var opinions = List.of(new ExpertOpinionResult("tahqiq-manat", List.of(finding(EvidenceTier.QIYAS, 0.85))));

        TierResolution result = resolver.resolve(opinions);

        assertEquals(EvidenceTier.QIYAS, result.governingTier());
        assertEquals(3, result.escalationLog().size() - 1); // Qur'an, hadith, ijma' all logged as silent first
    }

    @Test
    void fullyUnresolvedWhenNoTierClearsTheThreshold() {
        var opinions = List.of(new ExpertOpinionResult("hadith", List.of(finding(EvidenceTier.HADITH, 0.3))));

        TierResolution result = resolver.resolve(opinions);

        assertNull(result.governingTier());
        assertTrue(result.isFullyUnresolved());
    }
}
