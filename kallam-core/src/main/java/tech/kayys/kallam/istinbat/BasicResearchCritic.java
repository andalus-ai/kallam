package tech.kayys.kallam.istinbat;

import java.util.ArrayList;
import java.util.List;

public final class BasicResearchCritic implements ResearchCritic {

    @Override
    public ResearchCritique critique(ResearchState state) {
        var findings = new ArrayList<ResearchCritique.Finding>();
        var required = new ArrayList<String>();

        if (state.evidence().isEmpty()) {
            findings.add(new ResearchCritique.Finding(
                    "EVIDENCE", "HIGH", "No evidence was retrieved.", List.of()));
            required.add("Retrieve primary and scholarly evidence.");
        }

        boolean hasQuran = state.evidence().stream().anyMatch(e -> e.type() == EvidenceType.QURAN);
        boolean hasHadith = state.evidence().stream().anyMatch(e -> e.type() == EvidenceType.HADITH);

        if (!hasQuran) required.add("Check relevant Quranic evidence.");
        if (!hasHadith) required.add("Check relevant hadith evidence.");

        if (!state.unresolvedQuestions().isEmpty()) {
            findings.add(new ResearchCritique.Finding(
                    "FACTUAL", "MEDIUM",
                    "The research contains unresolved questions.",
                    List.of()));
        }

        double completeness = 1.0;
        if (!hasQuran) completeness -= 0.20;
        if (!hasHadith) completeness -= 0.20;
        if (state.evidence().isEmpty()) completeness = 0.0;
        if (!state.unresolvedQuestions().isEmpty()) completeness -= 0.10;

        completeness = Math.max(0, Math.min(1, completeness));

        return new ResearchCritique(
                findings, required, completeness, required.isEmpty() && completeness >= 0.8);
    }
}
