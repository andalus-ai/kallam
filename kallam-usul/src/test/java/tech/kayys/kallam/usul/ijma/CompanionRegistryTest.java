package tech.kayys.kallam.usul.ijma;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class CompanionRegistryTest {
    private CompanionBiographicalAttestation attestation(String id) {
        return new CompanionBiographicalAttestation(id,
                CompanionBiographicalAttestation.AssertionKind.NAME,
                "A source reports this name form.", "source.catalogue", "volume-1/page-20",
                CompanionBiographicalAttestation.VerificationState.SOURCE_VERIFIED, null);
    }

    @Test void profileRepresentsIdentityWithoutAssigningUniversalQualification() {
        var profile = new CompanionProfile(new CompanionId("companion:example"), "Preferred name", "ar",
                List.of(), null, null, List.of(attestation("bio-1")));
        assertEquals("companion:example", profile.companionId().value());
        assertTrue(profile.nameVariants().isEmpty());
        assertNull(profile.birthDate());
    }

    @Test void nameVariantsRetainTheirAttestationReferences() {
        var variant = new CompanionNameVariant("Abu Example", CompanionNameVariant.NameKind.KUNYA,
                "ar", List.of("bio-1", "bio-1"));
        assertEquals(List.of("bio-1"), variant.attestationIds());
    }

    @Test void reviewedBiographicalAttestationRequiresReviewer() {
        assertThrows(IllegalArgumentException.class, () -> new CompanionBiographicalAttestation(
                "bio-1", CompanionBiographicalAttestation.AssertionKind.CHRONOLOGY,
                "A date assertion.", "source", "locator",
                CompanionBiographicalAttestation.VerificationState.SCHOLAR_REVIEWED, null));
    }

    @Test void historicalDatesPreserveCalendarAndUncertainty() {
        var approximateDeath = new CompanionHistoricalDateRange(
                CompanionHistoricalDateRange.CalendarSystem.HIJRI, 40, 42,
                CompanionHistoricalDateRange.Precision.APPROXIMATE);
        assertEquals(40, approximateDeath.earliestYear());
        assertEquals(42, approximateDeath.latestYear());
        assertThrows(IllegalArgumentException.class, () -> new CompanionHistoricalDateRange(
                CompanionHistoricalDateRange.CalendarSystem.HIJRI, 42, 40,
                CompanionHistoricalDateRange.Precision.APPROXIMATE));
    }

    @Test void exactDateRequiresOneYear() {
        assertThrows(IllegalArgumentException.class, () -> new CompanionHistoricalDateRange(
                CompanionHistoricalDateRange.CalendarSystem.CE, 600, 602,
                CompanionHistoricalDateRange.Precision.EXACT));
    }

    @Test void qualificationIsScopedToMethodologyAndQuestion() {
        var assessment = new CompanionQualificationAssessment("assessment-1",
                new CompanionId("companion:example"), "methodology:hanafi:v1",
                "Eligibility for this defined ijma' inquiry",
                CompanionQualificationAssessment.QualificationStatus.QUALIFIED_WITHIN_SCOPE,
                List.of("participant-qualification"), List.of("bio-1"),
                "scholar:reviewer-1", "Reviewed against the cited criteria.");
        assertEquals("methodology:hanafi:v1", assessment.methodologyProfileId());
        assertEquals("Eligibility for this defined ijma' inquiry", assessment.questionScope());
    }

    @Test void concludedQualificationRequiresScholarlyTraceability() {
        assertThrows(IllegalArgumentException.class, () -> new CompanionQualificationAssessment(
                "assessment-1", new CompanionId("companion:example"), "methodology:v1", "defined inquiry",
                CompanionQualificationAssessment.QualificationStatus.QUALIFIED_WITHIN_SCOPE,
                List.of(), List.of(), null, null));
    }

    @Test void unresolvedQualificationCanRemainUnassessed() {
        var assessment = new CompanionQualificationAssessment("assessment-1",
                new CompanionId("companion:example"), "methodology:v1", "defined inquiry",
                CompanionQualificationAssessment.QualificationStatus.NOT_ASSESSED,
                List.of(), List.of(), null, null);
        assertEquals(CompanionQualificationAssessment.QualificationStatus.NOT_ASSESSED, assessment.status());
    }
}
