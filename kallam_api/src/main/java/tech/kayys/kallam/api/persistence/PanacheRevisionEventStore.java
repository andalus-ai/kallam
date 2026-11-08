package tech.kayys.kallam.api.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import tech.kayys.kallam.api.domain.block.Block;
import tech.kayys.kallam.api.domain.event.RevisionActionType;
import tech.kayys.kallam.api.domain.event.RevisionActor;
import tech.kayys.kallam.api.domain.event.RevisionEvent;
import tech.kayys.kallam.api.domain.event.RevisionEventReader;
import tech.kayys.kallam.api.domain.event.RevisionEventWriter;
import tech.kayys.kallam.api.wire.BlockDto;
import tech.kayys.kallam.api.wire.BlockDtoMapper;

/**
 * The only adapter in this codebase that knows events live in Postgres.
 * Everything above {@link RevisionEventWriter}/{@link RevisionEventReader}
 * - the projector, the service, the REST resource - is unaware of this
 * class; swapping storage engines means writing a new implementation of
 * those two interfaces and changing one CDI wiring point.
 */
@ApplicationScoped
public class PanacheRevisionEventStore implements RevisionEventWriter, RevisionEventReader {

    private final BlockDtoMapper blockDtoMapper;
    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    public PanacheRevisionEventStore(
            BlockDtoMapper blockDtoMapper, ObjectMapper objectMapper, EntityManager entityManager) {
        this.blockDtoMapper = blockDtoMapper;
        this.objectMapper = objectMapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public RevisionEvent append(RevisionEvent event) {
        var entity = new RevisionEventEntity();
        entity.id = event.id();
        entity.sessionId = event.sessionId();
        entity.sequence = event.sequence();
        entity.actionType = event.actionType().name();
        entity.actor = event.actor().name();
        entity.actorId = event.actorId();
        entity.affectedBlockId = event.affectedBlockId();
        entity.timestamp = event.timestamp();
        entity.note = event.note();
        entity.completeness = event.completeness();
        entity.readyForScholarReview = event.readyForScholarReview();
        entity.blockJson = event.block() == null ? null : toJson(blockDtoMapper.toDto(event.block()));
        entity.persist();
        // The unique (session_id, sequence) constraint surfaces a
        // ConstraintViolationException here if two writers raced for the
        // same slot - callers are expected to retry with a fresh
        // nextSequence() on that failure.
        return event;
    }

    @Override
    public List<RevisionEvent> readAll(String sessionId) {
        return RevisionEventEntity
                .find("sessionId", Sort.by("sequence"), sessionId)
                .<RevisionEventEntity>list()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<RevisionEvent> readUpTo(String sessionId, long upToSequenceInclusive) {
        return RevisionEventEntity
                .find("sessionId = ?1 and sequence <= ?2", Sort.by("sequence"), sessionId, upToSequenceInclusive)
                .<RevisionEventEntity>list()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public long nextSequence(String sessionId) {
        Long max = entityManager
                .createQuery(
                        "select max(e.sequence) from RevisionEventEntity e where e.sessionId = :sessionId",
                        Long.class)
                .setParameter("sessionId", sessionId)
                .getSingleResult();
        return (max == null ? 0 : max) + 1;
    }

    private RevisionEvent toDomain(RevisionEventEntity entity) {
        Block block = entity.blockJson == null ? null : blockDtoMapper.fromDto(fromJson(entity.blockJson));
        return new RevisionEvent(
                entity.id,
                entity.sessionId,
                entity.sequence,
                RevisionActionType.valueOf(entity.actionType),
                RevisionActor.valueOf(entity.actor),
                entity.actorId,
                entity.affectedBlockId,
                block,
                entity.timestamp,
                entity.note,
                entity.completeness,
                entity.readyForScholarReview);
    }

    private String toJson(BlockDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialise block payload", e);
        }
    }

    private BlockDto fromJson(String json) {
        try {
            return objectMapper.readValue(json, BlockDto.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to deserialise block payload", e);
        }
    }
}
