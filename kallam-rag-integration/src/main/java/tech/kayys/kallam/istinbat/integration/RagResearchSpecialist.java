package tech.kayys.kallam.istinbat.integration;

import tech.kayys.kallam.istinbat.Evidence;
import tech.kayys.kallam.istinbat.ResearchPlan;
import tech.kayys.kallam.istinbat.ResearchRequest;
import tech.kayys.kallam.istinbat.ResearchSpecialist;
import tech.kayys.kallam.istinbat.ResearchState;

import java.util.List;

/**
 * Generic specialist adapter. The same implementation can back Quran, Hadith,
 * Fiqh, Usul, Tafsir, etc.; specialist identity controls metadata mapping and
 * collection filtering.
 */
public final class RagResearchSpecialist implements ResearchSpecialist {

    private final String id;
    private final EvidenceRetriever retriever;

    public RagResearchSpecialist(String id, EvidenceRetriever retriever) {
        this.id = java.util.Objects.requireNonNull(id);
        this.retriever = java.util.Objects.requireNonNull(retriever);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public List<Evidence> research(
            ResearchRequest request,
            ResearchState state,
            ResearchPlan.ResearchTask task) {

        return retriever.retrieve(
                task.query(),
                task.topK(),
                id,
                task.primarySourceOnly());
    }
}
