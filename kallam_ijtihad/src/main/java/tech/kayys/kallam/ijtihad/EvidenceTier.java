package tech.kayys.kallam.ijtihad;

/**
 * The classical priority ordering of usul al-fiqh evidence sources.
 * Declaration order IS the priority order - {@link TierPriorityResolver}
 * iterates {@code values()} and stops at the first tier that resolves the
 * question, so this enum's ordering is not decorative, it's load-bearing.
 *
 * <p>This ordering (Qur'an, then Sunnah, then ijma', then qiyas as last
 * resort) is the majority position across the four Sunni madhahib at the
 * top level; where madhahib genuinely diverge is *within* a tier (e.g.
 * how much weight istihsan or 'amal ahl al-Madinah carries) and at the
 * conditions for validity within a tier, not at this top-level ordering
 * itself. Encode madhhab divergence in rule cards scoped to a tier, not by
 * reordering this enum.
 */
public enum EvidenceTier {
    QURAN,
    HADITH,
    IJMA_SAHABAH,
    QIYAS
}
