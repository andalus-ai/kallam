package tech.kayys.kallam.ijtihad.experts;

import java.util.List;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.ExpertOpinionResult;
import tech.kayys.kallam.ijtihad.IjtihadExpertPlugin;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;

/**
 * Comparative fiqh: how the four Sunni madhahib (and where relevant,
 * others) differ on this mas'alah, and - critically - *why*, tracing each
 * position back to a usul-methodology difference (e.g. istihsan's
 * standing, qiyas conditions, 'amal ahl al-Madinah) rather than presenting
 * disagreement as unexplained variance. Feeds the ikhtilaf block directly.
 *
 * <p><b>Stub</b> - see {@code HadithExpertPlugin} javadoc for why.
 */
public final class MadhhabComparisonExpertPlugin implements IjtihadExpertPlugin {

    @Override
    public String id() {
        return "expert.madhhab-comparison";
    }

    @Override
    public String name() {
        return "Madhhab comparison";
    }

    @Override
    public EvidenceTier[] applicableTiers() {
        return new EvidenceTier[] { EvidenceTier.HADITH, EvidenceTier.IJMA_SAHABAH, EvidenceTier.QIYAS };
    }

    /**
     * Unlike the other expert stubs, this one actually uses
     * {@code context.madhhab()}: when a single school has been selected
     * for the session, its madhhab-specific methodology addendum is
     * loaded alongside the common comparison methodology, so the expert's
     * own reasoning is grounded in that school's usul principles rather
     * than a generic cross-school average. When no madhhab is selected
     * (comparison mode), only the common card is loaded and the expert is
     * expected to survey all schools evenly.
     */
    @Override
    public List<String> methodologyRuleCardIds(MasalahContext context) {
        List<String> ids = new java.util.ArrayList<>(List.of("experts.madhhab-comparison-expert"));
        if (context.madhhab() != null) {
            ids.add("madhhab." + context.madhhab().toLowerCase() + ".methodology");
        }
        return ids;
    }

    @Override
    public ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards) {
        throw new UnsupportedOperationException(
                "Wire to a Andalus AgentInstance running against this expert's methodology cards");
    }
}
