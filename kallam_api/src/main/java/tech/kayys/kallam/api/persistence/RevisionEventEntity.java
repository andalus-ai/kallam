package tech.kayys.kallam.api.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;

/**
 * Append-only row per {@code RevisionEvent}. {@code blockJson} holds the
 * serialised {@link tech.kayys.kallam.api.wire.BlockDto} for proposal
 * events, null for pure actions (accept/reject/etc).
 *
 * The unique constraint on (session_id, sequence) is what makes sequence
 * assignment safe under concurrent appends to the same session: a losing
 * writer gets a constraint violation and retries, rather than silently
 * overwriting another event's slot.
 *
 * Production note: {@code blockJson} should use a real {@code jsonb}
 * column (e.g. via Hibernate's {@code @JdbcTypeCode(SqlTypes.JSON)}) once
 * the target Hibernate/Postgres version is pinned - kept as text here to
 * stay dependency-version-agnostic in the scaffold.
 */
@Entity
@Table(
        name = "revision_event",
        indexes = @Index(name = "ix_revision_event_session", columnList = "session_id, sequence"),
        uniqueConstraints = @UniqueConstraint(
                name = "uq_revision_event_session_sequence", columnNames = {"session_id", "sequence"}))
public class RevisionEventEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id", length = 36)
    public String id;

    @Column(name = "session_id", nullable = false, length = 64)
    public String sessionId;

    @Column(name = "sequence", nullable = false)
    public long sequence;

    @Column(name = "action_type", nullable = false, length = 32)
    public String actionType;

    @Column(name = "actor", nullable = false, length = 16)
    public String actor;

    @Column(name = "actor_id", nullable = false, length = 64)
    public String actorId;

    @Column(name = "affected_block_id", nullable = false, length = 64)
    public String affectedBlockId;

    @Lob
    @Column(name = "block_json")
    public String blockJson;

    @Column(name = "occurred_at", nullable = false)
    public Instant timestamp;

    @Column(name = "note", length = 2000)
    public String note;

    @Column(name = "completeness")
    public Double completeness;

    @Column(name = "ready_for_scholar_review")
    public Boolean readyForScholarReview;
}
