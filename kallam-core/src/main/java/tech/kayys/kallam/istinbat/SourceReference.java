package tech.kayys.kallam.istinbat;

import java.util.Map;

public record SourceReference(
        String sourceId,
        String work,
        String author,
        String edition,
        String volume,
        String page,
        String chapter,
        String locator,
        Map<String, String> metadata) {

    public SourceReference {
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }
}
