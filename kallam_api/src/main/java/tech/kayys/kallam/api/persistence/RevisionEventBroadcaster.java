package tech.kayys.kallam.api.persistence;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.BroadcastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import tech.kayys.kallam.api.domain.event.RevisionEvent;
import tech.kayys.kallam.api.domain.event.RevisionEventPublisher;

/**
 * In-memory pub/sub for live revision events, one {@link BroadcastProcessor}
 * per session. Deliberately separate from persistence: a WebSocket
 * subscriber gets "what happens from now on", not history (the REST GET
 * covers history) - conflating the two would make the socket handler also
 * a replay engine.
 *
 * A single-instance in-memory broadcaster is the honest scaffold answer;
 * running more than one API instance needs this backed by a shared bus
 * (e.g. Postgres LISTEN/NOTIFY or a message broker) so events published on
 * one instance reach subscribers connected to another.
 */
@ApplicationScoped
public class RevisionEventBroadcaster implements RevisionEventPublisher {

    private final ConcurrentMap<String, BroadcastProcessor<RevisionEvent>> bySession = new ConcurrentHashMap<>();

    @Override
    public Multi<RevisionEvent> subscribe(String sessionId) {
        return processorFor(sessionId);
    }

    @Override
    public void publish(RevisionEvent event) {
        processorFor(event.sessionId()).onNext(event);
    }

    private BroadcastProcessor<RevisionEvent> processorFor(String sessionId) {
        return bySession.computeIfAbsent(sessionId, id -> BroadcastProcessor.create());
    }
}
