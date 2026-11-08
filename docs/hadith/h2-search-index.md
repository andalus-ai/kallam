# H2 — Source-aware Hadith search and retrieval

## Scope

H2 supplies a deterministic, rebuildable search adapter over source-attested occurrences. It implements exact, normalized, lexical and hybrid retrieval. It is an embedded/development adapter, not a durable corpus store, production-scale full-text engine or semantic model.

## Arabic normalization

The search projection applies Unicode NFKC, removes Arabic vowel marks and Quranic annotation marks, removes tatweel, folds common Alef variants to Alef, folds Alef Maksura to Ya, strips punctuation and collapses whitespace.

This is a retrieval convenience, not an editorial correction. The original Matn and Isnad remain the authoritative source text and are never modified.

## Search modes

| Mode | Behavior |
|---|---|
| EXACT | Exact match against original Matn or Isnad text |
| NORMALIZED | Exact match after the documented Arabic normalization |
| LEXICAL | Query-token coverage against normalized Matn and Isnad |
| HYBRID | Exact, then normalized, then weighted lexical candidate ranking |
| SEMANTIC | Explicitly unsupported by the in-memory adapter; requires a separately configured embedding adapter |

Scores are retrieval signals only. They do not establish narration identity, source authenticity, or Hadith grading. Ties are deterministic by occurrence ID. Results contain occurrence IDs; the discovery application service must resolve each hit back to its collection and edition before presenting source evidence.

## Adapter lifecycle

InMemoryHadithSearchIndex supports indexing/replacing one occurrence, removing an occurrence and searching. It is intentionally volatile and must be rebuilt from the corpus store after restart. A production index adapter should consume committed corpus changes, support full rebuild and incremental updates, expose index/corpus version information, and provide deletion/retraction propagation.

## Known limitations and next work

- No persistent search backend is selected yet.
- No embedding model or semantic ranking is bundled.
- The current lexical score is simple query-token coverage, not BM25.
- Arabic orthographic normalization is conservative but still needs scholar/language-specialist review against a curated test corpus.
- H3 adds Matn variant comparison and alignment; H4 addresses Isnad and narrator/Rijal research.
