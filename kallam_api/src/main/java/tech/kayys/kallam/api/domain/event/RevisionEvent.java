package tech.kayys.kallam.api.domain.event;

import java.time.Instant;
import tech.kayys.kallam.api.domain.block.Block;

/**
 * An immutable fact about something that happened to a research session.
 * A session's current state is always the fold of its revision events in
 * {@code sequence} order - never a value edited in place. That gives the
 * workspace its revision history and its "time travel" view for free, and
 * keeps provenance (source text vs scholarly interpretation vs AI
 * inference vs scholar determination) intact instead of silently
 * overwritten.
 *
 * <p>{@code block} carries the proposed content when {@code actionType} is
 * a proposal/revision (an AI agent or a specialist producing a new or
 * revised block); it is {@code null} for pure actions like accept/reject,
 * which only reference an existing block by id.
 *
 * <p>{@code completeness} and {@code readyForScholarReview} are a snapshot
 * of kallam-core's {@code ResearchCritique} at the moment this event was
 * recorded. Both are only ever set by system/AI checkpoints (after a
 * retrieval+critique round); scholar-authored events leave them
 * {@code null} and the projector keeps the most recent non-null values.
 */
public record RevisionEvent(
        String id,
        String sessionId,
        long sequence,
        RevisionActionType actionType,
        RevisionActor actor,
        String actorId,
        String affectedBlockId,
        Block block,
        Instant timestamp,
        String note,
        Double completeness,
        Boolean readyForScholarReview) {
}
