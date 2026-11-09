package tech.kayys.kallam.usul.ijma;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConsensusSearchProtocolTest {
    private ConsensusSearchProtocol protocol(ConsensusSearchProtocol.SearchStatus status,
            Instant completedAt, List<String> sources, List<String> expressions, String execution) {
        return new ConsensusSearchProtocol("protocol-1", sources, "Companion period; bounded by cited source dates",
                "Unspecified", "Reports explicitly attributed to Companions",
                "Later juristic conclusions without an attributed early report", expressions,
                ConsensusSearchProtocol.SearchCoverage.NAMED_COLLECTIONS, status, completedAt, execution, null);
    }

    @Test void completedSearchMustDeclareCollectionsExpressionsAndExecution() {
        assertThrows(IllegalArgumentException.class, () -> protocol(
                ConsensusSearchProtocol.SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE,
                Instant.parse("2026-01-01T00:00:00Z"), List.of(), List.of("ijma"), "run-1"));
        assertThrows(IllegalArgumentException.class, () -> protocol(
                ConsensusSearchProtocol.SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE,
                Instant.parse("2026-01-01T00:00:00Z"), List.of("collection-1"), List.of(), "run-1"));
        assertThrows(IllegalArgumentException.class, () -> protocol(
                ConsensusSearchProtocol.SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE,
                Instant.parse("2026-01-01T00:00:00Z"), List.of("collection-1"), List.of("ijma"), null));
    }

    @Test void unfinishedSearchCannotClaimCompletionTimestamp() {
        assertThrows(IllegalArgumentException.class, () -> protocol(
                ConsensusSearchProtocol.SearchStatus.IN_PROGRESS,
                Instant.parse("2026-01-01T00:00:00Z"), List.of("collection-1"), List.of("ijma"), null));
    }

    @Test void completedSearchIsExplicitlyBoundedAndAuditable() {
        var result = protocol(ConsensusSearchProtocol.SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE,
                Instant.parse("2026-01-01T00:00:00Z"), List.of("collection-1", "collection-1"),
                List.of("ijma", "ijma al-sahaba"), "search-run-42");
        assertEquals(List.of("collection-1"), result.sourceCollectionIds());
        assertEquals(List.of("ijma", "ijma al-sahaba"), result.searchExpressions());
        assertEquals("search-run-42", result.executionReference());
    }

    @Test void protocolDoesNotClaimCorpusExhaustiveness() {
        var result = protocol(ConsensusSearchProtocol.SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE,
                Instant.parse("2026-01-01T00:00:00Z"), List.of("selected-collection"),
                List.of("consensus"), "search-run-42");
        assertEquals(ConsensusSearchProtocol.SearchCoverage.NAMED_COLLECTIONS, result.coverage());
        assertEquals(ConsensusSearchProtocol.SearchStatus.COMPLETED_WITHIN_DECLARED_SCOPE, result.status());
    }
}
