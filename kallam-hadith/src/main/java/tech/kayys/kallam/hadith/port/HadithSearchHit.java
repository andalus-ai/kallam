package tech.kayys.kallam.hadith.port;

import java.util.Objects;
import tech.kayys.kallam.hadith.domain.OccurrenceId;

/** A retrieval candidate, not an identity match or authenticity judgment. */
public record HadithSearchHit(
        OccurrenceId occurrenceId,
        double score,
        String matchType,
        String explanation) {
    public HadithSearchHit {
        Objects.requireNonNull(occurrenceId, "occurrenceId");
        if (!Double.isFinite(score) || score < 0.0 || score > 1.0)
            throw new IllegalArgumentException("score must be finite and in [0,1]");
        matchType = matchType == null ? "UNSPECIFIED" : matchType;
    }
}
