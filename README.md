# Kallam

Kallam is a scholarly research product for structured Fiqh research and Istinbāṭ assistance. It supports qualified scholars with evidence discovery, methodology-aware analysis, comparison, critique and auditable research artifacts. It does not replace scholarly judgment or issue autonomous religious authority.

## Repository modules

| Module | Responsibility |
|---|---|
| kallam-core | Framework-independent scholarly domain, research state, planning, analysis and reports |
| kallam_ijtihad | Usul/methodology capabilities, expert contracts, evidence tiers and Tarjih |
| kallam-rag-integration | Andalus RAG adapter and evidence provenance translation |
| kallam-andalus-integration | Andalus agent execution adapters for methodology experts |
| kallam_api | Quarkus application, REST/WebSocket API, persistence and application wiring |

## Architecture rules

1. kallam-core remains independent of Quarkus, Andalus, MCP, model vendors and persistence implementations.
2. Scholarly methodology and adjudication belong to Kallam, not the generic agent runtime.
3. Andalus integrations are adapters; they must not become the source of scholarly authority or evidence classification.
4. Retrieval metadata must not be promoted to verified provenance without independent validation.
5. A scholar remains responsible for review and final determination.
6. AI-generated analysis, published interpretation, primary-source material and scholar annotations must remain distinguishable.

See docs/development/p0-build-foundation.md and docs/architecture/module-boundaries.md.

## Build

Requirements: JDK 25 and Maven 3.9.9 or newer.

Andalus artifacts are resolved from GitHub Packages. Configure a GitHub token with package read access as GH_PACKAGES_READ_TOKEN, and set GITHUB_ACTOR to your GitHub username.

    export GITHUB_ACTOR="<github-user>"
    export GH_PACKAGES_READ_TOKEN="<read-packages-token>"
    mvn -s .mvn/settings.xml -B -ntp clean verify

To build only the framework-independent domain and methodology modules:

    mvn -pl kallam-core,kallam_ijtihad -am -B -ntp clean verify

The Quarkus API is in kallam_api. Its datasource and CORS settings are configured through environment variables; see kallam_api/src/main/resources/application.properties.

## Scholarly safety

Kallam is research assistance, not a mufti or an autonomous issuer of fatwas. Outputs must carry source provenance, epistemic status, methodology context, uncertainty and scholar-review state. Do not silently merge madhhab assumptions or present AI inference as a primary source.

## Continuous integration

The Build workflow runs the complete Maven reactor on pushes and pull requests. It uses the repository's GitHub token as a fallback for package reads; if the Andalus packages are private and that token is not granted access, configure GH_PACKAGES_READ_TOKEN in repository Actions secrets.
