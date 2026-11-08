package tech.kayys.kallam.api.wire;

import jakarta.enterprise.context.ApplicationScoped;
import tech.kayys.kallam.api.domain.ResearchSession;

@ApplicationScoped
public class ResearchSessionDtoMapper {

    private final BlockDtoMapper blockDtoMapper;

    public ResearchSessionDtoMapper(BlockDtoMapper blockDtoMapper) {
        this.blockDtoMapper = blockDtoMapper;
    }

    public ResearchSessionDto toDto(ResearchSession session) {
        return new ResearchSessionDto(
                session.id(),
                session.masalahQuestion(),
                session.completeness(),
                session.status().name(),
                session.blocks().stream().map(blockDtoMapper::toDto).toList());
    }
}
