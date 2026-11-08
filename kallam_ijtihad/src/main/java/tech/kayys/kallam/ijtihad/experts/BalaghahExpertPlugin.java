package tech.kayys.kallam.ijtihad.experts;

import java.util.List;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.ExpertOpinionResult;
import tech.kayys.kallam.ijtihad.IjtihadExpertPlugin;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;

/**
 * Arabic rhetoric and linguistics: dalalat al-alfaz (what a form of words
 * entails - amr, nahy, 'aam/khass, mutlaq/muqayyad, haqiqah/majaz). This
 * expert's findings feed both the Qur'an and hadith tiers, since the same
 * linguistic analysis applies to both kinds of text.
 *
 * <p><b>Stub</b> - see {@link HadithExpertPlugin} javadoc for why.
 */
public final class BalaghahExpertPlugin implements IjtihadExpertPlugin {

    @Override
    public String id() {
        return "expert.balaghah";
    }

    @Override
    public String name() {
        return "Balaghah / Arabic linguistics (dalalat al-alfaz)";
    }

    @Override
    public EvidenceTier[] applicableTiers() {
        return new EvidenceTier[] { EvidenceTier.QURAN, EvidenceTier.HADITH };
    }

    @Override
    public List<String> methodologyRuleCardIds(MasalahContext context) {
        return List.of("experts.balaghah-expert");
    }

    @Override
    public ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards) {
        throw new UnsupportedOperationException(
                "Wire to a Andalus AgentInstance running against this expert's methodology cards");
    }
}
