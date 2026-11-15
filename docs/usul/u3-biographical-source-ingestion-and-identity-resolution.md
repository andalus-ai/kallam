# U3 — Biographical source registration and identity resolution

## Purpose

U3 makes Companion biographical research traceable at edition and locator level. It adds source-edition registration contracts, structured citations, reviewable name-to-identity resolution and explicit handling of conflicting reports.

This is a domain foundation, not a scraper, OCR pipeline, automatic authority classifier or pre-populated biographical database.

## Domain model

```mermaid
classDiagram
    class BiographicalSourceEdition {
      sourceId
      title
      author
      editorOrCompiler
      publisher
      publicationYear
      editionStatement
      languageTag
      sourceKind
      catalogIdentifier
    }
    class BiographicalSourceLocator {
      sourceId
      volume
      pageStart
      pageEnd
      section
      reportNumber
      textAnchor
    }
    class BiographicalSourceCitation {
      citationId
      citationNote
    }
    class CompanionBiographicalAttestation {
      attestationId
      assertion
      verificationState
    }
    class CompanionIdentityResolution {
      resolutionId
      observedName
      status
      selectedCompanionId
      reviewerReference
    }
    class BiographicalReportConflict {
      conflictId
      issue
      status
      resolutionRationale
    }
    BiographicalSourceEdition --> BiographicalSourceCitation
    BiographicalSourceCitation --> BiographicalSourceLocator
    CompanionBiographicalAttestation ..> BiographicalSourceCitation : traceable source
    CompanionIdentityResolution ..> BiographicalSourceCitation : supporting citations
    CompanionIdentityResolution --> CompanionId : candidate or selected identity
    BiographicalReportConflict --> CompanionId : subject
    BiographicalReportConflict ..> CompanionBiographicalAttestation : conflicting reports
```

## Resolution workflow

```mermaid
flowchart TD
    A[Register a specific source edition] --> B[Capture exact source locator]
    B --> C[Create citation and biographical attestation]
    C --> D[Normalize observed Arabic name form]
    D --> E[Generate candidate identity references]
    E --> F{Evidence sufficient and unambiguous?}
    F -->|No| G[Keep candidates ambiguous or under review]
    F -->|Yes| H[Qualified reviewer examines context and citations]
    H --> I{Reviewer decision}
    I -->|Accept| J[Record reviewed link to existing identity]
    I -->|Reject / no match| K[Record no match or continue research]
    C --> L[Detect incompatible attestations]
    L --> M[Open explicit report conflict]
    M --> N[Keep unresolved until reviewed with rationale]
```

## Rules

1. **Edition identity matters.** A title alone is insufficient. Register the edition, author/editor where known, publication metadata, language and catalogue identifier where available. Do not silently combine pagination across editions.
2. **Locators are edition-specific.** A citation locator must refer to the same registered `sourceId` as its edition. Volume, pages, section, report number or digital text anchor may be used; a locator with no anchor is invalid.
3. **Citations are not verdicts.** A citation identifies where an assertion can be checked. It does not itself verify authenticity, authorship, transmission or historical truth.
4. **Name matching proposes candidates.** Arabic normalization and lexical similarity may assist retrieval, but must not merge identities or convert a common kunya/nasab into a unique person.
5. **Accepted identity links require human review.** An accepted match must cite supporting references and preserve reviewer attribution and rationale. Candidate identities remain stable records; this milestone does not merge or delete them.
6. **Ambiguity is a first-class outcome.** Multiple candidates remain explicit; no arbitrary top-ranked candidate becomes the selected person.
7. **Conflicting reports remain visible.** At least two distinct attestations are required to register a conflict. Resolution requires reviewer attribution and an explanation; unresolved conflict is a valid state.
8. **No fabricated source metadata.** Unknown bibliographic fields remain absent. No source content or historical person records are seeded by this milestone.
9. **No automated grading.** Retrieval, OCR confidence, transliteration similarity or model confidence cannot promote a citation, attestation, identity match or conflict resolution.

## Acceptance criteria

- Citations bind to one explicit edition and a usable locator.
- Identity resolution preserves candidate identities and has an explicit ambiguous state.
- An accepted identity match requires citations, reviewer and rationale.
- Conflicts can be recorded without forcing a winner.
- A conflict cannot be marked resolved without reviewer attribution and rationale.
- Existing `CompanionId`, profile, attestation and qualification contracts remain the stable foundation.

## Deferred

- Edition catalogues and source licensing / rights workflows.
- Arabic script normalization, transliteration and fuzzy retrieval services.
- OCR, ingestion connectors, text extraction and source alignment.
- Historical name authority dataset and human adjudication UI.
- Automatic conflict detection; conflict registration remains an explicit reviewed research action.
