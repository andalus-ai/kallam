package tech.kayys.kallam.istinbat.integration;

import tech.kayys.kallam.istinbat.ResearchSpecialist;

import java.util.List;

/**
 * Keeps specialist wiring explicit. Later this can become CDI discovery based
 * on skill descriptors.
 */
public final class SpecialistFactory {

    private SpecialistFactory() {}

    public static List<ResearchSpecialist> all(EvidenceRetriever retriever) {
        return List.of(
                new RagResearchSpecialist("quran", retriever),
                new RagResearchSpecialist("hadith", retriever),
                new RagResearchSpecialist("fiqh", retriever),
                new RagResearchSpecialist("usul", retriever),
                new RagResearchSpecialist("tafsir", retriever),
                new RagResearchSpecialist("madhhab", retriever)
        );
    }
}
