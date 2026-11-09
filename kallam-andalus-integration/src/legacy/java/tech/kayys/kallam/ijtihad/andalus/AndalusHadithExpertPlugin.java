package tech.kayys.kallam.ijtihad.andalus;

import java.util.List;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.ExpertFinding;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;
import tech.kayys.andalus.execution.AgentExecutionService;
import tech.kayys.andalus.execution.ExecutionBudget;
import tech.kayys.andalus.runtime.AgentRegistry;

/**
 * Worked example of {@link AndalusGroundedExpertPlugin}. Everything except
 * {@link #parseFindings} and the inherited {@code runAgentBlocking} is
 * complete and not a stub - prompt construction from rule cards and cited
 * evidence is the part that was always fully specifiable regardless of
 * Andalus's exact execution API, so it's written out in full rather than
 * left as a TODO alongside the genuinely unverified pieces.
 */
public final class AndalusHadithExpertPlugin extends AndalusGroundedExpertPlugin {

    public AndalusHadithExpertPlugin(
            AgentRegistry agentRegistry, AgentExecutionService agentExecutionService, ExecutionBudget budget) {
        super(agentRegistry, agentExecutionService, budget);
    }

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
    protected String agentName() {
        return "kallam-hadith-expert";
    }

    @Override
    protected String buildPrompt(MasalahContext context, List<RuleCard> applicableRuleCards) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are the hadith science expert for a research session on:\n")
                .append(context.masalah().question()).append("\n\n");

        prompt.append("Apply exactly the following methodology. Do not deviate from it, ")
                .append("and cite the specific card when a rule drives your finding.\n\n");
        for (RuleCard card : applicableRuleCards) {
            prompt.append("--- ").append(card.id()).append(": ").append(card.title()).append(" ---\n")
                    .append(card.body()).append("\n\n");
        }

        prompt.append("Evidence available for this mas'alah:\n");
        for (var evidence : context.evidence()) {
            prompt.append("- [").append(evidence.id()).append("] ").append(evidence.arabicText())
                    .append(" — ").append(evidence.translation()).append("\n");
        }

        prompt.append("\nReturn a JSON array. Each element: claim (string), confidenceScore ")
                .append("(0.0-1.0, calibrated per the scoring guidance in the cards above), ")
                .append("citedEvidenceIds (array of the evidence ids above you relied on), ")
                .append("citedRuleCardIds (array of card ids that drove this finding), ")
                .append("reasoning (string, reference the specific rule applied by name).");

        return prompt.toString();
    }

    @Override
    protected List<ExpertFinding> parseFindings(String agentOutput) {
        // TODO: parse the JSON array specified in buildPrompt's final
        // instruction into ExpertFinding records. Left open pending a
        // decision on whether structured output is enforced via a JSON
        // schema on the Andalus agent call itself (preferable - fewer
        // parse failures) or parsed defensively from free text (fallback
        // if schema-constrained output isn't available for this agent).
        throw new UnsupportedOperationException("Parse agentOutput per the JSON contract specified in buildPrompt");
    }
}
