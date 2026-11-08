package tech.kayys.kallam.api.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;
import tech.kayys.kallam.api.domain.ResearchSession;
import tech.kayys.kallam.api.domain.ResearchSessionProjector;
import tech.kayys.kallam.api.domain.event.RevisionActionType;
import tech.kayys.kallam.api.domain.event.RevisionActor;
import tech.kayys.kallam.api.domain.event.RevisionEvent;
import tech.kayys.kallam.api.domain.event.RevisionEventPublisher;
import tech.kayys.kallam.api.domain.event.RevisionEventReader;
import tech.kayys.kallam.api.domain.event.RevisionEventWriter;
import tech.kayys.kallam.api.persistence.ResearchSessionHeaderEntity;
import tech.kayys.kallam.istinbat.ResearchRequest;

/**
 * Application service: the one place that turns a scholar's intent
 * ("accept this block", "start research on this question") into a
 * {@link RevisionEvent}, appends it, publishes it, and answers queries by
 * projecting the log. The REST resource and the WebSocket endpoint are
 * both thin adapters over this class - neither talks to the event store
 * or the projector directly.
 */
@ApplicationScoped
public class ResearchSessionService {

    private final RevisionEventWriter eventWriter;
    private final RevisionEventReader eventReader;
    private final RevisionEventPublisher eventPublisher;
    private final ResearchSessionProjector projector;
    private final AsyncResearchRunner researchRunner;

    public ResearchSessionService(
            RevisionEventWriter eventWriter,
            RevisionEventReader eventReader,
            RevisionEventPublisher eventPublisher,
            ResearchSessionProjector projector,
            AsyncResearchRunner researchRunner) {
        this.eventWriter = eventWriter;
        this.eventReader = eventReader;
        this.eventPublisher = eventPublisher;
        this.projector = projector;
        this.researchRunner = researchRunner;
    }

    @Transactional
    public String createSession(String question, String language) {
        String sessionId = "sess_" + UUID.randomUUID();
        var header = new ResearchSessionHeaderEntity();
        header.id = sessionId;
        header.masalahQuestion = question;
        header.createdAt = Instant.now();
        header.persist();

        researchRunner.runInitialResearch(sessionId, new ResearchRequest(sessionId, question, language, null));
        return sessionId;
    }

    public ResearchSession loadSession(String sessionId) {
        String question = headerOrThrow(sessionId).masalahQuestion;
        return projector.project(sessionId, question, eventReader.readAll(sessionId));
    }

    public ResearchSession loadSessionAtRevision(String sessionId, String revisionId) {
        String question = headerOrThrow(sessionId).masalahQuestion;
        long targetSequence = eventReader.readAll(sessionId).stream()
                .filter(e -> e.id().equals(revisionId))
                .findFirst()
                .map(RevisionEvent::sequence)
                .orElseThrow(() -> new NoSuchElementException("No such revision: " + revisionId));
        return projector.project(sessionId, question, eventReader.readUpTo(sessionId, targetSequence));
    }

    public RevisionEvent acceptBlock(String sessionId, String blockId, String scholarId) {
        return recordScholarAction(sessionId, blockId, scholarId, RevisionActionType.CLAIM_ACCEPTED, null);
    }

    public RevisionEvent rejectBlock(String sessionId, String blockId, String scholarId, String note) {
        return recordScholarAction(sessionId, blockId, scholarId, RevisionActionType.CLAIM_REJECTED, note);
    }

    public RevisionEvent disputeHadithGrading(String sessionId, String blockId, String scholarId, String note) {
        return recordScholarAction(sessionId, blockId, scholarId, RevisionActionType.HADITH_GRADING_DISPUTED, note);
    }

    public RevisionEvent recordScholarDetermination(String sessionId, String scholarId, String note) {
        return recordScholarAction(sessionId, "session", scholarId, RevisionActionType.SCHOLAR_DETERMINATION, note);
    }

    public RevisionEvent requestRevision(String sessionId, String blockId, String scholarId, String instruction) {
        var current = loadSession(sessionId);
        var target = current.blocks().stream().filter(b -> b.id().equals(blockId)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("No such block: " + blockId));

        var event = recordScholarAction(sessionId, blockId, scholarId, RevisionActionType.REVISION_REQUESTED, instruction);

        researchRunner.reviseBlock(
                sessionId,
                new ResearchRequest(sessionId, current.masalahQuestion(), "ar", null),
                blockId,
                target.type());
        return event;
    }

    private RevisionEvent recordScholarAction(
            String sessionId, String blockId, String scholarId, RevisionActionType type, String note) {
        var event = new RevisionEvent(
                "rev_" + UUID.randomUUID(),
                sessionId,
                eventReader.nextSequence(sessionId),
                type,
                RevisionActor.SCHOLAR,
                scholarId,
                blockId,
                null,
                Instant.now(),
                note,
                null,
                null);
        eventWriter.append(event);
        eventPublisher.publish(event);
        return event;
    }

    private ResearchSessionHeaderEntity headerOrThrow(String sessionId) {
        ResearchSessionHeaderEntity header = ResearchSessionHeaderEntity.findById(sessionId);
        if (header == null) {
            throw new NoSuchElementException("No such research session: " + sessionId);
        }
        return header;
    }
}
