package tech.kayys.kallam.api.domain.block;

import java.util.List;

/**
 * A single piece of Quran or hadith evidence. {@code type} distinguishes
 * which - both wire types share this one record, matching the Flutter
 * client's EvidenceBlock.
 */
public record EvidenceBlock(
        String id,
        BlockType type,
        String revisionId,
        EpistemicStatus epistemicStatus,
        String supersedesBlockId,
        String reference,
        String sourceText,
        String translation,
        String grading,
        List<String> chainNarrators) implements Block {

    public EvidenceBlock {
        if (type != BlockType.QURAN_EVIDENCE && type != BlockType.HADITH_EVIDENCE) {
            throw new IllegalArgumentException("EvidenceBlock type must be QURAN_EVIDENCE or HADITH_EVIDENCE");
        }
        chainNarrators = chainNarrators == null ? List.of() : List.copyOf(chainNarrators);
    }
}
