package tech.kayys.kallam.ijtihad.experts;

import java.util.List;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.ExpertOpinionResult;
import tech.kayys.kallam.ijtihad.IjtihadExpertPlugin;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;

/**
 * Historical context: asbab al-nuzul (occasions of revelation), asbab
 * al-wurud (occasions of a hadith's utterance), and sirah/chronology -
 * the evidence naskh determination in the Qur'an tarjih rule card depends
 * on, and the context that keeps a ruling from being applied outside the
 * circumstance it addressed.
 *
 * <p><b>Stub</b> - see {@code HadithExpertPlugin} javadoc for why.
 */
public final class TarikhExpertPlugin implements IjtihadExpertPlugin {

    @Override
    public String id() {
        return "expert.tarikh";
    }

    @Override
    public String name() {
        return "Tarikh / sirah (asbab al-nuzul, asbab al-wurud, chronology)";
    }

    @Override
    public EvidenceTier[] applicableTiers() {
        return new EvidenceTier[] { EvidenceTier.QURAN, EvidenceTier.HADITH };
    }

    @Override
    public List<String> methodologyRuleCardIds(MasalahContext context) {
        return List.of("experts.tarikh-expert", "tarjih.quran-quran-conflict");
    }

    @Override
    public ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards) {
        throw new UnsupportedOperationException(
                "Wire to a Andalus AgentInstance running against this expert's methodology cards");
    }
}
