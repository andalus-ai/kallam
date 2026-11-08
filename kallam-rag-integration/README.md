# Kallam Istinbat v0.2 — Andalus RAG Integration

This step adds the anti-corruption boundary between Kallam's scholarly
evidence model and the existing Andalus RAG runtime.

The existing runtime exposes `tech.kayys.andalus.rag.core.spi.Retriever` and
`RagQuery`; Kallam now consumes those through `AndalusRagEvidenceRetriever`.

## Important design choice

Do NOT create separate vector databases for every specialist initially.

Use one corpus infrastructure with metadata filters:

- evidenceType=QURAN
- evidenceType=HADITH
- evidenceType=TAFSIR
- evidenceType=FIQH
- evidenceType=USUL
- madhhab=HANAFI
- kallam.primarySource=true

The specialist becomes a retrieval policy, not another retrieval engine.

## Token/cost behavior

The adapter returns compact `Evidence` objects. It does not ask the RAG
generator to produce prose. Full passages are only passed to the reasoning
model later.

## Integration

Add this module to the Kallam/Andalus build with the existing Andalus RAG
runtime dependency. The exact Maven coordinates should follow the repository's
current parent/BOM; they are intentionally not guessed here.

Next implementation:
1. corpus ingestion metadata
2. specialist-specific query expansion
3. LLM analysis adapter using Andalus `InferenceBackend`
4. citation verifier
5. scholar-review event + persistent feedback
6. evaluation fixtures for Quran/Hadith retrieval
