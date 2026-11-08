package tech.kayys.kallam.ijtihad;

import java.util.List;
import java.util.Optional;

/**
 * Resolves conflicting findings within a single tier. Returning
 * {@link Optional#empty()} means "this conflict needs interpretive
 * judgment I can't perform deterministically" - not "no conflict exists".
 * Callers must treat empty as an escalation to scholar review, never as a
 * silent pass-through. See {@code StructuredTarjihAdjudicator} for the
 * honest boundary between what's actually a lookup and what genuinely
 * isn't.
 */
public interface TarjihAdjudicator {
    Optional<TarjihResolution> adjudicate(
            EvidenceTier tier,
            List<ExpertFinding> conflicting,
            MasalahContext context,
            List<RuleCard> applicableTarjihCards);
}
