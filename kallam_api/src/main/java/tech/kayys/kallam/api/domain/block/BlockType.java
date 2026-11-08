package tech.kayys.kallam.api.domain.block;

/**
 * Wire-stable identifiers for every rich content block a research session
 * can contain. Values here are serialised verbatim as the "type" field of
 * a block and must stay in lockstep with the Flutter client's BlockType
 * enum (data/dto/block_mapper.dart).
 *
 * Adding a case here, a matching record in this package, and one entry in
 * ReportToBlocksMapper is the whole cost of introducing a new section type -
 * the projector, the REST resource, and the WebSocket layer never change.
 */
public enum BlockType {
    TASWIR_AL_MASALAH,
    QURAN_EVIDENCE,
    HADITH_EVIDENCE,
    SCHOLARLY_POSITION,
    USUL_ANALYSIS,
    QAWAID_FIQHIYYAH,
    QIYAS_CHAIN,
    MAQASID_MASLAHAH,
    EVIDENCE_GRAPH,
    CHALLENGE_OBJECTIONS,
    SYNTHESIS,
    UNKNOWN
}
