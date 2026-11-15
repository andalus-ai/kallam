# Kallam

Kallam is a scholarly research product for structured Fiqh research and Istinbāṭ assistance. It supports qualified scholars with evidence discovery, methodology-aware analysis, comparison, critique and auditable research artifacts. It does not replace scholarly judgment or issue autonomous religious authority.

## Repository modules

| Module | Responsibility |
|---|---|
| kallam-core | Framework-independent research domain, evidence provenance, planning and reports |
| kallam-quran | Qur'anic text, editions, orthography, Qira'at, riwayah and source provenance |
| kallam-hadith | Hadith collections, source-aware Matn comparison, Isnad and Rijal research, takhrij, grading and discovery |
| kallam-language-arabic | General Arabic language and reusable linguistic analysis contracts |
| kallam-usul | Usul al-Fiqh, ijma', ijma' al-Sahabah, qiyas and legal-methodology contracts |
| kallam-ijtihad (directory: kallam_ijtihad) | Research orchestration, methodology profiles, comparative reasoning and Tarjih |
| kallam-rag-integration | Andalus RAG adapter and evidence provenance translation |
| kallam-andalus-integration | Andalus agent execution adapters |
| kallam_api | Quarkus application, API, persistence and application wiring |

## Architecture rules

1. Qur'anic text and reading traditions belong to kallam-quran, not the general Arabic language module.
2. kallam-language-arabic owns reusable linguistic capabilities and must not require a Qur'anic corpus.
3. kallam-usul owns legal-theory concepts and represents methodology differences explicitly.
4. Ijma' claims, including ijma' al-Sahabah, preserve evidence, scope, chronology, reported dissent, methodology and scholarly review state.
5. A report of agreement is not automatically verified consensus. A bounded search that locates no dissent is not proof that dissent never existed.
6. Andalus integrations are adapters, not sources of scholarly authority.
7. AI-generated synthesis, primary-source material and scholar annotations remain distinguishable.
8. Qualified scholars retain responsibility for review and final determination.

See docs/architecture/module-boundaries.md, docs/development/qa0-qa1-quranic-text-foundation.md, docs/development/qa1-qa2-source-registry-unicode-alignment.md and docs/usul/u0-ijma-domain-foundation.md.

## Build

Requirements: JDK 25 and Maven 3.9.9 or newer. Configure GH_PACKAGES_READ_TOKEN and GITHUB_ACTOR for Andalus GitHub Packages.

    mvn -s .mvn/settings.xml -B -ntp clean verify

Framework-independent domain modules:

    mvn -pl kallam-core,kallam-usul,kallam-quran,kallam_ijtihad,kallam-hadith,kallam-language-arabic -am -B -ntp clean verify

The optional Andalus runtime adapter profile is activated with -Pandalus-runtime. The Quarkus API is in kallam_api.

## Scholarly safety

Kallam is research assistance, not a mufti or autonomous issuer of fatwas. Outputs carry provenance, epistemic status, methodology context, uncertainty and scholar-review state.
