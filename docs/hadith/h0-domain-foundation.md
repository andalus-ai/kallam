# H0 — Hadith Domain Foundation

## Scope

This module establishes source-aware Hadith domain types and provider-neutral ports. It does not authenticate narrations, resolve narrator identities, or infer canonical identity from textual similarity.

## Identity model

- `HadithId`: a curated research identity for a narration concept.
- `OccurrenceId`: one appearance in a particular collection/edition.
- `CollectionId`: the compiled work.
- `EditionId`: a concrete publication or digitization.

A canonical Hadith identity may relate to multiple occurrences. Similarity is only a discovery signal; identity resolution requires an explicit curated or reviewed relationship.

## Source preservation

`HadithOccurrence.originalArabicMatn` and `originalIsnadText` preserve the source transcription. Search normalization belongs in a separate index projection and must never mutate these fields.

Each occurrence carries edition-specific book, chapter, number, volume, page, locator and import-batch information. Missing metadata remains null rather than being fabricated.

## Grading

`GradingRecord` records an attributed judgment. `AttributionStatus` describes verification of the attribution, not the Hadith's authenticity grade. Multiple and conflicting records coexist.

## Ports

- `HadithCorpus`: source-attested collection, edition and occurrence retrieval.
- `HadithSearch`: provider-neutral search.
- `HadithSearchQuery`: exact, normalized, lexical, semantic or hybrid intent.
- `HadithSearchHit`: candidate occurrence and retrieval score only.

A search score is not a grading score and does not establish identity.

## Dependency rules

The module is plain Java. It must not depend on Quarkus, Andalus runtime, a model provider, a vector database, or a specific persistence engine. Runtime and storage implementations are adapters.

## Initial acceptance

- Domain identifiers reject blank values.
- Occurrences preserve edition-specific numbering and original text.
- Grading attribution is represented independently from grading terminology.
- Search contracts distinguish retrieval candidates from verified identity.
- Unit tests cover invariants.
