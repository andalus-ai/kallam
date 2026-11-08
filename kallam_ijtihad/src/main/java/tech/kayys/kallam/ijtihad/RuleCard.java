package tech.kayys.kallam.ijtihad;

/**
 * A methodology rule, kept as text - the same shape as this environment's
 * own SKILL.md files, or Andalus's SkillDefinition: a scoped, human-readable
 * document an agent reads and applies, not a compiled predicate. Ulama can
 * review and edit a rule card directly; that mattered more here than
 * squeezing every rule into boolean logic.
 *
 * <p>{@code tier} is null for cards that apply across tiers (e.g. general
 * expert methodology). {@code appliesWhen} is deliberately free text, not
 * a machine condition - matching a card to a situation is itself part of
 * the orchestrator's or expert's judgment, informed by {@code appliesWhen}
 * as a description rather than dispatched on it as a predicate.
 */
public record RuleCard(
        String id,
        EvidenceTier tier,
        RuleCategory category,
        String title,
        String appliesWhen,
        int priority,
        String madhhab,
        String body) {
}
