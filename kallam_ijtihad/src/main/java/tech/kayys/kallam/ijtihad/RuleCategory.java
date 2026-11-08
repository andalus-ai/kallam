package tech.kayys.kallam.ijtihad;

public enum RuleCategory {
    /** Gates whether a tier should be consulted at all (e.g. "qiyas is last resort"). */
    TIER_GATING,
    /** Resolves conflicting opinions within or across tiers. */
    TARJIH,
    /** A qa'idah fiqhiyyah (legal maxim) and its scope/exceptions. */
    QAWAID,
    /** An expert's domain scope and methodology - what it does and doesn't judge. */
    EXPERT_METHODOLOGY,
    /** Formal validity conditions (e.g. arkan al-qiyas). */
    VALIDITY_CONDITIONS
}
