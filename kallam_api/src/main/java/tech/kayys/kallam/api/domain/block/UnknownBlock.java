package tech.kayys.kallam.api.domain.block;

import java.util.Map;

/**
 * Fallback for a block type this server build doesn't have a typed record
 * for yet - lets a newer research plugin/specialist ship block content
 * ahead of a matching record here without breaking projection.
 */
public record UnknownBlock(
        String id,
        String revisionId,
        EpistemicStatus epistemicStatus,
        String supersedesBlockId,
        Map<String, Object> rawPayload) implements Block {

    public UnknownBlock {
        rawPayload = rawPayload == null ? Map.of() : Map.copyOf(rawPayload);
    }

    @Override
    public BlockType type() {
        return BlockType.UNKNOWN;
    }
}
