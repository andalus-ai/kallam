package tech.kayys.kallam.istinbat.integration;

/**
 * Canonical metadata contract for Kallam corpus ingestion.
 *
 * Retrieval should filter on these fields rather than relying on prompt text.
 */
public final class MetadataContract {
    private MetadataContract() {}

    public static final String PRIMARY_SOURCE = "kallam.primarySource";
    public static final String EVIDENCE_TYPE = "evidenceType";
    public static final String EVIDENCE_STATUS = "evidenceStatus";
    public static final String SOURCE_ID = "sourceId";
    public static final String WORK = "work";
    public static final String AUTHOR = "author";
    public static final String EDITION = "edition";
    public static final String VOLUME = "volume";
    public static final String PAGE = "page";
    public static final String CHAPTER = "chapter";
    public static final String LOCATOR = "locator";
    public static final String TRANSLATION = "translation";
    public static final String SCHOOL = "madhhab";
    public static final String LANGUAGE = "language";
}
