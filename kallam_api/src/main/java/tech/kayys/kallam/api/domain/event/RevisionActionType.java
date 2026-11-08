package tech.kayys.kallam.api.domain.event;

public enum RevisionActionType {
    /** AI agent proposed a new block, or a revision of an existing one. */
    EVIDENCE_ADDED,
    HADITH_GRADING_DISPUTED,
    CLAIM_ACCEPTED,
    CLAIM_REJECTED,
    QIYAS_REVISED,
    BLOCK_SUPERSEDED,
    SCHOLAR_DETERMINATION,
    /**
     * A scholar asked the agent to rework a block, with {@code note}
     * carrying the instruction. Added after the Flutter client's first
     * pass shipped without it - the Dart mirror
     * (domain/entities/revision.dart RevisionActionType, plus the
     * icon/label switch in revision_timeline_panel.dart) needs the same
     * addition to stay in sync.
     */
    REVISION_REQUESTED
}
