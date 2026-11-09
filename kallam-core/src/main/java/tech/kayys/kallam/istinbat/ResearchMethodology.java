package tech.kayys.kallam.istinbat;

import java.util.List;
import java.util.Objects;

/**
 * Explicit, versioned methodology context for one research case.
 * "unspecified" is an explicit absence of a selected methodology, not a madhhab default.
 */
public record ResearchMethodology(
        String id,
        String version,
        String school,
        List<String> disciplines,
        boolean structuredAnalysisRequired) {

    public ResearchMethodology {
        id = requireText(id, "id");
        version = requireText(version, "version");
        school = school == null || school.isBlank() ? "unspecified" : school;
        disciplines = disciplines == null ? List.of() : List.copyOf(disciplines);
    }

    public static ResearchMethodology unspecified() {
        return new ResearchMethodology("unspecified", "0", "unspecified", List.of(), false);
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }
}
