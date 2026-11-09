# QA0–QA1 — Qur'anic Text Domain and Source Foundation

## Objective

Establish a provider-independent, edition-aware foundation for Qur'anic text before implementing corpus adapters, annotation services or scholarly interpretation workflows.

These contracts live in the independent kallam-quran module under tech.kayys.kallam.quran. The kallam-language-arabic module owns general Arabic language and linguistic capabilities. Neither module requires the other merely to represent its own domain.

## Scope

- Canonical surah/ayah coordinates and edition identity.
- Explicit text representation kinds and immutable source transcription.
- Provenance for derived representations.
- A provider-neutral read port for verse text.
- Qira'at, riwayah and optional tariq identity contracts.

## Representation policy

The model distinguishes SOURCE_TRANSCRIPTION, UTHMANI_ORTHOGRAPHY, READING_SPECIFIC, DISPLAY, SEARCH_NORMALIZED and ANALYSIS. Derived values identify their source representation and transformation. Search normalization never overwrites source text.

Qira'at is not inferred from spelling, model output or normalized text. Reading paths preserve Qira'at, riwayah and optional tariq as distinct identities, and verse-level variants require explicit source attestations.

## Source and licensing requirements

Corpus imports record provider, edition/release, stable source locator, declared license and redistribution constraints, import batch, transformation history, verification status and reviewer attribution where available. No Qur'anic dataset is bundled or declared verified by this foundation.

\`\`\`mermaid
flowchart TD
  CORPUS["Edition-aware Qur'an corpus"]
  REF["Canonical verse reference"]
  SOURCE["Immutable source transcription"]
  UTH["Uthmani representation"]
  READ["Reading-specific representation"]
  DISPLAY["Display projection"]
  SEARCH["Search-normalized projection"]
  ANALYSIS["Analysis projection"]
  CORPUS --> REF
  REF --> SOURCE
  SOURCE --> UTH
  SOURCE --> READ
  SOURCE --> DISPLAY
  SOURCE --> SEARCH
  SOURCE --> ANALYSIS
\`\`\`

## Acceptance criteria

- Invalid surah and ayah coordinates are rejected.
- Blank edition identifiers are rejected.
- Source text is retained exactly as imported.
- Derived representations require source and transformation references.
- Search/display transformations cannot replace source text.
- Qira'at, riwayah and tariq are not conflated.

## Deferred

Surah metadata, verified Uthmani corpus import, Unicode-versioned mark inventory, expert-reviewed variants, word-level alignment, tafsir registries and Asbab al-Nuzul registries.
