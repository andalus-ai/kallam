package tech.kayys.kallam.api.domain;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tech.kayys.kallam.api.domain.block.Block;
import tech.kayys.kallam.api.domain.event.RevisionActionType;
import tech.kayys.kallam.api.domain.event.RevisionActor;
import tech.kayys.kallam.api.domain.event.RevisionEvent;

/**
 * Pure fold: {@code List<RevisionEvent>} (in sequence order) in,
 * {@link ResearchSession} out. No I/O, nothing framework-specific - trivial
 * to unit test with a hand-built event list, and it is the one place that
 * knows how a chain of superseding block versions collapses into "the
 * current block" for a section.
 *
 * <p>Reused for both the live session (fold the full stream) and "time
 * travel" (fold a prefix of the stream up to a target sequence) - see
 * {@link tech.kayys.kallam.api.application.ResearchSessionService}.
 */
@ApplicationScoped
public class ResearchSessionProjector {

    public ResearchSession project(String sessionId, String masalahQuestion, List<RevisionEvent> events) {
        // chainKey identifies a logical section across all its revised versions.
        // A block's own id maps to the chain it belongs to, so a later event
        // referencing an older block id (via supersedesBlockId or
        // affectedBlockId) still resolves to the right chain.
        Map<String, String> chainKeyByBlockId = new LinkedHashMap<>();
        Map<String, Block> headByChainKey = new LinkedHashMap<>();

        ResearchSessionStatus status = ResearchSessionStatus.RESEARCHING;
        Double completeness = 0.0;
        long latestSequence = 0;

        for (RevisionEvent event : events) {
            latestSequence = event.sequence();
            if (event.completeness() != null) {
                completeness = event.completeness();
            }

            if (event.block() != null) {
                applyProposal(event, chainKeyByBlockId, headByChainKey);
            } else {
                applyAction(event, chainKeyByBlockId, headByChainKey);
            }

            status = nextStatus(status, event);
        }
        return new ResearchSession(
                sessionId,
                masalahQuestion,
                new ArrayList<>(headByChainKey.values()),
                completeness,
                status,
                latestSequence);
    }

    private void applyProposal(
            RevisionEvent event,
            Map<String, String> chainKeyByBlockId,
            Map<String, Block> headByChainKey) {
        Block proposed = event.block();
        String supersedes = proposed.supersedesBlockId();

        String chainKey = supersedes != null && chainKeyByBlockId.containsKey(supersedes)
                ? chainKeyByBlockId.get(supersedes)
                : proposed.id();

        chainKeyByBlockId.put(proposed.id(), chainKey);
        // LinkedHashMap.put on an existing key keeps its original iteration
        // position, so a revised block stays where the section first
        // appeared instead of jumping to the bottom of the workspace.
        headByChainKey.put(chainKey, proposed);
    }

    private void applyAction(
            RevisionEvent event,
            Map<String, String> chainKeyByBlockId,
            Map<String, Block> headByChainKey) {
        String chainKey = chainKeyByBlockId.get(event.affectedBlockId());
        if (chainKey == null) {
            return; // action on a block this projection hasn't seen - ignore defensively
        }
        switch (event.actionType()) {
            case CLAIM_REJECTED -> headByChainKey.remove(chainKey);
            // CLAIM_ACCEPTED, HADITH_GRADING_DISPUTED, SCHOLAR_DETERMINATION etc.
            // don't change which block is current - they're recorded for the
            // timeline and for status transitions below.
            default -> { }
        }
    }

    private ResearchSessionStatus nextStatus(ResearchSessionStatus current, RevisionEvent event) {
        if (current == ResearchSessionStatus.FINALIZED) {
            return current; // terminal
        }
        if (event.actor() == RevisionActor.SCHOLAR) {
            if (event.actionType() == RevisionActionType.SCHOLAR_DETERMINATION) {
                return ResearchSessionStatus.FINALIZED;
            }
            return ResearchSessionStatus.UNDER_SCHOLAR_REVIEW;
        }
        if (current == ResearchSessionStatus.UNDER_SCHOLAR_REVIEW) {
            return current; // a scholar is already engaged; AI checkpoints don't downgrade that
        }
        if (Boolean.TRUE.equals(event.readyForScholarReview())) {
            return ResearchSessionStatus.READY_FOR_REVIEW;
        }
        return current;
    }
}
