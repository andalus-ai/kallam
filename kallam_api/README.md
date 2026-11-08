# kallam-api — Quarkus session, versioning, and revision API

Wraps `kallam-core`'s `IstinbatCoordinator` in a persistent, event-sourced
`ResearchSession` and exposes it to the Flutter client built earlier. `kallam-core`
stays a framework-free library; every Quarkus/persistence/HTTP concern lives here.

## Module layout

```
tech.kayys.kallam.api/
  domain/
    block/       # Block sealed interface + typed records - mirrors the Dart client
    event/       # RevisionEvent + the three store interfaces (ISP)
    ResearchSession.java, ResearchSessionProjector.java   # the read model + its fold
  application/
    ResearchSessionService.java   # commands + queries, the seam REST/WS sit on
    AsyncResearchRunner.java      # wraps IstinbatCoordinator, appends events
    ReportToBlocksMapper.java     # bridges core's ResearchReport into typed Block
    CoreDomainBeans.java          # CDI producers for kallam-core's interfaces
  persistence/
    RevisionEventEntity.java, ResearchSessionHeaderEntity.java   # Panache entities
    PanacheRevisionEventStore.java, RevisionEventBroadcaster.java
  wire/
    BlockDto/BlockDtoMapper, RevisionEventDto/Mapper, ResearchSessionDto/Mapper
  web/
    rest/ResearchSessionResource.java   # matches the Flutter repository's endpoints
    ws/RevisionSocket.java              # matches the Flutter repository's watchRevisions()
```

## How this matches the Flutter client

`QuarkusResearchRepository` (in the earlier Flutter scaffold) expects exactly these
endpoints and this JSON shape - `ResearchSessionResource` and `wire/*Dto` implement
that contract directly:

| Flutter repository method | Endpoint |
|---|---|
| `loadSession` | `GET /api/v1/research-sessions/{id}` |
| `loadSessionAtRevision` | `GET /api/v1/research-sessions/{id}/at-revision/{revisionId}` |
| `acceptBlock` | `POST /api/v1/research-sessions/{id}/blocks/{blockId}/accept` |
| `rejectBlock` | `POST /api/v1/research-sessions/{id}/blocks/{blockId}/reject` |
| `requestRevision` | `POST /api/v1/research-sessions/{id}/blocks/{blockId}/revise` |
| `watchRevisions` | `WS /api/v1/research-sessions/{id}/revisions` |

`BlockType` and `EpistemicStatus` wire values in `wire/BlockDtoMapper.java` must stay
byte-for-byte in sync with Flutter's `data/dto/block_mapper.dart` - it's the one seam
between the two codebases that isn't caught by a compiler on either side.

**One addition since the Flutter scaffold shipped:** `RevisionActionType` gained an
8th value, `REVISION_REQUESTED`, so a scholar's "please redo this" leaves an audit
trail distinct from the eventual superseding proposal. The Dart mirror
(`domain/entities/revision.dart`) and the icon/label `switch` in
`revision_timeline_panel.dart` need the same case added - not done automatically here
since that file lives in the other deliverable.

## Event sourcing, concretely

- `RevisionEvent` (`domain/event/`) is immutable and append-only, enforced by a
  unique `(session_id, sequence)` constraint (`V1__revision_event_log.sql`) - a
  losing concurrent writer gets a constraint violation rather than a silently
  overwritten slot.
- `ResearchSessionProjector.project(...)` is a pure fold with no I/O: events in,
  `ResearchSession` (the current blocks + completeness + status) out. The same
  method serves both the live session (fold the whole stream) and "time travel"
  (fold a prefix via `readUpTo`) - see `ResearchSessionService.loadSessionAtRevision`.
- A block's identity persists across revisions of *that section*, not across the
  whole session: `applyProposal` in the projector resolves `supersedesBlockId`
  through a `blockId → chainKey` map, so a revised qiyas chain replaces the old one
  in place (same position in the list) rather than appearing as a duplicate entry.
- `AsyncResearchRunner` and `ResearchSessionService.recordScholarAction` both append
  through the same `RevisionEventWriter` - the projector, REST resource, and
  WebSocket socket have no idea whether a given event came from a scholar's tap or
  the AI agent finishing a round. That symmetry is what makes "the AI is still
  researching in the background while you review" work without special-casing.

## What's a deliberate simplification, not a design decision

- **`ReportToBlocksMapper`** exists because `kallam-core`'s `ResearchState` still
  represents positions/analysis/objections as `List<String>` rather than structured
  `MadhhabPosition`/qiyas records (see the class javadoc). Promoting those fields in
  `kallam-core` is the real fix; this mapper is the seam that absorbs the gap until
  then, and shrinks - not disappears - once it lands, since Evidence/Taswir mapping
  stays useful regardless.
- **No `ResearchSpecialist` beans exist yet** (`CoreDomainBeans` javadoc) - Quran,
  hadith, fiqh, and usul retrieval adapters, presumably calling into the Andalus
  agent runtime, are a separate, sizeable piece of work. `IstinbatCoordinator`
  degrades gracefully to an empty evidence set without them, so this module runs
  end-to-end today; it just won't produce evidence blocks until those adapters ship.
- **`AsyncResearchRunner.reviseBlock`** re-runs the *entire* coordinator pipeline and
  discards everything except the block type the scholar targeted - there's no scoped
  "redo just this section" entry point in `kallam-core` yet. Cheap to reason about,
  wasteful in tool calls; worth revisiting once specialists exist and cost matters.
- **Single-instance WebSocket broadcasting** (`RevisionEventBroadcaster` is
  in-memory, per JVM). Fine for one API instance; horizontal scaling needs it backed
  by something shared (Postgres `LISTEN/NOTIFY`, a message broker) so an event
  published on instance A reaches a scholar's socket connected to instance B.
- **No auth wiring.** `scholarId` is an explicit request field so the REST contract
  is readable without pulling in a security setup; swap for `SecurityContext` /
  OIDC principal in the resource layer when that's ready.

## Not included

- The four `ResearchSpecialist` adapters (Quran, hadith, fiqh, usul retrieval).
- Structured `MadhhabPosition`/qiyas fields on `kallam-core`'s `ResearchState` (see
  `ReportToBlocksMapper` javadoc for the migration path).
- Auth/principal wiring.
- Tests - `IstinbatCoreTest` covers `kallam-core`; this module has none yet. The
  projector (`ResearchSessionProjector.project`) is the highest-value target: pure
  function, no mocking required, and it's the piece most likely to have an off-by-one
  in the supersedes-chain logic.
