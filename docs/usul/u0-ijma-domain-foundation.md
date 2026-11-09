# U0 — Usul al-Fiqh and Ijma' Domain Foundation

## Objective

Introduce a provider-independent legal-theory domain, beginning with ijma' claims and the special evidentiary questions involved in ijma' al-Sahabah.

The module records research objects and verification states. It does not embed historical consensus assertions, select one madhhab methodology, or issue legal conclusions.

## Module boundary

kallam-usul owns legal-theory concepts. kallam-quran, kallam-hadith and kallam-language-arabic remain independent source/analysis domains. kallam-ijtihad coordinates methodology-aware research and consumes these concepts without duplicating them.

## Initial contracts

- IjmaClaim: proposition under investigation, classification, methodology profile, evidence and epistemic state.
- IjmaEvidenceReference: stable source identifier and locator, evidentiary role, verification state and reviewer attribution.
- CompanionId: stable reference to a curated Companion authority catalogue.
- CompanionPosition: a sourced position attributed to an individual Companion.
- CompanionConsensusAssessment: bounded assessment of reported agreement and dissent-search status.

## Essential distinctions

1. A source reporting agreement does not automatically establish ijma'.
2. Individual Companion positions remain separately attributed and sourced.
3. Explicit agreement (sarih) and tacit/silence-based claims (sukuti) are distinct; their authority and conditions vary by usul tradition.
4. Dissent search is bounded. DISSENT_NOT_IDENTIFIED_WITHIN_SCOPE is not proof that dissent never existed.
5. VERIFIED_WITHIN_METHODOLOGY requires a methodology profile and attributable scholar reviewer.
6. This foundation ships contracts and tests, not fabricated Companion positions or consensus claims.

\`\`\`mermaid
flowchart TD
  CLAIM["Ijma' claim proposed"] --> CLASSIFY["Classify type and scope"]
  CLASSIFY --> SOURCES["Locate reports and individual positions"]
  SOURCES --> VERIFY["Verify source identity and context"]
  VERIFY --> DISSENT["Search for dissent within declared scope"]
  DISSENT --> METHOD["Evaluate with selected usul methodology"]
  METHOD --> REVIEW["Qualified scholar review"]
  REVIEW --> STATE["Record methodology-specific assessment"]
  STATE --> REPORT["Expose evidence, uncertainty and unresolved issues"]
\`\`\`

## Future extensions

Methodology profiles for Hanafi, Maliki, Shafi'i, Hanbali and comparative traditions; generations and qualified participants; chronology and geographic scope; transmission criticism; ijma' qat'i/zanni and sukuti classifications; integration with ikhtilaf, qiyas, qawa'id and maqasid; and scholar review workflows. These require scholarly specification and reviewed source material before implementation.
