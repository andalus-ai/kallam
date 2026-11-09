package tech.kayys.kallam.usul.ijma;

import java.util.Objects;

/** Catalog metadata for a specific edition; source identity is not just a book title. */
public record BiographicalSourceEdition(
        String sourceId,
        String title,
        String author,
        String editorOrCompiler,
        String publisher,
        Integer publicationYear,
        String editionStatement,
        String languageTag,
        SourceKind sourceKind,
        String catalogIdentifier) {

    public enum SourceKind { PRIMARY_HISTORICAL_WORK, BIOGRAPHICAL_DICTIONARY, HADITH_COLLECTION,
        CHRONICLE, CRITICAL_EDITION, MODERN_STUDY, DIGITAL_CORPUS, OTHER }

    public BiographicalSourceEdition {
        sourceId = required(sourceId, "sourceId");
        title = required(title, "title");
        author = optional(editorOrCompiler) == null ? required(author, "author") : optional(author);
        editorOrCompiler = optional(editorOrCompiler);
        publisher = optional(publisher);
        editionStatement = optional(editionStatement);
        languageTag = optional(languageTag);
        Objects.requireNonNull(sourceKind, "sourceKind");
        catalogIdentifier = optional(catalogIdentifier);
    }

    private static String required(String v, String f) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(f + " must not be blank");
        return v.trim();
    }
    private static String optional(String v) { return v == null || v.isBlank() ? null : v.trim(); }
}
