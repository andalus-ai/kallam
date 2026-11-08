package tech.kayys.kallam.api.domain.block;

/**
 * The full 7-value epistemic status from the blueprint (section 11). This
 * intentionally supersedes kallam-core's {@code EvidenceStatus} (5 values,
 * evidence-only) at the API boundary: every block - not only evidence -
 * needs a status, and ANALOGICAL_EXTENSION / FACTUAL_ASSUMPTION have no
 * equivalent on the core enum yet.
 *
 * {@link tech.kayys.kallam.api.application.ReportToBlocksMapper} maps
 * core's EvidenceStatus into this enum today. If EvidenceStatus is ever
 * promoted to match it 1:1 inside kallam-core, that mapper collapses to an
 * identity function - nothing else in this module needs to change.
 */
public enum EpistemicStatus {
    SOURCE_VERIFIED,
    SCHOLARLY_INTERPRETATION,
    INFERENCE,
    ANALOGICAL_EXTENSION,
    UNVERIFIED,
    DISPUTED,
    FACTUAL_ASSUMPTION
}
