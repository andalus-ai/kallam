package tech.kayys.kallam.hadith.domain;

import java.util.Objects;

/**
 * An attributed grading statement. This record stores what a source reports;
 * it does not assert that Kallam independently authenticated the narration.
 */
public record GradingRecord(
        String id,
        HadithId hadithId,
        OccurrenceId occurrenceId,
        String gradingTerm,
        String graderName,
        String sourceWork,
        String sourceLocator,
        String statedReason,
        AttributionStatus attributionStatus) {
    public GradingRecord {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id must not be blank");
        Objects.requireNonNull(hadithId, "hadithId");
        graderName = required(graderName, "graderName");
        gradingTerm = required(gradingTerm, "gradingTerm");
        sourceWork = required(sourceWork, "sourceWork");
        Objects.requireNonNull(attributionStatus, "attributionStatus");
        id = id.trim();
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
