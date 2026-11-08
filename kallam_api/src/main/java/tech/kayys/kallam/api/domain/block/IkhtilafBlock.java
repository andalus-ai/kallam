package tech.kayys.kallam.api.domain.block;

import java.util.List;

/** The madhhab comparison table, plus *why* the schools diverge. */
public record IkhtilafBlock(
        String id,
        String revisionId,
        EpistemicStatus epistemicStatus,
        String supersedesBlockId,
        List<MadhhabPosition> positions,
        List<String> agreedPoints,
        List<String> disagreementReasons) implements Block {

    public IkhtilafBlock {
        positions = positions == null ? List.of() : List.copyOf(positions);
        agreedPoints = agreedPoints == null ? List.of() : List.copyOf(agreedPoints);
        disagreementReasons = disagreementReasons == null ? List.of() : List.copyOf(disagreementReasons);
    }

    @Override
    public BlockType type() {
        return BlockType.SCHOLARLY_POSITION;
    }
}
