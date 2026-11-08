package tech.kayys.kallam.api.domain.block;

/**
 * Base type for every rich content block. A sealed interface (rather than
 * an abstract class) keeps every implementation a plain, Jackson-friendly
 * record while still giving the compiler exhaustiveness checking on any
 * {@code switch} over block subtype - the Java-side mirror of the Dart
 * `sealed class Block` on the Flutter client.
 *
 * Every block is independently identified and versioned: {@code id} is
 * stable across revisions of the *same logical section*, while
 * {@code supersedesBlockId} points at the block this one replaced, so the
 * UI can show "revised from an earlier version" without losing history.
 */
public sealed interface Block
        permits TaswirBlock, EvidenceBlock, QiyasChainBlock, IkhtilafBlock, UnknownBlock {

    String id();

    BlockType type();

    /** Id of the {@code RevisionEvent} that produced this version of the block. */
    String revisionId();

    EpistemicStatus epistemicStatus();

    String supersedesBlockId();
}
