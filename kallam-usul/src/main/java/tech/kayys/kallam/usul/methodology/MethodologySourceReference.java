package tech.kayys.kallam.usul.methodology;

/** A locatable source used to substantiate a methodology description or criterion. */
public record MethodologySourceReference(String sourceId, String locator, SourceKind kind) {
    public enum SourceKind { PRIMARY_TEXT, SCHOLARLY_STUDY, EDITION_OR_CATALOGUE, EDITORIAL_CONTEXT }
    public MethodologySourceReference {
        sourceId = required(sourceId, "sourceId");
        locator = required(locator, "locator");
        if (kind == null) throw new IllegalArgumentException("kind is required");
    }
    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
