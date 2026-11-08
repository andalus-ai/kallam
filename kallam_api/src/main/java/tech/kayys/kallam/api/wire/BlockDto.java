package tech.kayys.kallam.api.wire;

import java.util.Map;

/**
 * The exact JSON envelope the Flutter client's {@code BlockMapper}
 * (data/dto/block_mapper.dart) expects:
 *
 * <pre>{@code
 * {
 *   "id": "blk_01",
 *   "type": "QIYAS_CHAIN",
 *   "revisionId": "rev_04",
 *   "epistemicStatus": "ANALOGICAL_EXTENSION",
 *   "supersedesBlockId": "blk_00",
 *   "payload": { ... type-specific fields ... }
 * }
 * }</pre>
 *
 * Used for HTTP responses, WebSocket push payloads, and - via
 * {@code PanacheRevisionEventStore} - JSONB persistence, so the wire shape
 * only has to be gotten right in one place ({@link BlockDtoMapper}).
 */
public record BlockDto(
        String id,
        String type,
        String revisionId,
        String epistemicStatus,
        String supersedesBlockId,
        Map<String, Object> payload) {
}
