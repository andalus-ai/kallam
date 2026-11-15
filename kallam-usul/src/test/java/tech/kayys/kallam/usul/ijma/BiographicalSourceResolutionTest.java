package tech.kayys.kallam.usul.ijma;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class BiographicalSourceResolutionTest {
    private BiographicalSourceEdition edition() {
        return new BiographicalSourceEdition("bio-dictionary-edition-1", "A biographical dictionary",
                "Author", "Editor", "Publisher", 2001, "Second edition", "ar",
                BiographicalSourceEdition.SourceKind.BIOGRAPHICAL_DICTIONARY, "catalog:123");
    }

    @Test void citationRequiresLocatorFromSameEdition() {
        var edition = edition();
        var locator = new BiographicalSourceLocator(edition.sourceId(), "2", "145", "147",
                "Companion biographies", null, null);
        assertEquals(edition.sourceId(), new BiographicalSourceCitation("cite-1", edition, locator, null)
                .locator().sourceId());
        var otherEdition = new BiographicalSourceLocator("different-edition", null, null, null,
                "section", null, null);
        assertThrows(IllegalArgumentException.class,
                () -> new BiographicalSourceCitation("cite-2", edition, otherEdition, null));
    }

    @Test void locatorMustContainAUsableAnchor() {
        assertThrows(IllegalArgumentException.class,
                () -> new BiographicalSourceLocator("edition-1", null, null, null, null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> new BiographicalSourceLocator("edition-1", null, null, "12", null, null, null));
    }

    @Test void acceptedIdentityMatchRequiresEvidenceAndHumanReview() {
        var id = new CompanionId("companion:example");
        var resolution = new CompanionIdentityResolution("resolution-1", "Abu Example",
                CompanionNameVariant.NameKind.KUNYA, List.of(id),
                CompanionIdentityResolution.ResolutionStatus.MATCH_ACCEPTED, id,
                List.of("citation-1"), "scholar:reviewer-1", "Name form and context reviewed.");
        assertEquals(id, resolution.selectedCompanionId());
        assertThrows(IllegalArgumentException.class, () -> new CompanionIdentityResolution(
                "resolution-2", "Abu Example", CompanionNameVariant.NameKind.KUNYA, List.of(id),
                CompanionIdentityResolution.ResolutionStatus.MATCH_ACCEPTED, id,
                List.of(), null, null));
    }

    @Test void ambiguousNameRetainsCandidatesWithoutSelectingOne() {
        var ids = List.of(new CompanionId("companion:a"), new CompanionId("companion:b"));
        var resolution = new CompanionIdentityResolution("resolution-1", "Common kunya",
                CompanionNameVariant.NameKind.KUNYA, ids,
                CompanionIdentityResolution.ResolutionStatus.AMBIGUOUS, null,
                List.of("citation-1"), null, "Available references do not disambiguate.");
        assertEquals(2, resolution.candidateCompanionIds().size());
        assertNull(resolution.selectedCompanionId());
    }

    @Test void rejectedCandidateIsNotTreatedAsIdentityMerge() {
        var id = new CompanionId("companion:a");
        var resolution = new CompanionIdentityResolution("resolution-1", "Observed spelling",
                CompanionNameVariant.NameKind.OTHER, List.of(id),
                CompanionIdentityResolution.ResolutionStatus.REVIEW_REQUIRED, null,
                List.of(), null, null);
        assertNull(resolution.selectedCompanionId());
    }

    @Test void conflictingReportsMustRemainExplicitUntilReviewed() {
        var conflict = new BiographicalReportConflict("conflict-1", new CompanionId("companion:a"),
                "Conflicting death-year reports", List.of("att-1", "att-2"),
                BiographicalReportConflict.ConflictStatus.UNRESOLVED, null, null);
        assertEquals(BiographicalReportConflict.ConflictStatus.UNRESOLVED, conflict.status());
        assertThrows(IllegalArgumentException.class, () -> new BiographicalReportConflict(
                "conflict-2", new CompanionId("companion:a"), "Conflicting report",
                List.of("att-1", "att-1"), BiographicalReportConflict.ConflictStatus.OPEN, null, null));
    }

    @Test void conflictCannotBeClosedWithoutReviewerAndRationale() {
        assertThrows(IllegalArgumentException.class, () -> new BiographicalReportConflict(
                "conflict-1", new CompanionId("companion:a"), "Conflicting chronology",
                List.of("att-1", "att-2"),
                BiographicalReportConflict.ConflictStatus.RESOLVED_WITH_QUALIFICATION, null, null));
    }
}
