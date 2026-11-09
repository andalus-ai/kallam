package tech.kayys.kallam.usul.ijma;

import java.util.Objects;

/** A traceable source assertion about a Companion; it does not automatically settle the assertion. */
public record CompanionBiographicalAttestation(
        String attestationId,
        AssertionKind assertionKind,
        String assertion,
        String sourceId,
        String locator,
        VerificationState verificationState,
        String reviewerReference) {

    public enum AssertionKind { IDENTITY, NAME, CHRONOLOGY, GENERATION, AFFILIATION, OTHER }
    public enum VerificationState { UNVERIFIED, SOURCE_LOCATED, SOURCE_VERIFIED, SCHOLAR_REVIEWED }

    public CompanionBiographicalAttestation {
        attestationId = required(attestationId, "attestationId");
        Objects.requireNonNull(assertionKind, "assertionKind");
        assertion = required(assertion, "assertion");
        sourceId = required(sourceId, "sourceId");
        locator = required(locator, "locator");
        Objects.requireNonNull(verificationState, "verificationState");
        reviewerReference = reviewerReference == null || reviewerReference.isBlank()
                ? null : reviewerReference.trim();
        if (verificationState == VerificationState.SCHOLAR_REVIEWED && reviewerReference == null)
            throw new IllegalArgumentException("scholar-reviewed attestation requires reviewer attribution");
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
