package tech.kayys.kallam.api.wire;

import jakarta.enterprise.context.ApplicationScoped;
import tech.kayys.kallam.api.domain.event.RevisionEvent;

@ApplicationScoped
public class RevisionEventDtoMapper {

    public RevisionEventDto toDto(RevisionEvent event) {
        return new RevisionEventDto(
                event.id(),
                event.sessionId(),
                event.sequence(),
                event.actionType().name(),
                event.actor().name(),
                event.actorId(),
                event.affectedBlockId(),
                event.timestamp().toString(),
                event.note());
    }
}
