package tech.kayys.kallam.api.domain.block;

import java.util.List;

public record TaswirBlock(
        String id,
        String revisionId,
        EpistemicStatus epistemicStatus,
        String supersedesBlockId,
        String subject,
        String definition,
        List<String> essentialCharacteristics,
        List<String> unresolvedFactualQuestions) implements Block {

    public TaswirBlock {
        essentialCharacteristics = essentialCharacteristics == null
                ? List.of() : List.copyOf(essentialCharacteristics);
        unresolvedFactualQuestions = unresolvedFactualQuestions == null
                ? List.of() : List.copyOf(unresolvedFactualQuestions);
    }

    @Override
    public BlockType type() {
        return BlockType.TASWIR_AL_MASALAH;
    }
}
