package tech.kayys.kallam.usul.ijma;

import java.util.Objects;

/** Stable citation record connecting a registered edition to an edition-specific locator. */
public record BiographicalSourceCitation(
        String citationId,
        BiographicalSourceEdition edition,
        BiographicalSourceLocator locator,
        String citationNote) {

    public BiographicalSourceCitation {
        citationId = required(citationId, "citationId");
        Objects.requireNonNull(edition, "edition");
        Objects.requireNonNull(locator, "locator");
        citationNote = citationNote == null || citationNote.isBlank() ? null : citationNote.trim();
        if (!edition.sourceId().equals(locator.sourceId()))
            throw new IllegalArgumentException("locator sourceId must match the cited edition");
    }

    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
}
