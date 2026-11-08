package tech.kayys.kallam.api.wire;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import tech.kayys.kallam.api.domain.block.Block;
import tech.kayys.kallam.api.domain.block.BlockType;
import tech.kayys.kallam.api.domain.block.EpistemicStatus;
import tech.kayys.kallam.api.domain.block.EvidenceBlock;
import tech.kayys.kallam.api.domain.block.IkhtilafBlock;
import tech.kayys.kallam.api.domain.block.MadhhabPosition;
import tech.kayys.kallam.api.domain.block.QiyasChainBlock;
import tech.kayys.kallam.api.domain.block.TaswirBlock;
import tech.kayys.kallam.api.domain.block.UnknownBlock;

/**
 * The single place that knows the JSON shape of a block, in both
 * directions. Keep this file and Flutter's block_mapper.dart in sync -
 * everything else (REST resource, WebSocket, persistence) only ever sees
 * either a typed {@link Block} or a {@link BlockDto}, never raw JSON.
 */
@ApplicationScoped
public class BlockDtoMapper {

    public BlockDto toDto(Block block) {
        return switch (block) {
            case TaswirBlock b -> new BlockDto(
                    b.id(), b.type().name(), b.revisionId(), b.epistemicStatus().name(), b.supersedesBlockId(),
                    Map.of(
                            "subject", b.subject(),
                            "definition", b.definition(),
                            "essentialCharacteristics", b.essentialCharacteristics(),
                            "unresolvedFactualQuestions", b.unresolvedFactualQuestions()));
            case EvidenceBlock b -> new BlockDto(
                    b.id(), b.type().name(), b.revisionId(), b.epistemicStatus().name(), b.supersedesBlockId(),
                    payloadOrOmitNulls(Map.of(
                            "reference", b.reference(),
                            "sourceText", b.sourceText(),
                            "translation", b.translation(),
                            "chainNarrators", b.chainNarrators()
                    ), "grading", b.grading()));
            case QiyasChainBlock b -> new BlockDto(
                    b.id(), b.type().name(), b.revisionId(), b.epistemicStatus().name(), b.supersedesBlockId(),
                    Map.of(
                            "asl", b.asl(),
                            "hukmAlAsl", b.hukmAlAsl(),
                            "illah", b.illah(),
                            "far", b.far(),
                            "tahqiqAlManat", b.tahqiqAlManat(),
                            "result", b.result()));
            case IkhtilafBlock b -> new BlockDto(
                    b.id(), b.type().name(), b.revisionId(), b.epistemicStatus().name(), b.supersedesBlockId(),
                    Map.of(
                            "positions", b.positions().stream().map(this::positionToMap).toList(),
                            "agreedPoints", b.agreedPoints(),
                            "disagreementReasons", b.disagreementReasons()));
            case UnknownBlock b -> new BlockDto(
                    b.id(), b.type().name(), b.revisionId(), b.epistemicStatus().name(), b.supersedesBlockId(),
                    b.rawPayload());
        };
    }

    public Block fromDto(BlockDto dto) {
        BlockType type = BlockType.valueOf(dto.type());
        EpistemicStatus status = EpistemicStatus.valueOf(dto.epistemicStatus());
        Map<String, Object> p = dto.payload() == null ? Map.of() : dto.payload();

        return switch (type) {
            case TASWIR_AL_MASALAH -> new TaswirBlock(
                    dto.id(), dto.revisionId(), status, dto.supersedesBlockId(),
                    str(p, "subject"), str(p, "definition"),
                    strList(p, "essentialCharacteristics"), strList(p, "unresolvedFactualQuestions"));
            case QURAN_EVIDENCE, HADITH_EVIDENCE -> new EvidenceBlock(
                    dto.id(), type, dto.revisionId(), status, dto.supersedesBlockId(),
                    str(p, "reference"), str(p, "sourceText"), str(p, "translation"),
                    (String) p.get("grading"), strList(p, "chainNarrators"));
            case QIYAS_CHAIN -> new QiyasChainBlock(
                    dto.id(), dto.revisionId(), status, dto.supersedesBlockId(),
                    str(p, "asl"), str(p, "hukmAlAsl"), str(p, "illah"), str(p, "far"),
                    str(p, "tahqiqAlManat"), str(p, "result"));
            case SCHOLARLY_POSITION -> new IkhtilafBlock(
                    dto.id(), dto.revisionId(), status, dto.supersedesBlockId(),
                    positionsFrom(p), strList(p, "agreedPoints"), strList(p, "disagreementReasons"));
            default -> new UnknownBlock(dto.id(), dto.revisionId(), status, dto.supersedesBlockId(), p);
        };
    }

    @SuppressWarnings("unchecked")
    private List<MadhhabPosition> positionsFrom(Map<String, Object> payload) {
        Object raw = payload.get("positions");
        if (!(raw instanceof List<?> list)) {
            return List.of();
        }
        return list.stream()
                .map(o -> (Map<String, Object>) o)
                .map(m -> new MadhhabPosition(
                        str(m, "school"), str(m, "position"), strList(m, "evidenceRefs"), str(m, "usulBasis")))
                .toList();
    }

    private Map<String, Object> positionToMap(MadhhabPosition p) {
        return Map.of(
                "school", p.school(), "position", p.position(),
                "evidenceRefs", p.evidenceRefs(), "usulBasis", p.usulBasis());
    }

    private String str(Map<String, Object> m, String key) {
        Object v = m.get(key);
        return v == null ? "" : v.toString();
    }

    @SuppressWarnings("unchecked")
    private List<String> strList(Map<String, Object> m, String key) {
        Object v = m.get(key);
        return v instanceof List<?> list ? (List<String>) list : List.of();
    }

    private Map<String, Object> payloadOrOmitNulls(Map<String, Object> base, String key, Object nullableValue) {
        if (nullableValue == null) {
            return base;
        }
        var withExtra = new java.util.HashMap<>(base);
        withExtra.put(key, nullableValue);
        return Map.copyOf(withExtra);
    }
}
