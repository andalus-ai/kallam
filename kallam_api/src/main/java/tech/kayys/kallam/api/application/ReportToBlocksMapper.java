package tech.kayys.kallam.api.application;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import tech.kayys.kallam.api.domain.block.Block;
import tech.kayys.kallam.api.domain.block.EpistemicStatus;
import tech.kayys.kallam.api.domain.block.EvidenceBlock;
import tech.kayys.kallam.api.domain.block.TaswirBlock;
import tech.kayys.kallam.api.domain.event.RevisionActionType;
import tech.kayys.kallam.istinbat.Evidence;
import tech.kayys.kallam.istinbat.EvidenceStatus;
import tech.kayys.kallam.istinbat.EvidenceType;
import tech.kayys.kallam.istinbat.ResearchReport;

/**
 * Bridges kallam-core's current output shape to this module's typed block
 * model. This mapper is honest about the gap between the two: core's
 * {@code ResearchState} still carries {@code positions}, {@code
 * usulFindings}, {@code argumentsFor/Against} as {@code List<String>}
 * (see kallam-core v0.1) rather than structured {@code MadhhabPosition}/
 * qiyas records, so those come through as generic text for now.
 *
 * <p><b>Recommended next step in kallam-core</b>, not done here since it
 * changes an already-tested module: promote those {@code List<String>}
 * fields on {@code ResearchState}/{@code ResearchReport} to structured
 * types (a {@code QiyasChain} record, a {@code MadhhabPosition} list) so
 * this mapper's taswir/evidence branches are the norm rather than the
 * exception. When that lands, this class shrinks - nothing downstream
 * (projector, REST, WebSocket, Flutter) needs to change, since they only
 * ever see the already-typed {@link Block}.
 */
@ApplicationScoped
public class ReportToBlocksMapper {

    /**
     * One proposal per resulting block, paired with the
     * {@link RevisionActionType} that best describes it for the timeline.
     */
    public record ProposedBlock(Block block, RevisionActionType actionType) {}

    public List<ProposedBlock> toBlocks(ResearchReport report) {
        List<ProposedBlock> proposals = new ArrayList<>();

        proposals.add(new ProposedBlock(
                new TaswirBlock(
                        newBlockId(),
                        newRevisionId(),
                        EpistemicStatus.SCHOLARLY_INTERPRETATION,
                        null,
                        report.question(),
                        report.taswir(),
                        List.of(),
                        report.unresolved()),
                RevisionActionType.BLOCK_SUPERSEDED));

        for (Evidence evidence : report.evidence()) {
            proposals.add(new ProposedBlock(toEvidenceBlock(evidence), RevisionActionType.EVIDENCE_ADDED));
        }

        // positions/analysis/objections are still List<String> in core - surfaced
        // as UNKNOWN-typed blocks with the raw text in the payload so the
        // Flutter client's GenericFallbackBlockWidget can at least display them
        // legibly instead of silently dropping them, until they're structured.
        proposals.addAll(asUnknownTextBlocks(report.positions(), "scholarlyPositionDraft"));
        proposals.addAll(asUnknownTextBlocks(report.analysis(), "usulAnalysisDraft"));
        proposals.addAll(asUnknownTextBlocks(report.objections(), "objectionDraft"));

        return proposals;
    }

    private EvidenceBlock toEvidenceBlock(Evidence evidence) {
        boolean isHadithFamily = evidence.type() == EvidenceType.HADITH || evidence.type() == EvidenceType.ATHAR;
        return new EvidenceBlock(
                newBlockId(),
                isHadithFamily
                        ? tech.kayys.kallam.api.domain.block.BlockType.HADITH_EVIDENCE
                        : tech.kayys.kallam.api.domain.block.BlockType.QURAN_EVIDENCE,
                newRevisionId(),
                toEpistemicStatus(evidence.status()),
                null,
                referenceOf(evidence),
                evidence.arabicText(),
                evidence.translation(),
                isHadithFamily ? evidence.provenance().get("grading") : null,
                List.of());
    }

    private String referenceOf(Evidence evidence) {
        var source = evidence.source();
        if (source == null) {
            return evidence.id();
        }
        return source.locator() != null && !source.locator().isBlank()
                ? "%s %s".formatted(source.work(), source.locator())
                : source.work();
    }

    private EpistemicStatus toEpistemicStatus(EvidenceStatus status) {
        // kallam-core's 5-value EvidenceStatus maps onto this module's fuller
        // 7-value EpistemicStatus; ANALOGICAL_EXTENSION and
        // FACTUAL_ASSUMPTION have no core equivalent yet (see EpistemicStatus
        // javadoc) and are only ever assigned by this API layer directly
        // (e.g. for qiyas blocks), not through this mapping.
        return switch (status) {
            case PRIMARY_SOURCE -> EpistemicStatus.SOURCE_VERIFIED;
            case SCHOLAR_INTERPRETATION -> EpistemicStatus.SCHOLARLY_INTERPRETATION;
            case INFERENCE -> EpistemicStatus.INFERENCE;
            case DISPUTED -> EpistemicStatus.DISPUTED;
            case UNVERIFIED -> EpistemicStatus.UNVERIFIED;
        };
    }

    private List<ProposedBlock> asUnknownTextBlocks(List<String> items, String payloadKey) {
        List<ProposedBlock> result = new ArrayList<>();
        for (String item : items) {
            var block = new tech.kayys.kallam.api.domain.block.UnknownBlock(
                    newBlockId(), newRevisionId(), EpistemicStatus.INFERENCE, null,
                    java.util.Map.of(payloadKey, item));
            result.add(new ProposedBlock(block, RevisionActionType.BLOCK_SUPERSEDED));
        }
        return result;
    }

    private String newBlockId() {
        return "blk_" + UUID.randomUUID();
    }

    private String newRevisionId() {
        return "rev_" + UUID.randomUUID();
    }
}
