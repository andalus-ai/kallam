package tech.kayys.kallam.usul.ijma;
import java.util.List;
import java.util.Objects;

/** Records the bounded result of searching for Companion agreement or dissent. */
public record CompanionConsensusAssessment(String claimId, String searchScope,
        DissentSearchStatus dissentStatus, List<CompanionPosition> positions, String methodologyProfileId) {
    public enum DissentSearchStatus { NOT_ASSESSED, SEARCH_INCOMPLETE, DISSENT_IDENTIFIED, DISSENT_NOT_IDENTIFIED_WITHIN_SCOPE }
    public CompanionConsensusAssessment {
        if (claimId == null || claimId.isBlank()) throw new IllegalArgumentException("claimId must not be blank");
        claimId = claimId.trim();
        Objects.requireNonNull(dissentStatus, "dissentStatus");
        positions = List.copyOf(Objects.requireNonNull(positions, "positions"));
        if (dissentStatus != DissentSearchStatus.NOT_ASSESSED && (searchScope == null || searchScope.isBlank()))
            throw new IllegalArgumentException("a dissent search result must declare its scope");
        searchScope = searchScope == null ? null : searchScope.trim();
        methodologyProfileId = methodologyProfileId == null || methodologyProfileId.isBlank()
                ? null : methodologyProfileId.trim();
        for (CompanionPosition position : positions) {
            if (!claimId.equals(position.claimId()))
                throw new IllegalArgumentException("all Companion positions must refer to this claim");
        }
        if (dissentStatus == DissentSearchStatus.DISSENT_IDENTIFIED
                && positions.stream().noneMatch(p -> p.position() == CompanionPosition.Position.OPPOSES))
            throw new IllegalArgumentException("identified dissent requires at least one opposing position");
    }
}
