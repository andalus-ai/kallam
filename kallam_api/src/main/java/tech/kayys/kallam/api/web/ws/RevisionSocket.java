package tech.kayys.kallam.api.web.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.PathParam;
import io.quarkus.websockets.next.WebSocket;
import jakarta.inject.Inject;
import tech.kayys.kallam.api.domain.event.RevisionEventPublisher;
import tech.kayys.kallam.api.wire.RevisionEventDtoMapper;

/**
 * Matches the Flutter client's
 * {@code GET/WS /api/v1/research-sessions/{sessionId}/revisions} - pushes
 * every newly-appended {@code RevisionEvent} for that session as JSON,
 * as it happens. History is fetched over REST; this socket only ever
 * emits what's new, per {@link RevisionEventPublisher}'s contract.
 */
@WebSocket(path = "/api/v1/research-sessions/{sessionId}/revisions")
public class RevisionSocket {

    @Inject
    RevisionEventPublisher publisher;

    @Inject
    RevisionEventDtoMapper eventDtoMapper;

    @Inject
    ObjectMapper objectMapper;

    @OnOpen
    public io.smallrye.mutiny.Multi<String> onOpen(@PathParam String sessionId) {
        return publisher.subscribe(sessionId)
                .map(eventDtoMapper::toDto)
                .map(this::toJson);
    }

    private String toJson(tech.kayys.kallam.api.wire.RevisionEventDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialise revision event", e);
        }
    }
}
