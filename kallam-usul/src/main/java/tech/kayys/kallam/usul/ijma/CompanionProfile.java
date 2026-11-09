package tech.kayys.kallam.usul.ijma;

import java.util.List;
import java.util.Objects;

/**
 * Historical identity record for a Companion. It deliberately carries no
 * universal legal-rank or qualification flag.
 */
public record CompanionProfile(
        CompanionId companionId,
        String preferredName,
        String preferredNameLanguageTag,
        List<CompanionNameVariant> nameVariants,
        CompanionHistoricalDateRange birthDate,
        CompanionHistoricalDateRange deathDate,
        List<CompanionBiographicalAttestation> biographicalAttestations) {

    public CompanionProfile {
        Objects.requireNonNull(companionId, "companionId");
        preferredName = required(preferredName, "preferredName");
        preferredNameLanguageTag = preferredNameLanguageTag == null || preferredNameLanguageTag.isBlank()
                ? null : preferredNameLanguageTag.trim();
        nameVariants = List.copyOf(Objects.requireNonNull(nameVariants, "nameVariants"));
        biographicalAttestations = List.copyOf(
                Objects.requireNonNull(biographicalAttestations, "biographicalAttestations"));
        if (birthDate != null && deathDate != null
                && birthDate.calendar() == deathDate.calendar()
                && birthDate.earliestYear() != null && deathDate.latestYear() != null
                && birthDate.earliestYear() > deathDate.latestYear())
            throw new IllegalArgumentException("birth chronology cannot follow death chronology");
        long distinctAttestationIds = biographicalAttestations.stream()
                .map(CompanionBiographicalAttestation::attestationId).distinct().count();
        if (distinctAttestationIds != biographicalAttestations.size())
            throw new IllegalArgumentException("biographical attestation IDs must be unique within a profile");
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
