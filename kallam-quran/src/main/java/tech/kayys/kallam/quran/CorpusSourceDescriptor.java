package tech.kayys.kallam.quran;
import java.net.URI;
import java.util.Objects;
/** Describes origin and declared terms for a corpus release; it does not certify source claims. */
public record CorpusSourceDescriptor(CorpusSourceId sourceId, String provider, String workTitle,
        EditionId editionId, String release, URI sourceUri, String licenseIdentifier, URI licenseUri,
        RedistributionPolicy redistributionPolicy) {
    public CorpusSourceDescriptor {
        Objects.requireNonNull(sourceId, "sourceId");
        provider = required(provider, "provider");
        workTitle = required(workTitle, "workTitle");
        Objects.requireNonNull(editionId, "editionId");
        release = required(release, "release");
        Objects.requireNonNull(redistributionPolicy, "redistributionPolicy");
        if (sourceUri != null && !sourceUri.isAbsolute()) throw new IllegalArgumentException("sourceUri must be absolute");
        if (licenseUri != null && !licenseUri.isAbsolute()) throw new IllegalArgumentException("licenseUri must be absolute");
        licenseIdentifier = optionalTrim(licenseIdentifier);
    }
    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optionalTrim(String v) { return v == null ? null : v.trim(); }
}
