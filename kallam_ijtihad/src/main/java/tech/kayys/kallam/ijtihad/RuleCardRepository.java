package tech.kayys.kallam.ijtihad;

import java.util.List;
import java.util.Optional;

/**
 * Read access to rule cards. The reference implementation
 * ({@code filesystem.FileSystemRuleCardRepository}) loads Markdown files
 * with YAML frontmatter from the classpath - deliberately the same
 * loading model as this environment's own skills, so ulama can maintain
 * rule cards as plain text files under version control.
 *
 * <p>A production deployment should back this with Andalus's
 * {@code SkillDefinitionStore} (andalus-skill-runtime) instead - it already
 * has versioning, lifecycle state, and deployment workflow for exactly
 * this kind of text artifact. This interface is the seam that makes that
 * swap possible without touching {@link IstinbatOrchestrator}.
 */
public interface RuleCardRepository {
    List<RuleCard> findApplicable(EvidenceTier tier, RuleCategory category);

    /**
     * Same as the two-arg overload, but also includes cards scoped to
     * {@code madhhab} (in addition to the always-included "common" cards).
     * Pass {@code null} to get common-only, matching the two-arg behaviour.
     */
    List<RuleCard> findApplicable(EvidenceTier tier, RuleCategory category, String madhhab);

    List<RuleCard> findByCategory(RuleCategory category);

    Optional<RuleCard> findById(String id);
}
