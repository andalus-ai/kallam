package tech.kayys.kallam.api.domain.block;

/**
 * An explicit, auditable qiyas chain: asl, hukm al-asl, illah, far, and
 * tahqiq al-manat as separate fields, plus a validity result - never a
 * paragraph of prose the reasoning happened inside of.
 */
public record QiyasChainBlock(
        String id,
        String revisionId,
        EpistemicStatus epistemicStatus,
        String supersedesBlockId,
        String asl,
        String hukmAlAsl,
        String illah,
        String far,
        String tahqiqAlManat,
        String result) implements Block {

    @Override
    public BlockType type() {
        return BlockType.QIYAS_CHAIN;
    }
}
