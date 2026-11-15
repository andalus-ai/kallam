package tech.kayys.kallam.ijtihad.andalus;

import java.util.List;
import tech.kayys.kallam.ijtihad.ExpertFinding;
import tech.kayys.kallam.ijtihad.ExpertOpinionResult;
import tech.kayys.kallam.ijtihad.IjtihadExpertPlugin;
import tech.kayys.kallam.ijtihad.MasalahContext;
import tech.kayys.kallam.ijtihad.RuleCard;
import tech.kayys.andalus.agent.AgentDefinition;
import tech.kayys.andalus.agent.AgentRequest;
import tech.kayys.andalus.execution.AgentExecution;
import tech.kayys.andalus.execution.AgentExecutionService;
import tech.kayys.andalus.execution.ExecutionBudget;
import tech.kayys.andalus.runtime.AgentRegistry;

/**
 * A real (partially) Andalus-backed {@link IjtihadExpertPlugin}, replacing
 * the {@code throw new UnsupportedOperationException} stubs in
 * {@code tech.kayys.kallam.ijtihad.experts}. Being precise about what's
 * actually verified here, since "real wiring" only means something if the
 * claim is checkable:
 *
 * <p><b>Verified directly against source in this project:</b>
 * <ul>
 *   <li>{@code AgentRegistry.findByName(String)} - {@code AgentRegistry}
 *       extends {@code GenericRegistry<AgentDefinition>}, which declares
 *       {@code findByName}.</li>
 *   <li>{@code AgentExecutionService.create(AgentDefinition, AgentRequest,
 *       ExecutionBudget) -> AgentExecution} - read directly from
 *       andalus-runtime-core's {@code AgentExecutionService.java}.</li>
 * </ul>
 *
 * <p><b>Not verified - confirm before relying on this:</b> {@code
 * AgentRequest}'s exact builder fields. I found {@code .prompt(...)},
 * {@code .maxSteps(...)}, and a static {@code AgentRequest.of(prompt)}
 * shorthand used elsewhere in the sources I had access to, but not inside
 * {@code tech.kayys.andalus.agent.AgentRequest}'s own file, which wasn't
 * included in what was provided. {@code AgentExecution}'s own interface -
 * however a caller actually drives it to completion and reads a result
 * back out - wasn't included either. Both gaps are isolated to
 * {@link #runAgentBlocking}, the one method in this class that's a stub
 * rather than a verified call, so the boundary between "checked" and "not
 * checked" stays visible instead of blurring across the whole class.
 */
public abstract class AndalusGroundedExpertPlugin implements IjtihadExpertPlugin {

    private final AgentRegistry agentRegistry;
    private final AgentExecutionService agentExecutionService;
    private final ExecutionBudget budget;

    protected AndalusGroundedExpertPlugin(
            AgentRegistry agentRegistry, AgentExecutionService agentExecutionService, ExecutionBudget budget) {
        this.agentRegistry = agentRegistry;
        this.agentExecutionService = agentExecutionService;
        this.budget = budget;
    }

    /** The registered Andalus agent name this expert runs, e.g. "kallam-hadith-expert". */
    protected abstract String agentName();

    /** Builds the agent's prompt from the mas'alah, its evidence, and the loaded rule cards. */
    protected abstract String buildPrompt(MasalahContext context, List<RuleCard> applicableRuleCards);

    /** Parses the agent's structured output into scored findings. */
    protected abstract List<ExpertFinding> parseFindings(String agentOutput);

    @Override
    public final ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards) {
        AgentDefinition definition = agentRegistry.findByName(agentName())
                .orElseThrow(() -> new IllegalStateException(
                        "No Andalus agent registered under name: " + agentName()
                                + " - register one before using " + getClass().getSimpleName()));

        String prompt = buildPrompt(context, applicableRuleCards);
        AgentRequest request = AgentRequest.builder().prompt(prompt).build(); // verify field names - see class javadoc

        AgentExecution execution = agentExecutionService.create(definition, request, budget);
        String output = runAgentBlocking(execution);

        return new ExpertOpinionResult(id(), parseFindings(output));
    }

    /**
     * The genuinely unverified step: turning a started
     * {@link AgentExecution} into finished text output.
     * {@code AgentInstance} - a different type, used for registry/resource
     * state elsewhere in Andalus - exposes {@code outputs(): List<Artifact>},
     * which suggests {@code AgentExecution} may look similar, but that is
     * an inference, not a citation. Implement this against
     * {@code tech.kayys.andalus.execution.AgentExecution}'s real interface
     * before use.
     */
    protected String runAgentBlocking(AgentExecution execution) {
        throw new UnsupportedOperationException(
                "AgentExecution's run/result-retrieval API was not in the sources available to verify - "
                        + "implement this against tech.kayys.andalus.execution.AgentExecution directly");
    }
}
