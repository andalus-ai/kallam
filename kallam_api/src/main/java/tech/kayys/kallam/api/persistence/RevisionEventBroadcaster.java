package tech.kayys.kallam.api.persistence;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.SubmissionPublisher;
import tech.kayys.kallam.api.domain.event.RevisionEvent;
import tech.kayys.kallam.api.domain.event.RevisionEventPublisher;

/**
 * In-memory pub/sub for live revision events, one publisher per session.
 * A subscriber receives events published after subscription; persisted
 * history remains the responsibility of the REST/session query path.
 *
 * This is intentionally a single-instance implementation. Multiple API
 * instances require a shared event bus so a publication on one instance
 * reaches WebSocket subscribers connected to another instance.
 */
@ApplicationScoped
public class RevisionEventBroadcaster implements RevisionEventPublisher {

    private final ConcurrentMap<String, SubmissionPublisher<RevisionEvent>> bySession =
            new ConcurrentHashMap<>();

    @Override
    public Multi<RevisionEvent> subscribe(String sessionId) {
        return Multi.createFrom().publisher(publisherFor(sessionId));
    }

    @Override
    public void publish(RevisionEvent event) {
        publisherFor(event.sessionId()).submit(event);
    }

    private SubmissionPublisher<RevisionEvent> publisherFor(String sessionId) {
        return bySession.computeIfAbsent(sessionId, ignored -> new SubmissionPublisher<>());
    }
}
