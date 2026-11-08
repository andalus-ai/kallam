# kallam-ijtihad — multi-expert panel, text rule cards, deterministic tier priority

Not a rule engine in the Drools/OptaPlanner sense — the concept of a rule engine
(consistent, auditable application of a stated methodology) without forcing usul
al-fiqh into boolean predicate logic it mostly doesn't fit. Rules live as text
("rule cards"), the same shape as this environment's own `SKILL.md` files or
Andalus's `SkillDefinition`. Only the genuinely crisp piece — which evidence tier
governs — is plain deterministic code.

## What's actually deterministic here

**`TierPriorityResolver`, nothing else.** Given a panel of expert findings, it
walks `EvidenceTier` in fixed order (Qurʾān, ḥadīth, ijmāʿ al-ṣaḥābah, qiyās) and
stops at the first tier with a finding above the confidence threshold. No LLM call,
no rule card, no interpretation — this is the part of your message that was fully
crisp ("Quran first; hadith only if Quran doesn't resolve it; qiyas the lowest
priority when nothing is found above") and it's implemented as exactly that: a
`for` loop over an enum, unit-tested (`TierPriorityResolverTest`) because a bug
here is the one failure mode that's genuinely dangerous — qiyās silently
outranking a Qurʾānic finding.

**`StructuredTarjihAdjudicator` resolves only what's honestly a lookup.** Hadith
grading comparison (ṣaḥīḥ > ḥasan > ḍaʿīf) when every conflicting finding has a
citable grading and the gradings actually differ. Equal or missing gradings —
correctly — fall through to scholar review, because matn/fiqh-level tarjīḥ between
two equally-graded hadith is not a grading comparison. Qurʾān-tier conflicts always
fall through: naskh, khaṣṣ/ʿāmm, and jamʿ are interpretive by nature, and pretending
otherwise would be worse than not automating it.

**Everything else is rule-card-guided, not rule-determined.** An expert reads the
applicable rule card(s) as context before analyzing, the same way this environment
reads a `SKILL.md` before writing code — the card constrains and grounds the
reasoning without pretending the reasoning itself is mechanical.

## Layout

```
tech.kayys.kallam.ijtihad/
  EvidenceTier.java                    # QURAN, HADITH, IJMA_SAHABAH, QIYAS - order is the priority
  RuleCard.java, RuleCategory.java, RuleCardRepository.java
  ExpertFinding.java, ExpertOpinionResult.java, MasalahContext.java
  IjtihadExpertPlugin.java             # mirrors Andalus's GuardrailDetectorPlugin shape
  TierResolution.java, TierPriorityResolver.java   # the deterministic gate
  TarjihResolution.java, TarjihAdjudicator.java, StructuredTarjihAdjudicator.java
  IstinbatDetermination.java, IstinbatOrchestrator.java   # the "ijtihad expert" coordinator
  experts/            # HadithExpertPlugin, BalaghahExpertPlugin, TarikhExpertPlugin,
                       # MadhhabComparisonExpertPlugin, TahqiqManatExpertPlugin - stubs, see below
  filesystem/FileSystemRuleCardRepository.java   # loads rules/*.md from the classpath
resources/rules/
  tier-priority/qiyas-last-resort.md
  tarjih/quran-quran-conflict.md, quran-hadith-relationship.md,
         hadith-hadith-conflict.md, ijma-formation-and-conflict.md
  experts/hadith-expert.md, balaghah-expert.md, tarikh-expert.md,
          madhhab-comparison-expert.md
```

## Why this shape, not Drools

Your reframe was right: most of uṣūl al-fiqh's actual content — jamʿ before naskh,
what makes an ʿillah munḍabiṭ, when a majority-of-ṣaḥābah position isn't really
ijmāʿ — is discursive methodology, not boolean logic. Forcing it into DRL rules
would have meant either oversimplifying it into something usable by a rule engine,
or writing rules so complex they'd need their own documentation to be readable by
the ulama who'd have to maintain them. Text a scholar can read and edit directly is
the more honest fit, and your own stack already has the infrastructure for exactly
this:

- **`andalus-guardrails-runtime`'s `GuardrailDetectorPlugin` + `DetectorOrchestrator`**
  is the plugin/orchestrator shape `IjtihadExpertPlugin` and `IstinbatOrchestrator`
  mirror — same idea (several specialists, each producing scored findings,
  something else combines them), different domain.
- **`andalus-guardrails-runtime`'s `PolicyEngine`** (CEL-expression policies from a
  repository) is a better fit than anything built here for the *crisp* sub-rules
  once you have them precisely stated — e.g. "exclude hadith with grading = da'if
  and corroboration_count = 0" is a clean CEL expression. `StructuredTarjihAdjudicator`
  is a hand-rolled placeholder for that; swap it for a `PolicyEngine`-backed
  adjudicator once specific CEL policies are written and reviewed.
- **`andalus-skill-runtime`'s `SkillDefinitionStore`** already has versioning,
  lifecycle state, and deployment workflow for text artifacts.
  `FileSystemRuleCardRepository` is a classpath-file reference implementation;
  `RuleCardRepository` is the seam for swapping in the real store without touching
  `IstinbatOrchestrator`.

## What's a stub, honestly

The five `experts/*Plugin` classes throw `UnsupportedOperationException`. I chose
not to fabricate a Andalus `AgentInstance` invocation without having verified its
current call signature — file listings showed `AgentInstance`/`AgentRegistry`
exist, not their exact invocation API. Wiring these is: resolve an agent
configured with the plugin's `methodologyRuleCardIds()` cards as context, invoke it
against `MasalahContext`, parse structured output into `ExpertFinding`s.

Rule card coverage is illustrative, not exhaustive: the four-tier priority
cascade you specified is fully covered (tier-gating + all three tarjih
relationships: Qur'an-Qur'an, Qur'an-hadith, hadith-hadith, plus ijma' formation).
Per-madhhab divergence within a tier (Hanafi istihsan, Shafi'i's stricter qiyas
conditions, etc.) is flagged in the cards but not written out — that's real
content each madhhab-aware card needs, likely reviewed by someone with the
relevant fiqh specialization rather than drafted wholesale here.

## What this doesn't do

Determinism here means: given the same facts and the same stated methodology, the
same tier governs and the same audit trail is produced, every time. It does not
mean the underlying determination is *correct* — that still depends on whether the
experts' fact-extraction was sound (was the illah really munḍabiṭ, was the hadith
really graded that way) and on genuine ikhtilaf about which madhhab's methodology
applies, neither of which software resolves. `IstinbatDetermination.Status` has
three outcomes on purpose - `RESOLVED`, `NEEDS_SCHOLAR_TARJIH`, and `UNRESOLVED` -
so a scholar sees an honest "the system could not determine this" rather than a
confident-looking answer papering over a real gap.
