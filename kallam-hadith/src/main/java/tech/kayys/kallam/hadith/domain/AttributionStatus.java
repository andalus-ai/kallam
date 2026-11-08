package tech.kayys.kallam.hadith.domain;

/** Verification state of the attribution, not the strength of the grading itself. */
public enum AttributionStatus {
    UNVERIFIED,
    SOURCE_LOCATED,
    ATTRIBUTION_VERIFIED,
    DISPUTED
}
