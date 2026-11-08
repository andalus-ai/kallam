package tech.kayys.kallam.ijtihad.experts;

import java.util.List;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.ExpertOpinionResult;
import tech.kayys.kallam.ijtihad.IjtihadExpertPlugin;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;

/**
 * Tahqiq al-manat: verifying that a general ruling's stated 'illah
 * actually applies to this specific case - distinct from identifying the
 * 'illah itself (that's the qiyas chain) and from grading the sources
 * (that's the hadith expert). This is the step that keeps qiyas from being
 * applied mechanically to a case that only superficially resembles the
 * asl.
 *
 * <p>This is my reading of "expert of tahseen" from the request that
 * shaped this module - flag if a different discipline was meant.
 *
 * <p><b>Stub</b> - see {@code HadithExpertPlugin} javadoc for why.
 */
public final class TahqiqManatExpertPlugin implements IjtihadExpertPlugin {

    @Override
    public String id() {
        return "expert.tahqiq-al-manat";
    }

    @Override
    public String name() {
        return "Tahqiq al-manat (case-specific verification of the 'illah)";
    }

    @Override
    public EvidenceTier[] applicableTiers() {
        return new EvidenceTier[] { EvidenceTier.QIYAS };
    }

    @Override
    public List<String> methodologyRuleCardIds(MasalahContext context) {
        return List.of("tier-priority.qiyas-last-resort");
    }

    @Override
    public ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards) {
        throw new UnsupportedOperationException(
                "Wire to a Andalus AgentInstance running against this expert's methodology cards");
    }
}
