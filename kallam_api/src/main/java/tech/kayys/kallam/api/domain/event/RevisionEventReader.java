package tech.kayys.kallam.api.domain.event;

import java.util.List;

/**
 * Read access to a session's event log, in sequence order.
 * Split from {@link RevisionEventWriter} and {@link RevisionEventPublisher}
 * so a consumer that only ever replays history (e.g. an export job) depends
 * on nothing it doesn't use.
 */
public interface RevisionEventReader {
    List<RevisionEvent> readAll(String sessionId);

    /** Events with {@code sequence <= upToSequenceInclusive}, for time travel. */
    List<RevisionEvent> readUpTo(String sessionId, long upToSequenceInclusive);

    long nextSequence(String sessionId);
}
