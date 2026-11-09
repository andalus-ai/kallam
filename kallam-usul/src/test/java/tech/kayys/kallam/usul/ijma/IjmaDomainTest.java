package tech.kayys.kallam.usul.ijma;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class IjmaDomainTest {
    private IjmaEvidenceReference evidence() {
        return new IjmaEvidenceReference("source.example", "vol-1/page-10",
                IjmaEvidenceReference.EvidenceRole.COMPANION_POSITION,
                IjmaEvidenceReference.EvidenceStatus.SOURCE_VERIFIED, null);
    }
    private CompanionPosition position(CompanionPosition.Position stance) {
        return new CompanionPosition("claim-1", new CompanionId("companion.example"),
                "Reported position", stance, List.of(evidence()));
    }
    @Test void companionConsensusHasAnExplicitScope() {
        var claim = new IjmaClaim("claim-1", "Issue", "Proposition", IjmaScope.COMPANIONS_ONLY,
                IjmaType.SARIH, IjmaAssessmentStatus.REPORTED, "methodology.example", List.of(evidence()), null);
        assertEquals(IjmaScope.COMPANIONS_ONLY, claim.scope());
    }
    @Test void methodologyVerifiedClaimRequiresProfileAndReviewer() {
        assertThrows(IllegalArgumentException.class, () -> new IjmaClaim("claim-1", "Issue", "Proposition",
                IjmaScope.COMPANIONS_ONLY, IjmaType.SARIH, IjmaAssessmentStatus.VERIFIED_WITHIN_METHODOLOGY,
                null, List.of(evidence()), null));
    }
    @Test void reportedClaimRequiresEvidence() {
        assertThrows(IllegalArgumentException.class, () -> new IjmaClaim("claim-1", "Issue", "Proposition",
                IjmaScope.COMPANIONS_ONLY, IjmaType.REPORTED, IjmaAssessmentStatus.REPORTED,
                "methodology.example", List.of(), null));
    }
    @Test void dissentSearchMustDeclareItsScope() {
        assertThrows(IllegalArgumentException.class, () -> new CompanionConsensusAssessment("claim-1", null,
                CompanionConsensusAssessment.DissentSearchStatus.DISSENT_NOT_IDENTIFIED_WITHIN_SCOPE,
                List.of(position(CompanionPosition.Position.AFFIRMS)), "methodology.example"));
    }
    @Test void identifiedDissentRequiresAnOpposingPosition() {
        assertThrows(IllegalArgumentException.class, () -> new CompanionConsensusAssessment("claim-1", "reviewed corpus",
                CompanionConsensusAssessment.DissentSearchStatus.DISSENT_IDENTIFIED,
                List.of(position(CompanionPosition.Position.AFFIRMS)), "methodology.example"));
    }
    @Test void dissentCannotBePresentWhenSearchSaysItWasNotIdentified() {
        assertThrows(IllegalArgumentException.class, () -> new CompanionConsensusAssessment("claim-1", "reviewed corpus",
                CompanionConsensusAssessment.DissentSearchStatus.DISSENT_NOT_IDENTIFIED_WITHIN_SCOPE,
                List.of(position(CompanionPosition.Position.OPPOSES)), "methodology.example"));
    }
    @Test void dissentIsRecordedWithoutClaimingExhaustiveAbsence() {
        var assessment = new CompanionConsensusAssessment("claim-1", "named sources and date range",
                CompanionConsensusAssessment.DissentSearchStatus.DISSENT_NOT_IDENTIFIED_WITHIN_SCOPE,
                List.of(position(CompanionPosition.Position.AFFIRMS)), "methodology.example");
        assertEquals(CompanionConsensusAssessment.DissentSearchStatus.DISSENT_NOT_IDENTIFIED_WITHIN_SCOPE,
                assessment.dissentStatus());
        assertEquals("named sources and date range", assessment.searchScope());
    }
    @Test void scholarReviewedEvidenceRequiresReviewerAttribution() {
        assertThrows(IllegalArgumentException.class, () -> new IjmaEvidenceReference("source", "locator",
                IjmaEvidenceReference.EvidenceRole.CONSENSUS_REPORT,
                IjmaEvidenceReference.EvidenceStatus.SCHOLAR_REVIEWED, null));
    }
}
