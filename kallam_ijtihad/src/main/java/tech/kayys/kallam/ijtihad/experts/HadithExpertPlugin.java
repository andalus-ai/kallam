package tech.kayys.kallam.ijtihad.experts;

import java.util.List;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.ExpertOpinionResult;
import tech.kayys.kallam.ijtihad.IjtihadExpertPlugin;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;

/**
 * Isnad and matn criticism: narrator reliability (jarh wa ta'dil), chain
 * continuity, grading (sahih/hasan/da'if), and matn-level corroboration or
 * contradiction against other transmitted evidence.
 *
 * <p><b>Stub.</b> A real implementation resolves an {@code AgentInstance}
 * (via Andalus's {@code AgentRegistry}) configured with this expert's
 * methodology cards as context, invokes it against the cited hadith
 * evidence, and parses its structured output into {@link ExpertFinding}s -
 * left undone here since fabricating the exact Andalus agent-invocation
 * call without verifying its current signature would be more misleading
 * than an honest gap.
 */
public final class HadithExpertPlugin implements IjtihadExpertPlugin {

    @Override
    public String id() {
        return "expert.hadith";
    }

    @Override
    public String name() {
        return "Hadith science (isnad and matn criticism)";
    }

    @Override
    public EvidenceTier[] applicableTiers() {
        return new EvidenceTier[] { EvidenceTier.HADITH };
    }

    @Override
    public List<String> methodologyRuleCardIds(MasalahContext context) {
        return List.of("experts.hadith-expert", "tarjih.hadith-hadith-conflict");
    }

    @Override
    public ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards) {
        throw new UnsupportedOperationException(
                "Wire to a Andalus AgentInstance running against this expert's methodology cards");
    }
}
