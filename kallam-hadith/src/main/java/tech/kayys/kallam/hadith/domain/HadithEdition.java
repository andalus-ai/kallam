package tech.kayys.kallam.hadith.domain;

import java.net.URI;
import java.util.Objects;

/** A concrete edition or digitization of a collection. */
public record HadithEdition(
        EditionId id,
        CollectionId collectionId,
        String publisher,
        String editionStatement,
        String publicationYear,
        URI sourceUri,
        String license,
        String corpusVersion) {
    public HadithEdition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(collectionId, "collectionId");
        publisher = clean(publisher);
        editionStatement = clean(editionStatement);
        publicationYear = clean(publicationYear);
        license = clean(license);
        corpusVersion = clean(corpusVersion);
    }
    private static String clean(String value) { return value == null ? null : value.trim(); }
}
