package tech.kayys.kallam.ijtihad;

import java.util.List;

public record ExpertOpinionResult(String expertId, List<ExpertFinding> findings) {
    public ExpertOpinionResult {
        findings = findings == null ? List.of() : List.copyOf(findings);
    }
}
