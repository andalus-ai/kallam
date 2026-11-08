package tech.kayys.kallam.istinbat;

import java.util.List;

public interface ResearchSpecialist {
    String id();
    List<Evidence> research(ResearchRequest request, ResearchState state, ResearchPlan.ResearchTask task);
}
