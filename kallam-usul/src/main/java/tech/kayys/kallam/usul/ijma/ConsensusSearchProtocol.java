package tech.kayys.kallam.usul.ijma;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Auditable description of a bounded search for reports of Companion positions or dissent.
 * Completion means only that the declared protocol was executed, not that the searched
 * sources are exhaustive or that consensus has been established.
 */
public record ConsensusSearchProtocol(
        String protocolId,
        List<String> sourceCollectionIds,
        String temporalScope,
        String geographicScope,
        String inclusionCriteria,
        String exclusionCriteria,
        List<String> searchExpressions,
        SearchCoverage coverage,
        SearchStatus status,
        Instant completedAt,
        String executionReference,
        String reviewerReference) {

    public enum SearchCoverage {
        NAMED_COLLECTIONS,
        BOUNDED_BIBLIOGRAPHY,
        CURATED_CORPUS,
        OPEN_ENDED_DISCOVERY
    }

    public enum SearchStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED_WITHIN_DECLARED_SCOPE
    }

    public ConsensusSearchProtocol {
        protocolId = required(protocolId, "protocolId");
        sourceCollectionIds = normalizedList(sourceCollectionIds, "sourceCollectionIds");
        temporalScope = required(temporalScope, "temporalScope");
        geographicScope = optional(geographicScope);
        inclusionCriteria = required(inclusionCriteria, "inclusionCriteria");
        exclusionCriteria = required(exclusionCriteria, "exclusionCriteria");
        searchExpressions = normalizedList(searchExpressions, "searchExpressions");
        Objects.requireNonNull(coverage, "coverage");
        Objects.requireNonNull(status, "status");
        executionReference = optional(executionReference);
        reviewerReference = optional(reviewerReference);

        if (status == SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE) {
            Objects.requireNonNull(completedAt, "completedAt");
            if (sourceCollectionIds.isEmpty())
                throw new IllegalArgumentException("a completed search must identify at least one source collection");
            if (searchExpressions.isEmpty())
                throw new IllegalArgumentException("a completed search must record its search expressions");
            if (executionReference == null)
                throw new IllegalArgumentException("a completed search must identify its execution record");
        } else if (completedAt != null) {
            throw new IllegalArgumentException("completedAt is only valid for a completed search");
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }

    private static String optional(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static List<String> normalizedList(List<String> values, String field) {
        Objects.requireNonNull(values, field);
        return values.stream().map(value -> required(value, field)).distinct().toList();
    }
}
