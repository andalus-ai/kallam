package tech.kayys.kallam.istinbat.integration;

import tech.kayys.kallam.istinbat.Evidence;
import java.util.List;

public interface EvidenceRetriever {
    List<Evidence> retrieve(String query, int topK, String specialist, boolean primarySourceOnly);
}
