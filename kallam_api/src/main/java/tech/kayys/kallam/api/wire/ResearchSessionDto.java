package tech.kayys.kallam.api.wire;

import java.util.List;

public record ResearchSessionDto(
        String id,
        String masalahQuestion,
        double completeness,
        String status,
        List<BlockDto> blocks) {
}
