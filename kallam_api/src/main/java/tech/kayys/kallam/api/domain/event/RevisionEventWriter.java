package tech.kayys.kallam.api.domain.event;

/** Appends events to a session's log. The only mutation this bounded context allows. */
public interface RevisionEventWriter {
    RevisionEvent append(RevisionEvent event);
}
