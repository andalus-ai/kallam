package tech.kayys.kallam.api.domain.block;

import java.util.List;

public record MadhhabPosition(
        String school,
        String position,
        List<String> evidenceRefs,
        String usulBasis) {

    public MadhhabPosition {
        evidenceRefs = evidenceRefs == null ? List.of() : List.copyOf(evidenceRefs);
    }
}
