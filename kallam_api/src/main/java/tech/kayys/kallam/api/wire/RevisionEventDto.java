package tech.kayys.kallam.api.wire;

public record RevisionEventDto(
        String id,
        String sessionId,
        long sequence,
        String actionType,
        String actor,
        String actorId,
        String affectedBlockId,
        String timestamp,
        String note) {
}
