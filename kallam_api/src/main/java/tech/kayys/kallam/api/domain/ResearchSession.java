package tech.kayys.kallam.api.domain;

import java.util.List;
import tech.kayys.kallam.api.domain.block.Block;

/**
 * Read model for one mas'alah under research: the question plus its
 * current, materialised set of blocks. Produced by folding a
 * {@link tech.kayys.kallam.api.domain.event.RevisionEvent} stream through
 * {@link ResearchSessionProjector} - never constructed by hand and never
 * mutated in place.
 */
public record ResearchSession(
        String id,
        String masalahQuestion,
        List<Block> blocks,
        double completeness,
        ResearchSessionStatus status,
        long latestSequence) {

    public ResearchSession {
        blocks = blocks == null ? List.of() : List.copyOf(blocks);
    }
}
