package tech.kayys.kallam.istinbat;

import java.util.List;

public record Masalah(
        String question,
        String taswir,
        List<String> assumptions,
        List<String> unresolvedFacts) {

    public Masalah {
        assumptions = assumptions == null ? List.of() : List.copyOf(assumptions);
        unresolvedFacts = unresolvedFacts == null ? List.of() : List.copyOf(unresolvedFacts);
    }
}
