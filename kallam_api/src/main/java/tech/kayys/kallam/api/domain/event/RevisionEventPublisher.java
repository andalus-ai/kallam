package tech.kayys.kallam.api.domain.event;

import io.smallrye.mutiny.Multi;

/**
 * Live feed of newly-appended events for a session, consumed by
 * {@code RevisionSocket}. Kept separate from the store so the WebSocket
 * transport depends only on "give me a stream", not on persistence
 * details (dependency inversion at the transport boundary too).
 */
public interface RevisionEventPublisher {
    Multi<RevisionEvent> subscribe(String sessionId);

    void publish(RevisionEvent event);
}
