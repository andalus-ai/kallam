package tech.kayys.kallam.api.application;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.eclipse.microprofile.context.ManagedExecutor;
import tech.kayys.kallam.api.domain.event.RevisionActor;
import tech.kayys.kallam.api.domain.event.RevisionEvent;
import tech.kayys.kallam.api.domain.event.RevisionEventPublisher;
import tech.kayys.kallam.api.domain.event.RevisionEventReader;
import tech.kayys.kallam.api.domain.event.RevisionEventWriter;
import tech.kayys.kallam.istinbat.IstinbatCoordinator;
import tech.kayys.kallam.istinbat.ResearchReport;
import tech.kayys.kallam.istinbat.ResearchRequest;

/**
 * The bridge to kallam-core: runs {@link IstinbatCoordinator} off the
 * request thread and turns its output into revision events, using the same
 * append path a scholar's accept/reject action uses. The rest of this
 * module - projector, REST resource, WebSocket - has no idea the AI agent
 * and a human reviewer are two different kinds of writer to the same log.
 */
@ApplicationScoped
public class AsyncResearchRunner {

    private final IstinbatCoordinator coordinator;
    private final ReportToBlocksMapper reportToBlocksMapper;
    private final RevisionEventWriter eventWriter;
    private final RevisionEventReader eventReader;
    private final RevisionEventPublisher eventPublisher;
    private final ManagedExecutor executor;

    public AsyncResearchRunner(
            IstinbatCoordinator coordinator,
            ReportToBlocksMapper reportToBlocksMapper,
            RevisionEventWriter eventWriter,
            RevisionEventReader eventReader,
            RevisionEventPublisher eventPublisher,
            ManagedExecutor executor) {
        this.coordinator = coordinator;
        this.reportToBlocksMapper = reportToBlocksMapper;
        this.eventWriter = eventWriter;
        this.eventReader = eventReader;
        this.eventPublisher = eventPublisher;
        this.executor = executor;
    }

    /** Kicks off the first research pass for a freshly created session. Fire-and-forget from the caller's point of view - progress shows up as events. */
    public CompletableFuture<Void> runInitialResearch(String sessionId, ResearchRequest request) {
        return CompletableFuture
                .supplyAsync(() -> coordinator.research(request), executor)
                .thenAccept(report -> appendReportAsEvents(sessionId, report));
    }

    /**
     * Re-runs research after a scholar's revision request and supersedes
     * only the block whose type matches {@code targetBlockType}.
     *
     * <p><b>Known simplification:</b> kallam-core v0.1 re-runs the whole
     * {@code IstinbatCoordinator} pipeline rather than regenerating a
     * single section - there is no scoped "redo just the qiyas" entry
     * point yet. Once {@code ResearchSpecialist} supports a targeted
     * re-run, this becomes a narrower, cheaper call and stops discarding
     * the rest of the fresh report.
     */
    public CompletableFuture<Void> reviseBlock(
            String sessionId,
            ResearchRequest request,
            String targetBlockId,
            tech.kayys.kallam.api.domain.block.BlockType targetBlockType) {
        return CompletableFuture
                .supplyAsync(() -> coordinator.research(request), executor)
                .thenAccept(report -> {
                    long sequence = eventReader.nextSequence(sessionId);
                    for (var proposal : reportToBlocksMapper.toBlocks(report)) {
                        if (proposal.block().type() != targetBlockType) {
                            continue; // discard the rest of the re-run - see javadoc above
                        }
                        var superseding = withSupersedes(proposal.block(), targetBlockId);
                        var event = new RevisionEvent(
                                newEventId(), sessionId, sequence++, proposal.actionType(),
                                RevisionActor.AI_AGENT, "kallam-agent", superseding.id(), superseding,
                                Instant.now(), null,
                                report.critique().completeness(), report.critique().readyForScholarReview());
                        eventWriter.append(event);
                        eventPublisher.publish(event);
                    }
                });
    }

    private void appendReportAsEvents(String sessionId, ResearchReport report) {
        long sequence = eventReader.nextSequence(sessionId);
        List<ReportToBlocksMapper.ProposedBlock> proposals = reportToBlocksMapper.toBlocks(report);

        for (int i = 0; i < proposals.size(); i++) {
            var proposal = proposals.get(i);
            boolean isLast = i == proposals.size() - 1;
            var event = new RevisionEvent(
                    newEventId(), sessionId, sequence++, proposal.actionType(),
                    RevisionActor.AI_AGENT, "kallam-agent", proposal.block().id(), proposal.block(),
                    Instant.now(), null,
                    isLast ? report.critique().completeness() : null,
                    isLast ? report.critique().readyForScholarReview() : null);
            eventWriter.append(event);
            eventPublisher.publish(event);
        }
    }

    private tech.kayys.kallam.api.domain.block.Block withSupersedes(
            tech.kayys.kallam.api.domain.block.Block block, String supersedesBlockId) {
        // Blocks are immutable records; re-stamping supersedesBlockId means
        // rebuilding the same-shaped record with a new id. A small, honest
        // amount of per-type boilerplate rather than reflection magic.
        return switch (block) {
            case tech.kayys.kallam.api.domain.block.TaswirBlock b -> new tech.kayys.kallam.api.domain.block.TaswirBlock(
                    newBlockId(), b.revisionId(), b.epistemicStatus(), supersedesBlockId,
                    b.subject(), b.definition(), b.essentialCharacteristics(), b.unresolvedFactualQuestions());
            case tech.kayys.kallam.api.domain.block.EvidenceBlock b -> new tech.kayys.kallam.api.domain.block.EvidenceBlock(
                    newBlockId(), b.type(), b.revisionId(), b.epistemicStatus(), supersedesBlockId,
                    b.reference(), b.sourceText(), b.translation(), b.grading(), b.chainNarrators());
            case tech.kayys.kallam.api.domain.block.QiyasChainBlock b -> new tech.kayys.kallam.api.domain.block.QiyasChainBlock(
                    newBlockId(), b.revisionId(), b.epistemicStatus(), supersedesBlockId,
                    b.asl(), b.hukmAlAsl(), b.illah(), b.far(), b.tahqiqAlManat(), b.result());
            case tech.kayys.kallam.api.domain.block.IkhtilafBlock b -> new tech.kayys.kallam.api.domain.block.IkhtilafBlock(
                    newBlockId(), b.revisionId(), b.epistemicStatus(), supersedesBlockId,
                    b.positions(), b.agreedPoints(), b.disagreementReasons());
            case tech.kayys.kallam.api.domain.block.UnknownBlock b -> new tech.kayys.kallam.api.domain.block.UnknownBlock(
                    newBlockId(), b.revisionId(), b.epistemicStatus(), supersedesBlockId, b.rawPayload());
        };
    }

    private String newEventId() {
        return "rev_" + UUID.randomUUID();
    }

    private String newBlockId() {
        return "blk_" + UUID.randomUUID();
    }
}
