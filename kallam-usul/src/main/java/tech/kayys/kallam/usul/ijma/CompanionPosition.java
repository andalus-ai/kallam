package tech.kayys.kallam.usul.ijma;
import java.util.List;
import java.util.Objects;

/** A reported Companion position with explicit evidence references. */
public record CompanionPosition(String claimId, CompanionId companionId, String proposition,
        Position position, List<IjmaEvidenceReference> evidence) {
    public enum Position { AFFIRMS, OPPOSES, QUALIFIES, UNKNOWN }
    public CompanionPosition {
        claimId = required(claimId, "claimId");
        Objects.requireNonNull(companionId, "companionId");
        proposition = required(proposition, "proposition");
        Objects.requireNonNull(position, "position");
        evidence = List.copyOf(Objects.requireNonNull(evidence, "evidence"));
        if (evidence.isEmpty()) throw new IllegalArgumentException("a reported position requires evidence references");
    }
    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
}
