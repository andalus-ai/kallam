package tech.kayys.kallam.ijtihad;

import java.util.List;

/**
 * One discipline's specialist analysis of a mas'alah - hadith science,
 * balaghah, tarikh/sirah, madhhab comparison, taḥqiq al-manat, and so on.
 * Deliberately the same shape as Andalus guardrails'
 * {@code GuardrailDetectorPlugin} (id/category/applicability + a
 * findings-producing analyze call): this codebase already has a proven
 * plugin/orchestrator pattern for "several specialists each produce scored
 * findings, something else combines them" - reusing it here rather than
 * inventing a parallel abstraction.
 *
 * <p>An expert asserts scored claims; it does not decide which tier
 * governs or how conflicting claims are weighed - that's
 * {@link IstinbatOrchestrator}'s job, kept separate so no single expert's
 * output can silently short-circuit the priority hierarchy.
 */
public interface IjtihadExpertPlugin {

    String id();

    String name();

    /** Which tiers this expert's findings are relevant to - most experts touch more than one. */
    EvidenceTier[] applicableTiers();

    /**
     * Rule card ids this expert's methodology is grounded in for the given
     * context - used by the orchestrator to attach provenance to every
     * finding, and by callers to load those cards into the expert's
     * context before invoking it. Takes {@link MasalahContext} so an
     * expert can include madhhab-specific cards (e.g. a Hanafi expert
     * methodology addendum) alongside its common-methodology cards,
     * rather than always returning a fixed list.
     */
    List<String> methodologyRuleCardIds(MasalahContext context);

    ExpertOpinionResult analyze(MasalahContext context, List<RuleCard> applicableRuleCards);
}
