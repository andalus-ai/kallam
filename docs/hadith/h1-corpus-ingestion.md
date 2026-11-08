# H1 — Corpus ingestion and source-version governance

## Purpose
H1 defines the application boundary for bringing source-edition material into the Hadith corpus without assuming a database, file format, publisher or provider.

## Import contract
A batch declares a stable batch ID, source name and version, source checksum, acquisition timestamp, bibliographic collection, concrete edition and edition-specific occurrences. The checksum and version describe the imported artifact; they are not proof of scholarly authenticity.

The importer preserves submitted Arabic Matn and printed Isnad verbatim. Normalization, tokenization, embeddings and retrieval projections belong in derived indexes and must never overwrite source text.

## Validation and atomicity
HadithImportService rejects the complete batch before persistence when the edition belongs to another collection, an occurrence references another collection or edition, occurrence IDs are duplicated, an occurrence claims another batch ID, or an occurrence has no source locator.

HadithImportStore.persistAtomically is a transaction contract: batch identity, collection/edition metadata and all occurrences are committed together or not at all. Production adapters must enforce uniqueness under concurrent imports. The pre-check is only an optimization, not a concurrency guarantee.

A repeated batch with the same ID and checksum is ALREADY_IMPORTED. Reuse of a batch ID with a different checksum is rejected as BATCH_CHECKSUM_CONFLICT. The store returns these outcomes from the same atomic write operation, so concurrent writers cannot turn the pre-check into a race condition.

## Provenance policy
- Unknown publisher, license, publication year or bibliographic detail remains unknown; no metadata is inferred.
- Edition URI and license are descriptive importer claims and require source-specific verification before being treated as trusted.
- Successful import means structurally accepted and transactionally stored, not authenticated or scholar-approved.
- Source checksum, acquisition time and importer identity should be retained in durable batch metadata by adapters.

## Adapter boundary
Implementations of HadithImportStore belong in persistence adapters. Source-specific parsers map external records into HadithImportRequest and must not bypass validation. The framework-independent module has no Quarkus, ORM or vendor dependencies.

## Next
H2 adds source-aware search/index adapters over imported occurrences. Retrieval scores remain distinct from scholarly grading.
