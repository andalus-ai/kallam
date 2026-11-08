package tech.kayys.kallam.istinbat;

import java.util.List;
import java.util.Map;

public record Evidence(
        String id,
        EvidenceType type,
        String arabicText,
        String translation,
        SourceReference source,
        EvidenceStatus status,
        double relevance,
        List<String> supportsClaims,
        List<String> contradictsClaims,
        Map<String, String> provenance) {

    public Evidence {
        supportsClaims = supportsClaims == null ? List.of() : List.copyOf(supportsClaims);
        contradictsClaims = contradictsClaims == null ? List.of() : List.copyOf(contradictsClaims);
        provenance = provenance == null ? Map.of() : Map.copyOf(provenance);
        if (relevance < 0 || relevance > 1) {
            throw new IllegalArgumentException("relevance must be between 0 and 1");
        }
    }
}
