package tech.kayys.kallam.usul.methodology;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class MethodologyProfileTest {
    private MethodologySourceReference source() {
        return new MethodologySourceReference("catalogue.work-1", "edition-2/page-15",
                MethodologySourceReference.SourceKind.PRIMARY_TEXT);
    }
    private MethodologyCriterion reviewedCriterion() {
        return new MethodologyCriterion("ijma.sarih", MethodologyTopic.IJMA_SARIH,
                "Descriptive criterion requiring scholarly specification.",
                MethodologyReviewState.SCHOLAR_REVIEWED, List.of(source()), "scholar:reviewer-1");
    }
    private MethodologyProfile draftProfile() {
        return new MethodologyProfile("hanafi-research", "1.0.0", MethodologyTradition.HANAFI,
                "Hanafi research profile", "A versioned profile for sourced methodological descriptions.",
                MethodologyProfileStatus.DRAFT, List.of(), List.of(), null);
    }
    @Test void draftProfileCanBeCreatedWithoutPretendingItsCriteriaAreReviewed() {
        assertEquals(MethodologyProfileStatus.DRAFT, draftProfile().status());
    }
    @Test void traditionIsAnExplicitProfileDimension() {
        assertEquals(MethodologyTradition.HANAFI, draftProfile().tradition());
    }
    @Test void reviewedCriterionRequiresSourcesAndReviewer() {
        assertThrows(IllegalArgumentException.class, () -> new MethodologyCriterion("ijma.sukuti",
                MethodologyTopic.IJMA_SUKUTI, "A descriptive criterion.", MethodologyReviewState.SCHOLAR_REVIEWED,
                List.of(), null));
    }
    @Test void publishedProfileRequiresReviewedContentAndAttribution() {
        assertThrows(IllegalArgumentException.class, () -> new MethodologyProfile("profile", "1.0.0",
                MethodologyTradition.COMPARATIVE, "Comparative", "A comparative profile.",
                MethodologyProfileStatus.PUBLISHED, List.of(), List.of(), null));
    }
    @Test void publishedProfileCanCarryReviewedAndSourcedCriteria() {
        var profile = new MethodologyProfile("hanafi-research", "1.0.0", MethodologyTradition.HANAFI,
                "Hanafi research profile", "A versioned profile with reviewed descriptive criteria.",
                MethodologyProfileStatus.PUBLISHED, List.of(reviewedCriterion()), List.of(source()), "scholar:editor-1");
        assertEquals("1.0.0", profile.version());
        assertEquals(MethodologyReviewState.SCHOLAR_REVIEWED, profile.criteria().getFirst().reviewState());
    }
    @Test void criterionIdentifiersMustBeUniqueWithinProfile() {
        var first = new MethodologyCriterion("same-id", MethodologyTopic.IJMA_SARIH, "First description.",
                MethodologyReviewState.DRAFT, List.of(), null);
        var duplicate = new MethodologyCriterion("same-id", MethodologyTopic.IJMA_SUKUTI, "Second description.",
                MethodologyReviewState.DRAFT, List.of(), null);
        assertThrows(IllegalArgumentException.class, () -> new MethodologyProfile("custom", "0.1.0",
                MethodologyTradition.CUSTOM, "Custom", "A custom research profile.",
                MethodologyProfileStatus.DRAFT, List.of(first, duplicate), List.of(), null));
    }
    @Test void profileCollectionsAreImmutableCopies() {
        var criteria = new java.util.ArrayList<MethodologyCriterion>();
        var profile = new MethodologyProfile("custom", "0.1.0", MethodologyTradition.CUSTOM,
                "Custom", "A custom research profile.", MethodologyProfileStatus.DRAFT, criteria, List.of(), null);
        criteria.add(reviewedCriterion());
        assertTrue(profile.criteria().isEmpty());
    }
}
