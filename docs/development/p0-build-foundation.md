# P0 — Build and Integration Foundation

## Objective

Establish one reproducible Maven reactor while preserving Kallam's scholarly domain boundary and keeping Andalus implementation details in adapter modules.

## Decisions

- Canonical Maven coordinates: tech.kayys.kallam.
- Reactor version: 0.1.0-SNAPSHOT.
- Build baseline: Java 25, Maven 3.9.9+, Quarkus 3.32.2.
- Andalus artifacts are consumed from the separate andalus-framework and andalus repositories at version 0.0.1.
- kallam-core is a plain Java library.
- Andalus agent adapters are isolated in kallam-andalus-integration; RAG adapters are isolated in kallam-rag-integration.
- The API assembles modules but does not move research policy into Quarkus.

## Module dependency direction

~~~mermaid
flowchart TD
  CORE[kallam-core]
  IJ[kallam_ijtihad]
  RAG[kallam-rag-integration]
  AND[kallam-andalus-integration]
  API[kallam_api / Quarkus]
  CORE --> IJ
  CORE --> RAG
  IJ --> AND
  CORE --> API
  IJ --> API
  RAG --> API
  AND --> API
  RAG -. uses .-> ARAG[Andalus RAG Runtime]
  AND -. uses .-> ARUNTIME[Andalus Runtime]
~~~

## Package access

The two Andalus package repositories require GitHub Packages credentials. .mvn/settings.xml reads credentials from environment variables and contains no token. CI must provide GH_PACKAGES_READ_TOKEN with read access to both package repositories.

## Verification

The acceptance command is:

    mvn -s .mvn/settings.xml -B -ntp clean verify

The build should be executed in a JDK 25 environment with package access. A successful XML edit or successful GitHub commit is not evidence that the full reactor compiles. Record the exact Maven result in the pull request before merging.

## Known follow-up work

- Add a CDI producer for concrete RAG specialists after selecting and validating the runtime Retriever bean.
- Replace the placeholder Andalus execution/result bridge with the confirmed execution API.
- Add API-level tests for session projection, revision sequencing and authenticated scholar identity.
- Add source-provenance validation and evidence metadata contract tests.
