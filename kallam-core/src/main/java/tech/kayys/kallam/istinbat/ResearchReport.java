package tech.kayys.kallam.istinbat;

import java.util.List;

public record ResearchReport(
        String caseId,
        String question,
        String taswir,
        List<Evidence> evidence,
        List<String> positions,
        List<String> analysis,
        List<String> objections,
        List<String> unresolved,
        ResearchCritique critique,
        String preliminarySynthesis,
        boolean requiresScholarJudgment,
        ResearchAnalysisResult structuredAnalysis) {

    /** Backward-compatible constructor for report consumers. */
    public ResearchReport(
            String caseId, String question, String taswir, List<Evidence> evidence,
            List<String> positions, List<String> analysis, List<String> objections,
            List<String> unresolved, ResearchCritique critique, String preliminarySynthesis,
            boolean requiresScholarJudgment) {
        this(caseId, question, taswir, evidence, positions, analysis, objections,
                unresolved, critique, preliminarySynthesis, requiresScholarJudgment, null);
    }

    public ResearchReport {
        evidence = evidence == null ? List.of() : List.copyOf(evidence);
        positions = positions == null ? List.of() : List.copyOf(positions);
        analysis = analysis == null ? List.of() : List.copyOf(analysis);
        objections = objections == null ? List.of() : List.copyOf(objections);
        unresolved = unresolved == null ? List.of() : List.copyOf(unresolved);
    }
}
