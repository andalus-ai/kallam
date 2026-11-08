i plan to create an agent as tools for "ulama" to help them to discovered quran ayah, hadith, ushul fiqh, and other references as knowledge for him and at the same time the agent has it's own analytical of it research what the agent generated based on all what usually the mujathid neeeded to produce the fiqh or conclusion, and with certain the citation, reasoning, quote and other  references. so these should help the "ulama"/person to understand.
of course not to replace the "ulama" job/task. but help him discover and analytic which then can be enhance/enrich continouslly.



\---
**Core Qualifications of a Mujtahid**

- **Arabic Language Proficiency:** Mastery of Arabic grammar, morphology, and rhetoric is essential to accurately interpret the linguistic nuances of the Quran and Sunnah. [[1](https://journal.staiypiqbaubau.ac.id/index.php/Maslahah/article/download/1674/2073/7798), [2](https://seekersguidance.org/answers/general-answers-feeds/what-is-the-definition-and-categories-of-a-mujtahid/)]
- **Knowledge of the Quran:** Comprehensive understanding of the verses related to legal rulings (*ahkam*), including the context of revelation (*asbab an-nuzul*) and knowing which verses are abrogated (*nasikh*) and which abrogate (*mansukh*). [[1](https://journal.staiypiqbaubau.ac.id/index.php/Maslahah/article/download/1674/2073/7798), [2](https://seekersguidance.org/answers/general-answers-feeds/what-is-the-definition-and-categories-of-a-mujtahid/)]
- **Knowledge of the Sunnah:** Familiarity with the traditions, sayings, and actions of Prophet Muhammad, alongside the science of Hadith grading to distinguish authentic (*sahih*) reports from weak ones. [[1](https://islamqa.org/hanafi/askimam/29306/what-are-all-the-pre-requisites-of-a-mujtahid-mutluk-imam/), [2](https://seekersguidance.org/answers/general-answers-feeds/what-is-the-definition-and-categories-of-a-mujtahid/)]
- **Principles of Jurisprudence (*****Ushul Fiqh*****):** Mastery of legal methodologies, including consensus (*ijma*), analogical reasoning (*qiyas*), and the broader objectives of Islamic law (*maqasid al-sharia*). [[1](https://www.bircu-journal.com/index.php/birci/article/download/2568/pdf), [2](https://jurnal.stikes-ibnusina.ac.id/index.php/Intellektika/article/view/1509), [3](https://seekersguidance.org/answers/general-answers-feeds/what-is-the-definition-and-categories-of-a-mujtahid/)]
- **Awareness of Consensus and Differences:** Knowing past scholarly agreements (*ijma*) to avoid issuing rulings that contradict established consensus, as well as understanding historical scholarly disagreements (*ikhtilaf*). [[1](https://jurnal.stikes-ibnusina.ac.id/index.php/Intellektika/article/download/1509/1713/6805), [2](https://islamqa.org/hanafi/askimam/29306/what-are-all-the-pre-requisites-of-a-mujtahid-mutluk-imam/)]

**The Process of Producing Fiqh (*****Istinbat*****)**

- **Textual Analysis:** Analyzing primary sources using linguistic and contextual tools to extract direct or implied meanings. [[1](https://www.researchgate.net/publication/406176638_Pengertian_dan_Syarat_Mujtahid_dalam_Ilmu_Fikih_dan_Perkembgannya_Diri_Masa_Kemasa), [2](https://islam.nu.or.id/syariah/metode-ijtihad-mengungkap-cara-ulama-memproduksi-hukum-aZimH)]
- **Application of Analogy (*****Qiyas*****):** Extending an established ruling from a primary text to a new, contemporary issue that shares the same underlying effective cause (*illah*). [[1](https://jurnal.stikes-ibnusina.ac.id/index.php/Intellektika/article/download/1509/1713/6805), [2](https://seekersguidance.org/answers/general-answers-feeds/what-is-the-definition-and-categories-of-a-mujtahid/)]
- **Considering Public Interest (*****Maslahah*****):** Aligning the derived law with the preservation of human life, intellect, faith, lineage, and property, ensuring the ruling promotes justice and societal well-being.



---------

Yes. What you are describing is better understood not as a “fatwa AI,” but as a **research and istinbāṭ-assistance system for qualified scholars**.

The important design principle is to separate three things:

1. **Retrieval** — discover the relevant Qur'an, Sunnah, athar, classical works, usul principles, qawa'id, positions of madhhabs, etc.
2. **Analysis** — organize and reason over those materials according to a configurable usul methodology.
3. **Scholarly judgment** — remain with the mujtahid/ʿālim, who validates the evidence, methodology, premises, and final conclusion.

Your list of muj­tahid qualifications is actually a very useful starting point for the architecture. I would turn it into the agent's **research protocol**, rather than merely a list of things the model should "know."

### 1. The agent should think in terms of an "Istinbāṭ Workspace"

Instead of:

> User asks question → AI gives answer

I would design:

> **Mas'alah → Taswīr al-Mas'alah → Evidence discovery → Evidence verification → Linguistic analysis → Hadith analysis → Usul analysis → Ikhtilāf mapping → Ta'līl/Qiyās → Maqāṣid/Maṣlaḥah → Synthesis → Scholarly review**

For example, if an ʿālim asks:

> What is the ruling concerning X?

the system should not immediately answer *haram/halal*.

It should first construct the research problem.

---

## 2. A possible architecture

### Layer A — Mas'alah Understanding

The agent first decomposes the question:

* What exactly is the phenomenon?
* What are its real-world characteristics?
* What assumptions are being made?
* Which aspects are legally relevant?
* What terminology is being used?
* Are there multiple possible interpretations of the question?

This is extremely important because **the hukm may depend on the taswīr of the mas'alah**.

The agent could produce:

**Taswīr al-Mas'alah**

| Element                      | Finding |
| ---------------------------- | ------- |
| Subject                      | X       |
| Definition                   | ...     |
| Essential characteristics    | ...     |
| Variable characteristics     | ...     |
| Relevant actors              | ...     |
| Relevant consequences        | ...     |
| Potential legal causes       | ...     |
| Unresolved factual questions | ...     |

And then explicitly say:

> **Before proceeding to istinbāṭ, the following factual questions should be verified by the scholar.**

That prevents the system from building sophisticated fiqh reasoning on an incorrect description of the real-world case.

---

# 3. Qur'an Research Engine

The Qur'an module should go considerably beyond semantic search.

For every candidate ayah, the system should provide:

### Direct evidence

* Surah
* Ayah
* Arabic text
* translation
* relevant phrase
* grammatical structure
* key vocabulary

### Tafsir context

Potentially:

* Tafsīr al-Ṭabarī
* Ibn Kathīr
* al-Qurṭubī
* al-Rāzī
* al-Jaṣṣāṣ
* Ibn ʿĀshūr
* etc.

But crucially, the agent should distinguish:

> **Qur'an text**

from

> **scholar's interpretation of Qur'an**

from

> **AI's inference from those interpretations.**

Those three should never be collapsed into one citation.

---

# 4. Hadith Engine

This should probably be one of the most sophisticated components.

For every relevant hadith:

**Hadith Record**

* Arabic matn
* translation
* narrator
* source
* book
* chapter
* hadith number
* isnad
* parallel narrations
* variants of matn
* grading
* graders
* reason for grading where available
* conflicting gradings
* relevant sharḥ
* fiqh usage

Then the agent can say something like:

> **Hadith status:** Ṣaḥīḥ according to X; classified as ḍaʿīf by Y; the disagreement appears to concern Z.

That is dramatically better than simply:

> "According to AI, this hadith is authentic."

---

# 5. Usul al-Fiqh Engine

This is where your concept becomes particularly interesting.

You could represent the research as a chain:

**Naṣṣ → Dalālah → Uṣūl Principle → Taṭbīq → ʿIllah → Qiyās → Hukm**

For example:

```text
Evidence
   ↓
What does the text indicate?
   ↓
What type of indication?
   ↓
What usul principle governs it?
   ↓
Are there qualifications / exceptions?
   ↓
Are there contradictory evidences?
   ↓
How did the madhhabs interpret it?
   ↓
Can the principle be applied to the new case?
   ↓
What is the proposed hukm?
```

The agent should explicitly identify things such as:

* ʿĀmm / Khāṣṣ
* Muṭlaq / Muqayyad
* Mujmal / Mubayyan
* Amr / Nahy
* Ẓāhir / Naṣṣ / Mufaśśar / Muḥkam, according to the relevant usul framework
* Mafhūm al-muwāfaqah
* Mafhūm al-mukhālafah
* ʿIllah
* Qiyās
* Istishāb
* Istiḥsān
* Maṣlaḥah
* Sadd al-dharā'iʿ
* ʿUrf
* Qawāʿid fiqhiyyah
* Maqāṣid

But there is an important architectural point:

### Do not create one generic "Islamic reasoning."

Different usul traditions should be modeled separately.

For example:

> Hanafi Usul Profile
> Shafi'i Usul Profile
> Maliki Usul Profile
> Hanbali Usul Profile
> Comparative Usul Profile

Because the agent's reasoning can change depending on which usul framework it is applying.

---

# 6. Madhhab / Ikhtilaf Engine

This should be a first-class component.

The system should be able to produce something like:

### Position Map

| School / Scholar | Position | Evidence | Usul basis | Qualification |
| ---------------- | -------- | -------- | ---------- | ------------- |
| Hanafi           | X        | ...      | ...        | ...           |
| Maliki           | Y        | ...      | ...        | ...           |
| Shafi'i          | X        | ...      | ...        | ...           |
| Hanbali          | Z        | ...      | ...        | ...           |

But then go one level deeper:

### Why do they disagree?

For example:

```text
They agree on:
A
B
C

They disagree on:
D

Reason for disagreement:
- interpretation of naṣṣ
- hadith authentication
- linguistic meaning
- qiyas
- conflicting evidence
- usul principle
```

This is much more valuable to an ʿālim than simply displaying four different answers.

---

# 7. Qawā'id Fiqhiyyah Engine

The agent should also ask:

> Is this case governed by an established legal maxim?

For example:

* الأمور بمقاصدها
* اليقين لا يزول بالشك
* المشقة تجلب التيسير
* الضرر يزال
* العادة محكمة

But again, it should not merely quote the maxim.

It should establish:

**Maxim → Scope → Conditions → Exceptions → Application to case**

Because a legal maxim without its conditions can easily produce an invalid conclusion.

---

# 8. Qiyās Engine

I would make qiyās explicit rather than allowing the LLM to perform it invisibly.

For example:

### Aṣl

What existing case has an established ruling?

### Ḥukm al-Aṣl

What is its ruling?

### Naṣṣ / source

What establishes that ruling?

### ʿIllah

What is the operative effective cause?

### Farʿ

What is the new case?

### Taḥqīq al-Manāṭ

Does the ʿillah actually exist in the new case?

Then:

> **Qiyās result:** valid / questionable / invalid

And explain why.

This makes the AI's reasoning **auditable**.

---

# 9. The most important feature: an Evidence Graph

I would not make the underlying knowledge base merely a vector database.

Build an **Islamic Legal Knowledge Graph** on top of retrieval.

For example:

```text
Question
   │
   ├── Qur'an evidence
   │      ├── Ayah
   │      ├── Tafsir
   │      └── linguistic analysis
   │
   ├── Hadith evidence
   │      ├── Hadith
   │      ├── Isnad
   │      ├── grading
   │      └── Sharh
   │
   ├── Athar
   │
   ├── Ijma
   │
   ├── Ikhtilaf
   │
   ├── Usul principles
   │
   ├── Qawa'id
   │
   ├── Qiyas
   │
   ├── Maqasid
   │
   └── Contemporary facts
```

Then every conclusion should have **traceable edges** back to its sources.

---

# 10. Citation should be much stricter than normal AI citation

This is critical for your project.

The system should distinguish:

### Primary source

> Qur'an 2:275

### Secondary source

> Tafsīr al-Qurṭubī, discussion of...

### Scholarly position

> Imam al-Shafi'i's position...

### AI inference

> The system infers that...

The user should be able to click through the reasoning:

```text
CONCLUSION
    ↓
Reason 1
    ↓
Evidence A
    ↓
Original source

Reason 2
    ↓
Usul principle B
    ↓
Classical reference

Reason 3
    ↓
Qiyas
    ↓
Aṣl
    ↓
ʿIllah
    ↓
Farʿ
```

This gives the scholar **provenance**, not merely citations.

---

# 11. Introduce an "epistemic status" for every statement

This would be one of the strongest differentiators of the product.

Every generated proposition should carry a status such as:

**SOURCE-VERIFIED**

Directly supported by the cited source.

**SCHOLARLY INTERPRETATION**

The source contains a scholar's interpretation.

**INFERENCE**

The agent derived this from multiple sources.

**ANALOGICAL EXTENSION**

The system proposes an application through qiyās or another reasoning mechanism.

**UNVERIFIED**

The system has not established sufficient evidence.

**DISPUTED**

Scholars disagree.

**FACTUAL ASSUMPTION**

Depends upon a factual premise that needs verification.

This prevents the very dangerous phenomenon where AI-generated reasoning *looks* like transmitted Islamic knowledge.

---

# 12. Add a "Contradiction / Challenge Agent"

This is something I would strongly recommend.

Don't let the main agent only search for evidence supporting its emerging conclusion.

Create an adversarial stage:

> **"Try to disprove this conclusion."**

It should search for:

* contradictory ayat
* contradictory hadith
* exceptions
* opposing madhhab positions
* alternative linguistic interpretations
* stronger/weaker evidence
* ijma claims
* claims of abrogation
* conflicting qiyas
* relevant qawa'id
* conflicting maqasid
* factual counterexamples

Then produce:

### Objections

1. ...
2. ...
3. ...

### Responses

1. ...
2. ...
3. ...

### Unresolved Issues

1. ...

That resembles scholarly debate much more closely than ordinary RAG.

---

# 13. "Mujtahid Checklist" as a validation layer

Your list of qualifications can become a **research completeness checklist**.

For example:

### Evidence

* [ ] Relevant Qur'anic passages identified
* [ ] Relevant Sunnah identified
* [ ] Hadith authenticity examined
* [ ] Relevant athar identified
* [ ] Relevant ijma claims checked
* [ ] Relevant ikhtilaf identified

### Language

* [ ] Arabic terminology examined
* [ ] Morphology examined where relevant
* [ ] Syntax examined where relevant
* [ ] Rhetorical implications examined where relevant
* [ ] Ambiguous terminology resolved

### Usul

* [ ] General/specific
* [ ] Absolute/restricted
* [ ] Command/prohibition
* [ ] Nasikh/mansukh
* [ ] Conflicting evidence
* [ ] Qiyas
* [ ] ʿIllah
* [ ] Istishab
* [ ] Maslahah
* [ ] Sadd al-dhara'i
* [ ] Relevant qawa'id

### Scholarly tradition

* [ ] Madhhab positions
* [ ] Classical authorities
* [ ] Contemporary scholarship
* [ ] Reason for disagreement
* [ ] Strength of evidence

### Contemporary application

* [ ] Taswir al-mas'alah verified
* [ ] Relevant facts established
* [ ] ʿIllah verified
* [ ] Consequences considered
* [ ] Maqasid considered

Then the agent can report:

> **Research completeness: 78%**

rather than pretending that it has produced a definitive hukm.

---

# 14. I would also separate "Research Mode" from "Opinion Mode"

### Research Mode

The AI answers:

> "What are the relevant materials?"

It should be extremely expansive.

### Analytical Mode

> "How do these materials relate?"

It performs structured analysis.

### Comparative Mode

> "How did the madhhabs / scholars approach this?"

### Challenge Mode

> "What could invalidate this argument?"

### Drafting Mode

> "Prepare a research memorandum for the scholar."

### Scholar Review Mode

The scholar can accept/reject/edit:

```text
✓ Evidence accepted
✓ Hadith grading accepted
✗ Interpretation rejected
✓ Qiyas accepted
✗ Maqasid argument rejected
```

This feedback becomes extremely valuable for improving the system.

---

# 15. The final output should look more like a scholarly research memorandum

Not:

> **Answer: Haram.**

But something like:

## Mas'alah

...

## Taswīr al-Mas'alah

...

## Qur'anic Evidence

...

## Hadith Evidence

...

## Scholarly Positions

...

## Usul Analysis

...

## Qawā'id Fiqhiyyah

...

## Qiyās

...

## Maqāṣid / Maṣlaḥah

...

## Points of Agreement

...

## Points of Disagreement

...

## Strongest Arguments

...

## Counterarguments

...

## Unresolved Questions

...

## Preliminary Analytical Conclusion

...

## Confidence / Evidence Status

...

## Sources

...

And finally:

> **This is a research aid and analytical synthesis. The final fiqh judgment requires review and determination by a qualified scholar.**

---

# 16. One conceptual change I would make to your terminology

I would be careful about calling the system:

> **"an agent that does what a mujtahid does."**

Instead:

> **"an agent that reconstructs and assists the research workflow surrounding istinbāṭ."**

Because the distinction is important.

You want the machine to become extraordinarily good at:

**discovering → organizing → comparing → tracing → testing → challenging → synthesizing**

while the scholar remains responsible for:

**judgment → methodological authority → acceptance/rejection → final hukm.**

That distinction should be embedded not only in the UI but in the technical architecture.

---

## 17. The bigger opportunity

I think the most interesting version of your idea is not simply an AI chatbot for ulama.

It could become a **"Scholarly Fiqh Research Environment."**

Think of it almost as:

> **GitHub + academic research system + legal research system + classical Islamic library + reasoning assistant**

for Islamic scholarship.

A scholar could open a *mas'alah*, and the system maintains a living research graph:

```text
MAS'ALAH
│
├── Taswir
│
├── Qur'an
│
├── Sunnah
│
├── Athar
│
├── Ijma
│
├── Ikhtilaf
│
├── Usul
│
├── Qawa'id
│
├── Qiyas
│
├── Maqasid
│
├── Contemporary research
│
├── Arguments FOR
│
├── Arguments AGAINST
│
├── Unresolved issues
│
└── Scholar's final determination
```

And the scholar can continuously enrich it.

That last point is particularly powerful: **the system should learn the scholar's corrections without silently changing the underlying transmitted sources.** It should preserve what came from the texts, what came from classical scholars, what the AI inferred, and what the modern scholar personally determined.

That gives you a system whose central value is not **"AI knows fiqh"**, but **"AI makes the scholar's research process deeper, faster, more transparent, and more auditable."**

If this is the direction you intend, the next useful step would be to turn your concept into a **concrete system architecture**—the agents, tools, knowledge-base schema, source hierarchy, citation/provenance model, reasoning pipeline, and the exact output schema for an *istinbāṭ research report*.




-----PROMPT-------
excellent and yes i agree with:
"an agent that reconstructs and assists the research workflow surrounding istinbāṭ."
and there shoul specific agent to expert and research as tafsir, balagha, madhhab, tafseer, comparison, hadith, ciation , references, sanad, etc. with each own skills, then all orchestratate with contruction agent, conclusion, event visualize, reporting etc.
and since this targeted for local inference with limited token so the tools and toke management should very optimized.
and of course continues fine-tuning are great right, but it could take hight cost. but how to make with curent reasoning model could achieve this system.
but keep support reinforment learning and when possible dynamic understandin at runtime/on the fly with new knowledge from "ulama" and agent can followiing 

--------



Yes. I think the architecture becomes substantially stronger if we make one distinction very explicit:

> **Do not try to make the base model itself contain the entire Islamic scholarly corpus or the entire mujtahid workflow. Make the model a reasoning engine operating inside a structured scholarly runtime.**

That is especially important for **local inference with limited tokens**.

The runtime architecture in your uploaded material already has several pieces that fit this direction well: skill-driven agents, multiple memory layers, tool calling, orchestration, graph operations, hybrid retrieval, context/token budgeting, and evaluation infrastructure.  

## 1. I would build it as a "Scholarly Agent System"

Something like:

```text
                         ┌───────────────────────────┐
                         │       ULAMA / USER        │
                         └─────────────┬─────────────┘
                                       │
                                       ▼
                         ┌───────────────────────────┐
                         │   MAS'ALAH CONSTRUCTOR    │
                         │   Taswir al-Mas'alah      │
                         └─────────────┬─────────────┘
                                       │
                                       ▼
                         ┌───────────────────────────┐
                         │     RESEARCH PLANNER      │
                         │ What must be investigated?│
                         └─────────────┬─────────────┘
                                       │
             ┌─────────────────────────┼─────────────────────────┐
             │                         │                         │
             ▼                         ▼                         ▼
      ┌────────────┐           ┌────────────┐           ┌────────────┐
      │ QUR'AN     │           │ HADITH     │           │ FIQH       │
      │ Agent      │           │ Agent      │           │ Agent      │
      └────────────┘           └────────────┘           └────────────┘
             │                         │                         │
      ┌────────────┐           ┌────────────┐           ┌────────────┐
      │ TAFSIR     │           │ SANAD      │           │ MADHHAB    │
      │ Agent      │           │ Agent      │           │ Agent      │
      └────────────┘           └────────────┘           └────────────┘
             │                         │                         │
      ┌────────────┐           ┌────────────┐           ┌────────────┐
      │ BALAGHA    │           │ TAKHRIJ    │           │ USUL       │
      │ Agent      │           │ Agent      │           │ Agent      │
      └────────────┘           └────────────┘           └────────────┘
             │                         │                         │
             └─────────────────────────┼─────────────────────────┘
                                       ▼
                         ┌───────────────────────────┐
                         │  EVIDENCE / PROVENANCE    │
                         │      CONSTRUCTOR          │
                         └─────────────┬─────────────┘
                                       ▼
                         ┌───────────────────────────┐
                         │    ANALYSIS / ISTINBAT     │
                         │       CONSTRUCTOR          │
                         └─────────────┬─────────────┘
                                       ▼
                         ┌───────────────────────────┐
                         │    CONTRADICTION AGENT    │
                         │  "Try to disprove this"   │
                         └─────────────┬─────────────┘
                                       ▼
                         ┌───────────────────────────┐
                         │   SCHOLAR REVIEW GATE      │
                         └─────────────┬─────────────┘
                                       ▼
                  ┌────────────────────┼────────────────────┐
                  ▼                    ▼                    ▼
             REPORTING             GRAPH/VIEW            MEMORY
             Agent                 Agent                  Agent
```

This is much better than simply creating 15 autonomous LLM agents.

The **specialists should mostly be skill/tool modules**, while a smaller number of reasoning agents orchestrate them.

That distinction will save a huge amount of inference.

---

# 2. Don't make every specialist an LLM

This is probably the most important optimization.

For example:

### Sanad Agent

It shouldn't necessarily be:

> LLM → "Please analyze this sanad."

Instead:

```text
Sanad Tool
   ↓
Isnad database
   ↓
Narrator database
   ↓
Jarh wa Ta'dil database
   ↓
Hadith variants
   ↓
Known grading
   ↓
LLM only when interpretation is required
```

Likewise:

### Citation Agent

should mostly be deterministic:

```text
Source ID
Book
Volume
Page
Chapter
Hadith #
Ayah
Edition
Manuscript/source metadata
```

The LLM should **interpret citations**, not invent them.

### Qur'an Agent

Could use:

```text
exact Arabic search
root search
lemma search
semantic search
tafsir index
cross-reference graph
```

before invoking the reasoning model.

### Madhhab Agent

Could query a structured database:

```text
mas'alah
    ↓
school
    ↓
position
    ↓
scholar
    ↓
source
    ↓
evidence
    ↓
reason
```

The model then synthesizes it.

This is consistent with the skill-driven architecture in your existing runtime: capabilities can be encapsulated as discoverable skills and called as tools during reasoning. 

---

# 3. Think of the LLM as the "CPU", not the database

This gives you a very useful architecture:

```text
                ┌──────────────────┐
                │   Reasoning LLM  │
                └────────┬─────────┘
                         │
       ┌─────────────────┼─────────────────┐
       │                 │                 │
       ▼                 ▼                 ▼
   Retrieval          Analysis          Planning
       │                 │                 │
       ▼                 ▼                 ▼
  Knowledge Base     Usul Engine       Workflow
       │
       ▼
  Evidence Graph
```

The model does not need to remember everything.

It needs to know:

> **What should I ask the system next?**

That is much more achievable with a smaller local reasoning model.

---

# 4. I would introduce a "Scholarly Kernel"

This could become the heart of the project.

The kernel maintains a structured **Research State**.

For example:

```json
{
  "masalah": "...",
  "taswir": {},
  "assumptions": [],
  "questions": [],
  "evidence": [],
  "quran": [],
  "hadith": [],
  "tafsir": [],
  "madhhab": [],
  "ijma": [],
  "ikhtilaf": [],
  "usul": [],
  "qawaid": [],
  "qiyas": [],
  "maqasid": [],
  "arguments_for": [],
  "arguments_against": [],
  "unresolved": [],
  "scholar_annotations": [],
  "proposed_conclusion": null
}
```

But **do not send this entire object to the model every time**.

That is where your token optimization becomes extremely important.

---

# 5. Use "state + retrieval", not "conversation history"

Your uploaded runtime already has a useful concept here: separate working, conversation, vector and episodic memory, with context assembly based on token budget. 

For this project I would extend that into:

### L0 — Current reasoning

Only the current subproblem.

### L1 — Research state

Small structured state.

### L2 — Evidence index

IDs and metadata, not full documents.

### L3 — Evidence content

Retrieve only when required.

### L4 — Scholarly memory

Previous validated conclusions, annotations, preferences and methodology.

### L5 — Corpus

The actual books.

So instead of putting 50 pages into the prompt:

```text
Evidence:
E17
E23
E51
E82

Relevant proposition:
E17 supports X
E23 contradicts X
E51 provides usul basis
```

Then retrieve the actual passages only when the reasoning step needs them.

---

# 6. Evidence should have IDs

This is extremely important.

For example:

```text
Q-001
H-014
T-032
F-HAN-122
U-SHA-041
QAWA-008
```

Then the model can reason using IDs:

> H-014 conflicts with H-021.

The system can resolve those IDs to the full citations.

This dramatically reduces tokens.

It also makes hallucinated citations much easier to detect.

---

# 7. Build an Evidence Graph, not only RAG

Your uploaded runtime already contains graph query/upsert capabilities, which are directly useful here. 

The graph could look like:

```text
Ayah
 │
 ├── interpreted_by → Tafsir
 │
 ├── contains → Legal_term
 │
 └── related_to → Hadith

Hadith
 │
 ├── narrated_by → Narrator
 ├── graded_by → Scholar
 ├── explained_by → Sharh
 └── used_by → Faqih

Faqih
 │
 ├── follows → Usul principle
 ├── holds → Position
 └── cites → Evidence

Position
 │
 ├── supports → Hukm
 ├── based_on → Qiyas
 └── differs_from → Position
```

Now your agent can ask graph questions instead of asking the LLM to reconstruct relationships from text.

---

# 8. The orchestration should be hierarchical

I would **not** have:

```text
Agent A talks to Agent B
Agent B talks to Agent C
Agent C talks to Agent D
...
```

That becomes expensive and chaotic.

Instead:

```text
                    SUPERVISOR
                        │
                 RESEARCH PLAN
                        │
          ┌─────────────┼─────────────┐
          ▼             ▼             ▼
       QUR'AN        HADITH        FIQH/USUL
          │             │             │
          └─────────────┼─────────────┘
                        ▼
                    SYNTHESIS
                        │
                        ▼
                  CHALLENGE AGENT
                        │
                        ▼
                  REVIEW / REPORT
```

The runtime material already supports sequential, parallel, loop and delegation patterns. 

For example:

### Parallel

Qur'an + Hadith + Madhhab searches happen simultaneously.

### Sequential

Evidence → analysis → challenge → report.

### Loop

Research → evaluate completeness → identify missing evidence → research again.

But set hard budgets:

```text
max_research_rounds = 3
max_specialists = 5
max_tool_calls = 20
max_tokens_per_agent = X
```

The runtime material specifically identifies reducing max steps/tokens, improving skill descriptions, early termination and caching as ways to control excessive token usage. 

---

# 9. Don't use the largest model everywhere

This is another major optimization.

You can use a **model hierarchy**.

### Small model

Use for:

* classification
* routing
* query rewriting
* metadata extraction
* citation formatting
* deduplication
* simple summarization
* deciding which tool to call

### Medium reasoning model

Use for:

* evidence comparison
* tafsir comparison
* usul analysis
* madhhab comparison
* contradiction detection

### Strong reasoning model

Use only for:

* difficult istinbāṭ analysis
* complex qiyas
* conflicting evidence
* final research synthesis
* difficult scholar review assistance

So:

```text
100 requests
    ↓
80 handled by deterministic tools / small model
    ↓
15 medium reasoning
    ↓
5 deep reasoning
```

That could make local deployment dramatically more practical.

---

# 10. Fine-tuning: yes, but NOT first

I strongly agree with your thought that continuous fine-tuning is attractive.

But I would **not start there**.

Your first version can be surprisingly capable with:

> **Base reasoning model + structured tools + excellent retrieval + structured state + specialized prompts + evaluation + scholar feedback**

Then collect real interactions.

Only afterward ask:

> Which failures actually require model training?

This distinction is critical.

---

# 11. There are four different things people call "learning"

I would explicitly separate them.

### A. Runtime learning

No model training.

The scholar says:

> "This interpretation is incorrect."

Store:

```text
case
mistake
scholar correction
reason
source
```

Future runs retrieve this correction.

**Cheap. Immediate.**

---

### B. Knowledge learning

A scholar adds:

> "This manuscript/reference should also be considered."

Store it in the knowledge graph / corpus.

No model training.

**Cheap and immediate.**

---

### C. Behavioral learning

After thousands of interactions:

```text
Scholar prefers:
- Hanafi usul first
- Arabic quotations
- classical sources prioritized
- weak hadith explicitly flagged
- no conclusion unless evidence threshold met
```

This becomes a **scholar methodology profile**.

Again, no fine-tuning required.

---

### D. Model learning

Only now:

> Fine-tune the model.

Use the accumulated high-quality examples.

This is expensive, so do it when the data shows a clear benefit.

---

# 12. Reinforcement learning should come later

I would absolutely design the system so that RL is possible.

But don't begin by training an RL model.

First create the **reward infrastructure**.

For example:

```text
Research Task
      ↓
Agent trajectory
      ↓
Evidence retrieved
      ↓
Reasoning
      ↓
Conclusion
      ↓
Scholar review
      ↓
Reward signals
```

Rewards could include:

```text
+ correct source
+ complete citation
+ correct hadith grading
+ relevant evidence
+ identifies disagreement
+ detects contradiction
+ correct usul application
+ scholar accepts reasoning

- hallucinated citation
- fabricated quote
- missed major evidence
- wrong attribution
- unsupported conclusion
- ignored contrary evidence
```

Then you have a dataset for future optimization.

Your existing runtime's evaluation architecture is already oriented toward this type of trajectory/tool-use evaluation: tool-use quality, multi-turn trajectory, grounding, hallucination and custom rubrics are all represented. 

That is a very good foundation.

---

# 13. In fact, I would create a "Scholar Feedback Protocol"

Every interaction could end with:

```text
Research quality:
[ Excellent ] [ Good ] [ Needs correction ]

Evidence:
✓ Correct
✗ Missing
✗ Incorrect

Reasoning:
✓ Correct
✗ Incorrect

Usul:
✓ Correct
✗ Incorrect

Conclusion:
✓ Useful
✗ Not useful

Scholar correction:
____________________
```

But the really valuable part is:

> **Why?**

Because a binary reward tells you little.

A correction such as:

> "The agent treated this as qiyas, but this is actually taḥqīq al-manāṭ."

is enormously valuable training data.

---

# 14. This creates a continuous improvement loop

I would design it as:

```text
                 ┌──────────────────┐
                 │   ULAMA SESSION   │
                 └────────┬─────────┘
                          ▼
                    AGENT RESEARCH
                          ▼
                    AGENT ANALYSIS
                          ▼
                    SCHOLAR REVIEW
                          ▼
                 ┌──────────────────┐
                 │   CORRECTIONS    │
                 └────────┬─────────┘
                          │
             ┌────────────┼────────────┐
             ▼            ▼            ▼
          Memory       Knowledge     Dataset
             │            │            │
             ▼            ▼            ▼
          Runtime       Graph       Training
             │                         │
             └────────────┬────────────┘
                          ▼
                    BETTER AGENT
```

This is what I mean by making the system **trainable without requiring constant retraining**.

---

# 15. Runtime knowledge should be versioned

For Islamic scholarship, this is particularly important.

Suppose an ʿālim teaches the system:

> "For our research methodology, treat X according to position Y."

Don't overwrite the old information.

Create:

```text
Knowledge v1
Knowledge v2
Scholar annotation
Scholar attribution
Date
Source
Confidence
Scope
```

Then:

> **Who said this?**

becomes answerable.

And:

> **Is this from the corpus or from this scholar's interpretation?**

also becomes answerable.

This is essential for scholarly integrity.

---

# 16. I would also give each scholar a "Methodology Profile"

For example:

```text
Scholar Profile

Primary madhhab:
Hanafi

Usul preference:
Hanafi

Comparative:
Enabled

Hadith threshold:
...

Preferred sources:
...

Language:
Arabic first

Citation style:
Classical + modern edition

Conclusion policy:
No definitive conclusion if major disagreement unresolved

Research depth:
Deep

Maqasid:
Enabled
```

This does **not** mean the system changes facts.

It means the orchestration knows:

> **How should I perform this scholar's research workflow?**

That makes personalization possible without corrupting the knowledge base.

---

# 17. Token optimization should be designed into the protocol

I would create a strict internal protocol.

Instead of every agent returning paragraphs, specialists return **compact structured evidence**.

For example:

```json
{
  "evidence_id": "H-014",
  "type": "hadith",
  "relevance": 0.94,
  "supports": ["claim-03"],
  "status": "sahih",
  "source": "....",
  "quote": "...",
  "reason": "..."
}
```

Then only the final reporting agent expands it into prose.

This is a huge difference.

### Internal agents:

**structured + compressed**

### Final report:

**human-readable + detailed**

---

# 18. Use progressive disclosure

Don't send:

> Qur'an + 20 tafsir books + 100 hadith + 50 fiqh books

into the context.

Do:

```text
Stage 1
Find candidates.

      ↓

Stage 2
Rank candidates.

      ↓

Stage 3
Retrieve primary passages.

      ↓

Stage 4
Retrieve relevant commentary.

      ↓

Stage 5
Deep analyze only contested evidence.
```

Your existing retrieval layer already has concepts such as top-K, similarity threshold, hybrid search, reranking, MMR, grouping and deduplication that can support this strategy. 

---

# 19. Cache aggressively

Some things should almost never require another LLM call.

Cache:

* Arabic morphology
* verse metadata
* hadith metadata
* narrator information
* citation resolution
* common tafsir passages
* common qawa'id
* common madhhab positions
* embeddings
* search results
* scholar corrections
* previously validated reasoning components

The LLM should be spending compute on **novel reasoning**, not rediscovering the same metadata.

---

# 20. Your architecture therefore becomes four systems

I would conceptually divide Kallam into:

### Kallam Corpus

The authoritative knowledge.

```text
Qur'an
Hadith
Tafsir
Sharh
Fiqh
Usul
Arabic
Balaghah
Rijal
Tarikh
etc.
```

### Kallam Knowledge Graph

Relationships between them.

```text
who said what
based on what
contradicts what
explains what
belongs to which madhhab
```

### Kallam Agent Runtime

The reasoning/orchestration system.

```text
planner
specialists
tools
memory
reasoning
verification
challenge
reporting
```

### Kallam Learning System

Continuous improvement.

```text
scholar feedback
evaluation
corrections
trajectories
reward
datasets
fine-tuning
RL
```

This separation is very powerful.

---

# 21. And I would add one more agent: the "Methodologist"

Not a fiqh agent.

Not a conclusion agent.

Its job is:

> **"Has the research actually followed a sufficiently rigorous istinbāṭ research procedure?"**

It checks:

```text
Was taswir established?
Was primary evidence searched?
Was contradictory evidence searched?
Was hadith status checked?
Was linguistic analysis performed?
Was ikhtilaf identified?
Was ijma checked?
Was usul applied consistently?
Was qiyas justified?
Was the illah established?
Were exceptions considered?
Were factual assumptions verified?
Were citations verified?
```

It does not determine the hukm.

It determines:

> **"Is the research process sufficiently complete for the scholar to review?"**

I think this could become one of the defining features of the system.

---

# 22. So can a current reasoning model do this?

**Yes—if you don't ask the model to be the whole system.**

A current local reasoning model can already act as:

* planner
* router
* researcher
* comparator
* synthesizer
* critic
* report writer
* tool user

provided that the **knowledge, provenance, specialist capabilities, state and verification mechanisms live outside the model**.

The model is then effectively:

> **a reasoning component inside a scholarly computational environment.**

That is a much more realistic path than trying to train a gigantic "Islamic Mujtahid Model."

---

# 23. The development strategy I would recommend

### Phase 1 — No fine-tuning

Build:

```text
Base local reasoning model
+
Qur'an retrieval
+
Hadith retrieval
+
Tafsir retrieval
+
Fiqh/Madhhab retrieval
+
Usul retrieval
+
Citation engine
+
Evidence graph
+
Research state
+
Orchestrator
+
Scholar review
```

Focus heavily on **grounding and provenance**.

### Phase 2 — Specialist tools

Add:

```text
Arabic
Balaghah
Sanad
Takhrij
Madhhab
Ikhtilaf
Qawa'id
Qiyas
Maqasid
```

### Phase 3 — Research loops

Add:

```text
planner
→ parallel research
→ synthesis
→ contradiction
→ completeness check
→ additional research
→ scholar review
```

### Phase 4 — Scholar memory

Capture:

```text
corrections
preferences
methodology
validated conclusions
```

### Phase 5 — Evaluation dataset

Every scholar-reviewed case becomes an evaluation/training case.

### Phase 6 — Fine-tuning

Fine-tune only where evaluation shows the base model consistently fails.

### Phase 7 — RL / preference optimization

Use scholar-approved trajectories and corrections to optimize:

* tool selection
* research depth
* evidence selection
* reasoning quality
* citation discipline
* contradiction detection

---

## The key idea

I would summarize the whole architecture as:

> **Corpus provides knowledge.
> Graph provides relationships.
> Tools provide specialized expertise.
> Agents provide decomposition.
> The reasoning model provides synthesis.
> Verification provides trust.
> The scholar provides judgment.
> Feedback provides continuous learning.**

And importantly, **fine-tuning becomes an optimization layer rather than the foundation of the system**.

That gives you a path to start with today's local reasoning models and progressively turn the system into something much more sophisticated as scholars use it.

Your existing runtime is already surprisingly close to the infrastructure needed: skill-driven capabilities, multi-layer memory, multiple orchestration patterns, context/token budgeting, graph operations, hybrid retrieval, and evaluation/trajectory infrastructure are all already represented in the material you uploaded.   

The next architectural step I would take is to define the **Kallam Istinbāṭ Protocol**: the exact state schema, specialist-agent contracts, evidence object, provenance model, orchestration states, token budgets, scholar-feedback events, and evaluation/reward schema. Once that protocol is solid, the choice of local model becomes much less of a constraint because the model becomes one replaceable component of the system rather than the system itself.


----------PROMPT------

great i think the architecture is enough right? next perhaps the true/actual code implementation  

----------------

Yes. **I think the architecture is sufficiently mature to move into actual implementation.** In fact, I would stop adding major architectural concepts for now and start turning the architecture into executable code.

The important thing is that we should **not build the whole Kallam system at once**. We should implement a thin but real vertical slice first, then expand specialist by specialist.

Your existing Andalus runtime already gives us substantial infrastructure to build on: skill-driven tools, memory, RAG/hybrid retrieval, orchestration, execution state, caching, and graph capabilities.  The existing runtime also already has hybrid dense/keyword retrieval and RRF-style merging.  There is also retrieval caching and execution budgeting that we can exploit for the local/token-constrained design. 

## I would start with a real MVP vertical slice

Not 20 agents.

Start with:

```text
                    KALLAM
                      │
                      ▼
              Istinbat Research
                 Coordinator
                      │
          ┌───────────┼───────────┐
          ▼           ▼           ▼
       Qur'an       Hadith      Fiqh/Usul
       Search       Search       Search
          │           │           │
          └───────────┼───────────┘
                      ▼
                Evidence Builder
                      │
                      ▼
                 Analysis Agent
                      │
                      ▼
              Challenge / Critic
                      │
                      ▼
               Research Report
                      │
                      ▼
                 Scholar Review
```

Then once this actually works, we add:

* Tafsir
* Balaghah
* Sanad
* Takhrij
* Madhhab
* Ikhtilaf
* Qawa'id
* Qiyas
* Maqasid
* visualization
* scholar memory
* continuous learning
* RL/evaluation

---

# The first implementation target

I would create a new bounded module along these lines:

```text
kallam/
├── core/
│   ├── research/
│   │   ├── ResearchCase.java
│   │   ├── ResearchState.java
│   │   ├── ResearchClaim.java
│   │   ├── Evidence.java
│   │   ├── Citation.java
│   │   └── ResearchPhase.java
│   │
│   ├── orchestration/
│   │   ├── IstinbatCoordinator.java
│   │   ├── ResearchPlanner.java
│   │   └── ResearchLoop.java
│   │
│   └── scholar/
│       ├── ScholarProfile.java
│       └── ScholarFeedback.java
│
├── skills/
│   ├── quran/
│   │   └── QuranResearchSkill.java
│   ├── hadith/
│   │   └── HadithResearchSkill.java
│   ├── fiqh/
│   │   └── FiqhResearchSkill.java
│   ├── usul/
│   │   └── UsulResearchSkill.java
│   ├── citation/
│   │   └── CitationVerificationSkill.java
│   └── critique/
│       └── ResearchCriticSkill.java
│
├── retrieval/
│   ├── EvidenceRetriever.java
│   ├── HybridEvidenceRetriever.java
│   └── EvidenceReranker.java
│
├── graph/
│   ├── EvidenceGraph.java
│   └── EvidenceGraphService.java
│
└── reporting/
    ├── ResearchReport.java
    └── ResearchReportBuilder.java
```

The exact package/module names can adapt to your existing Andalus conventions.

---

# 1. First class: `ResearchCase`

This is the root of everything.

```java
public record ResearchCase(
    String id,
    String question,
    String language,
    ResearchState state,
    Instant createdAt
) {}
```

But `ResearchState` should be structured rather than a giant prompt string.

For example:

```java
public record ResearchState(
    Masalah masalah,
    List<Claim> claims,
    List<EvidenceRef> evidence,
    List<Position> positions,
    List<UsulAnalysis> usulAnalysis,
    List<Argument> argumentsFor,
    List<Argument> argumentsAgainst,
    List<String> unresolvedQuestions,
    ResearchAssessment assessment
) {}
```

This becomes the **source of truth for the research**, not the conversation transcript.

---

# 2. Evidence becomes a first-class object

This is probably the most important domain object.

```java
public record Evidence(
    String id,
    EvidenceType type,
    String arabicText,
    String translation,
    SourceReference source,
    EvidenceStatus status,
    double relevance,
    List<String> supportsClaims,
    List<String> contradictsClaims,
    Provenance provenance
) {}
```

Where:

```java
public enum EvidenceType {
    QURAN,
    HADITH,
    ATHAR,
    TAFSIR,
    FIQH,
    USUL,
    IJMA,
    QAWAID,
    SCHOLAR_STATEMENT,
    CONTEMPORARY_RESEARCH
}
```

And:

```java
public enum EvidenceStatus {
    PRIMARY_SOURCE,
    SCHOLAR_INTERPRETATION,
    INFERENCE,
    DISPUTED,
    UNVERIFIED
}
```

That gives us the epistemic separation we discussed.

---

# 3. Citation must be deterministic

The LLM should never be responsible for creating a citation from memory.

Instead:

```java
public record SourceReference(
    String sourceId,
    String work,
    String author,
    String edition,
    String volume,
    String page,
    String chapter,
    String section,
    String url,
    String locator
) {}
```

The model can request:

```text
sourceId = TAFSIR_TABARI_00231
```

and the citation system resolves it.

This is especially important because the runtime already has tool/skill abstractions that allow us to separate actual capability execution from reasoning. 

---

# 4. Then build the first actual Skill

For example:

```java
@SkillDescriptor(
    id = "kallam.quran.research",
    name = "Quran Research",
    description = "Finds and structures relevant Quranic evidence."
)
public class QuranResearchSkill implements AgentSkill {

    @Override
    public Uni<SkillResult> execute(
            SkillContext context,
            Map<String, Object> arguments) {

        String query = (String) arguments.get("query");

        // exact search
        // lemma/root search
        // semantic search
        // retrieve metadata
        // return compact Evidence objects

        return ...;
    }
}
```

The crucial point is that this skill should **not produce a long essay**.

It should produce structured evidence.

---

# 5. Same pattern for Hadith

```java
@SkillDescriptor(
    id = "kallam.hadith.research",
    name = "Hadith Research",
    description = "Finds hadith evidence, variants, grading and scholarly references."
)
public class HadithResearchSkill implements AgentSkill {
    ...
}
```

Initially it can return:

```json
{
  "evidenceId": "H-000142",
  "matn": "...",
  "source": "...",
  "book": "...",
  "hadithNumber": "...",
  "grading": "...",
  "graders": [],
  "variants": [],
  "relevance": 0.93
}
```

Later we plug in the actual sanad/takhrij subsystem.

---

# 6. The coordinator becomes relatively small

This is where I think our earlier architecture pays off.

Something like:

```java
public class IstinbatCoordinator {

    public Uni<ResearchReport> research(ResearchRequest request) {

        return planner.plan(request)
            .flatMap(plan ->
                executeResearch(plan)
            )
            .flatMap(state ->
                analyzer.analyze(state)
            )
            .flatMap(state ->
                critic.challenge(state)
            )
            .flatMap(state ->
                reportBuilder.build(state)
            );
    }
}
```

The coordinator does **not** contain fiqh logic.

It orchestrates.

That means later we can replace:

```text
QuranResearchSkill
```

with a much more sophisticated implementation without rewriting the orchestration.

---

# 7. Parallelize the specialists

This is where the local inference optimization becomes practical.

Instead of:

```text
Quran → Hadith → Fiqh → Usul
```

we do:

```text
             Research Plan
                  │
        ┌─────────┼─────────┐
        ▼         ▼         ▼
      Quran     Hadith     Fiqh
        │         │         │
        └─────────┼─────────┘
                  ▼
                Usul
```

Qur'an, Hadith and Fiqh retrieval can often happen concurrently because they don't need the outputs of one another.

Only the **analysis** stage needs the combined evidence.

Your existing runtime explicitly supports orchestration patterns including parallel execution. 

---

# 8. Token optimization starts immediately

I would make a rule:

> **No specialist is allowed to return arbitrary prose to the coordinator.**

Instead:

```text
Agent → compact JSON/domain object → coordinator
```

For example:

```json
{
  "id": "Q-021",
  "type": "QURAN",
  "relevance": 0.91,
  "supports": ["C-02"],
  "locator": "2:275",
  "snippet": "...",
  "sourceId": "QURAN_CANONICAL"
}
```

Then the coordinator only expands `Q-021` when necessary.

This should substantially reduce context pressure.

---

# 9. The model should receive an "evidence packet"

Instead of:

> Here are 70 retrieved documents...

we construct:

```text
RESEARCH QUESTION
C-01 ...

EVIDENCE
Q-021 ...
Q-031 ...
H-014 ...
F-HAN-007 ...

CONFLICTS
H-014 ↔ H-019

OPEN QUESTIONS
OQ-02 ...

TASK
Evaluate whether the evidence supports C-01.
```

This is exactly the kind of context engineering that your existing runtime's context management and retrieval infrastructure can support. The runtime already contains hybrid retrieval, reranking and context-window management concepts. 

---

# 10. Then implement the Critic

This is our first genuinely interesting reasoning component.

The critic receives only:

```text
question
research state
proposed analysis
```

and asks:

```text
1. Missing primary evidence?
2. Contradictory evidence?
3. Incorrect attribution?
4. Citation problem?
5. Invalid inference?
6. Usul problem?
7. Qiyas problem?
8. Factual assumption?
9. Unresolved ikhtilaf?
10. Overconfidence?
```

Output:

```java
public record ResearchCritique(
    List<CritiqueFinding> findings,
    List<String> requiredResearch,
    double completeness
) {}
```

Then the coordinator can loop:

```text
Research
   ↓
Analysis
   ↓
Critique
   ↓
Missing evidence?
   ├── YES → Research again
   └── NO  → Report
```

But with a hard iteration budget.

---

# 11. And this gives us our first real Kallam MVP

A user could eventually type:

> "Research the fiqh implications of X."

And get:

```text
KALLAM RESEARCH CASE

Mas'alah
...

Taswir
...

Qur'an
  Q-001
  Q-002

Hadith
  H-001
  H-002

Fiqh positions
  Hanafi
  Maliki
  Shafi'i
  Hanbali

Usul
...

Points of agreement
...

Points of disagreement
...

Arguments
...

Critique
...

Unresolved questions
...

Preliminary synthesis
...

Sources
...
```

And **not**:

> "The ruling is definitely X."

---

# 12. I would NOT implement fine-tuning yet

We can make the entire system **fine-tuning-ready from day one**.

Every execution should optionally record:

```text
ResearchCase
ResearchPlan
ToolCalls
RetrievedEvidence
AgentOutputs
Critiques
ScholarCorrections
FinalReport
EvaluationScores
```

That becomes your future training dataset.

Your existing execution state/event infrastructure is already suitable for this kind of trajectory capture. 

Then later:

```text
                    CASES
                      │
                      ▼
              Scholar Corrections
                      │
                      ▼
              Curated Dataset
                      │
              ┌───────┴────────┐
              ▼                ▼
         SFT / LoRA       Preference/RL
              │                │
              └───────┬────────┘
                      ▼
                Better Model
```

No need to block implementation on training.

---

# 13. One thing I would do before writing the code

Because your uploaded runtime appears to already contain substantial implementations—and some pieces are explicitly marked incomplete—we should **map the existing code before creating duplicate infrastructure**.

For example, the documentation says the core implementation already has:

* `AgentOrchestrator`
* `AgentState`
* `AgentConfig`
* `ReActOrchestrator`
* `AgentMemory`
* `ToolProvider`
* `ToolAdapter`
* skill infrastructure. 

And the runtime already has vector memory and retrieval implementations. 

So the next coding step should be:

### **Codebase reconnaissance → identify extension points → implement Kallam domain layer → wire into existing runtime.**

Not:

> create another agent framework inside Andalus.

That would duplicate what you already have.

---

## So yes: I would move to actual code now.

And I recommend we do it **incrementally and actually executable**, not pseudo-code.

### First coding milestone

**Kallam Istinbāṭ Core v0.1**

Implement:

1. `ResearchCase`
2. `ResearchState`
3. `Evidence`
4. `Citation/SourceReference`
5. `ResearchPlan`
6. `ResearchCritique`
7. `IstinbatCoordinator`
8. `QuranResearchSkill`
9. `HadithResearchSkill`
10. `FiqhResearchSkill`
11. `UsulResearchSkill`
12. `CitationVerificationSkill`
13. `ResearchCriticSkill`
14. structured research report
15. execution/trajectory logging
16. token/research budgets

Then make **one complete end-to-end research case actually run locally**.

After that, we add the specialized Islamic knowledge engines one by one.

That approach gives us something much more valuable than another architecture document: **a running Kallam research agent that we can test, inspect, benchmark, and progressively teach.**


------

Done. I moved from architecture into the **first actual implementation layer: Kallam Istinbāṭ Core v0.1**.

[Download the Kallam Istinbāṭ Core v0.1 source](sandbox:/mnt/data/kallam-istinbat-core-v0.1.zip)

### What is implemented

* `ResearchCase` / `ResearchRequest`
* `ResearchState`
* `Mas'alah` and initial `Taswīr`
* `Evidence` with epistemic status and provenance
* `SourceReference`
* research phases
* research budgets
* `ResearchPlan`
* specialist interface
* analysis interface
* critic interface
* report builder
* `IstinbatCoordinator`
* conservative completeness critic
* basic research planner
* basic report generation
* unit tests
* standalone Maven project

I intentionally **did not duplicate the existing Andalus/Gollek agent runtime**. Your uploaded runtime already has the agent orchestration, skills, RAG, memory, and related infrastructure we should reuse.  It also already supports RAG results/citations and hybrid retrieval.  

### The important next step

Now we should make this **actually run inside your existing runtime**, rather than keeping the core standalone.

The next implementation should be:

```text
Kallam Core
     │
     ▼
Andalus/Gollek Orchestrator
     │
     ├── Quran Skill
     ├── Hadith Skill
     ├── Fiqh Skill
     ├── Usul Skill
     └── Citation Skill
              │
              ▼
        Existing RAG Runtime
              │
              ▼
       Evidence → ResearchState
              │
              ▼
       Local Reasoning Model
              │
              ▼
       Critic / Completeness
              │
              ▼
        Scholar Research Report
```

The existing runtime already has `PlanAndExecute`, `ReAct`, `Reflexion`, skill loading, and tool execution mechanisms that are appropriate extension points. 

**One important constraint:** the uploaded `andalus-runtime.txt` is a source dump/documentation snapshot rather than the actual repository tree available to me as editable files. So I made the domain layer cleanly isolated rather than guessing exact integration APIs and potentially corrupting your existing codebase.

If you provide the **actual Andalus/Gollek repository/source tree** next, I can take the next step as a real code integration: wire these classes into the existing `AgentOrchestrator`/skills/RAG infrastructure, implement the first **Qur'an + Hadith research specialists**, and make one complete local end-to-end Istinbāṭ research case executable.



--------------

Good. I continued to **v0.2: the actual Andalus RAG integration boundary**.

[Download Kallam Istinbāṭ v0.2 — RAG Integration](sandbox:/mnt/data/kallam-istinbat-v0.2-rag-integration.zip)

I checked the uploaded runtime more deeply first. The existing RAG layer already exposes a `Retriever → RagQuery → RagScoredChunk → RagChunk` path, with chunk metadata available for provenance.  

### What this version adds

**1. `AndalusRagEvidenceRetriever`**

An anti-corruption layer:

```text
Andalus RAG
   ↓
RagChunk
   ↓
Kallam Evidence
   ↓
ResearchState
```

So Kallam does not become coupled to the internal RAG representation.

**2. Generic `RagResearchSpecialist`**

The same specialist implementation can currently represent:

```text
quran
hadith
fiqh
usul
tafsir
madhhab
```

This is intentional. We should **not duplicate retrieval engines** for every discipline.

**3. Metadata-driven specialization**

The corpus can eventually be queried using metadata such as:

```text
evidenceType = QURAN
evidenceType = HADITH
evidenceType = TAFSIR
madhhab = HANAFI
kallam.primarySource = true
```

The existing RAG runtime already supports metadata filters and hybrid retrieval.  Its hybrid strategy combines dense and keyword retrieval using reciprocal-rank fusion. 

**4. Evidence provenance**

The adapter maps:

```text
sourceId
work
author
edition
volume
page
chapter
locator
documentId
chunkId
```

into Kallam's `SourceReference` and provenance model.

**5. Specialist factory**

We now have one explicit place where specialist capabilities are assembled.

---

## The next step is where it gets much more interesting

Now we have:

```text
Question
   ↓
Research Planner
   ↓
Specialist
   ↓
Andalus RAG
   ↓
Evidence
```

The next missing piece is:

```text
Evidence
   ↓
┌──────────────────────────────┐
│ LOCAL REASONING MODEL        │
│                              │
│ Taswir                       │
│ Evidence comparison          │
│ Usul analysis                │
│ Claim construction           │
│ Contradiction detection      │
└──────────────┬───────────────┘
               ↓
        Structured Analysis
               ↓
            Critic
```

And the uploaded runtime already has the backend abstraction we need: `InferenceBackend` with `InferenceRequest`, and the Gollek adapter implements that abstraction.  

So **next I would implement `LLMResearchAnalyzer`**, but importantly not as a generic chatbot.

It should receive a **small Evidence Packet**, request structured JSON, validate it, and merge it back into `ResearchState`.

That will give us our first genuine loop:

```text
             USER
               │
               ▼
        Research Planner
               │
      ┌────────┼────────┐
      ▼        ▼        ▼
    Quran    Hadith    Fiqh
      │        │        │
      └────────┼────────┘
               ▼
          Evidence Set
               │
               ▼
       Local Reasoning Model
               │
               ▼
        Structured Analysis
               │
               ▼
            Critic
               │
       ┌───────┴───────┐
       │               │
    incomplete       ready
       │               │
       ▼               ▼
    Research       Scholar Report
      again
```

This is the point where Kallam stops being merely a collection of retrieval tools and starts becoming the **actual istinbāṭ research assistant**.

And we should keep the model context deliberately small: **IDs + compact evidence + only the relevant quotations**, rather than dumping the whole RAG context into the local model. The existing runtime also already has retrieval caching and execution budgets we can use for this. 

**Next milestone: v0.3 — Local Reasoning + Structured Evidence Analysis + Critic loop.**


Yes. We can continue from v0.3 conceptually, but I need to be transparent about one limitation: **the previous file-generation tool was rate-limited**, so I cannot honestly claim that a new v0.3 source archive was created in this turn.

The next milestone I would implement is **v0.3.1 — constrained reasoning + semantic validation**, before moving to v0.4 citation verification.

### Why this should come before v0.4

For a local model, `"return valid JSON"` in the prompt is not enough. We want the inference layer to enforce the structure at decoding time where possible, then validate the result semantically afterward. llama.cpp currently supports JSON Schema/constrained decoding through LLGuidance, including JSON-schema requests; its documented token-mask overhead is very small in typical cases. ([GitHub][1])

So the pipeline becomes:

```text
                 Evidence Packet
                       │
                       ▼
              Local Reasoning Model
                       │
          ┌────────────┴────────────┐
          │                         │
   constrained JSON            token budget
          │
          ▼
     JSON decoder
          │
          ▼
    Schema validation
          │
          ▼
  Semantic validation
          │
          ▼
    ResearchState
          │
          ▼
        Critic
```

## The important distinction

We should have **three validation layers**:

### 1. Syntactic

Can we parse the output?

```text
valid JSON?
```

### 2. Structural

Does it satisfy the schema?

```text
claims[]
arguments[]
conflicts[]
missingEvidence[]
usulQuestions[]
synthesis
confidence
```

### 3. Epistemic / semantic

This is the important Kallam layer.

For example, the model says:

```json
{
  "claimId": "C1",
  "assessment": "SUPPORTED",
  "evidenceIds": ["E17"]
}
```

Kallam checks:

```text
Does E17 actually exist?
        ↓
Does E17 belong to this ResearchCase?
        ↓
Is E17 allowed to support C1?
        ↓
Is E17 primary evidence or interpretation?
        ↓
Does the model's reasoning distinguish the two?
```

If not:

```text
REJECT / FLAG
```

The model does **not** get to invent an evidentiary relationship merely because the JSON is valid.

---

# The next core object

I would introduce:

```java
public record EvidenceAssertion(
    String claimId,
    String evidenceId,
    AssertionType type,
    String reasoning
) {}
```

with:

```java
public enum AssertionType {
    DIRECT_SUPPORT,
    INDIRECT_SUPPORT,
    CONTRADICTION,
    CONTEXT,
    INTERPRETATION
}
```

This is better than storing evidence IDs inside arbitrary prose.

We can then construct an actual graph:

```text
             C1
            /  \
           /    \
       DIRECT   INDIRECT
         /        \
       E01        E07
        │          │
      Quran      Tafsir
```

And:

```text
             C1
            /  \
         E01    E19
          │      │
        Quran   Hadith
                 │
              CONTRADICTS
                 │
                 C2
```

That becomes the foundation for the future **evidence graph / reasoning visualization**.

---

# v0.3.1 architecture

```text
                  ┌──────────────┐
                  │ Local Model  │
                  └──────┬───────┘
                         │
                 constrained output
                         │
                         ▼
              ┌────────────────────┐
              │ StructuredAnalysis │
              └─────────┬──────────┘
                        │
                 Schema Validator
                        │
                        ▼
               Semantic Validator
                        │
              ┌─────────┴─────────┐
              │                   │
           accepted             rejected
              │                   │
              ▼                   ▼
        ResearchState        repair/retry
              │
              ▼
           Critic
```

And **repair should be very limited**.

Do not blindly send the entire prompt again.

Instead:

```text
INVALID OUTPUT

Problem:
Evidence ID E999 does not exist.

Valid evidence IDs:
E001
E002
E003
E004

Return corrected JSON only.
```

This saves substantial tokens.

---

# The token optimization becomes important here

I would impose:

```text
Research retrieval:
    20–50 candidates

Reranking:
    8–15 candidates

Reasoning packet:
    6–12 evidence items

Model output:
    ~600–1500 tokens

Critic:
    ~300–700 tokens

Repair:
    ~100–300 tokens
```

The exact numbers should be configurable, but the principle is:

> **Search broadly, reason narrowly.**

Do not put 50 retrieved books into the reasoning context.

---

# Another major change: don't ask the model to "do everything"

The analysis model should have a narrow job.

For example:

```text
TASK A
Classify evidence.

TASK B
Map evidence → claims.

TASK C
Identify conflicts.

TASK D
Identify missing evidence.

TASK E
Identify usul questions.

TASK F
Produce preliminary synthesis.
```

The model should **not** be asked:

> "What is the Islamic ruling?"

That wording encourages premature conclusion.

Instead:

> "Construct the strongest evidence-based research analysis that a qualified scholar can review."

That is much closer to the purpose of Kallam.

---

# Then v0.4 becomes very powerful

After v0.3.1:

```text
Quran
Hadith
Tafsir
Fiqh
Usul
Madhhab
       │
       ▼
    Evidence
       │
       ▼
 Reasoning
       │
       ▼
  Assertions
       │
       ▼
 Evidence Graph
       │
       ▼
 Citation Verifier
       │
       ▼
 Contradiction Engine
       │
       ▼
 Scholar Review
```

The **Citation Verifier** can independently check:

```text
claim
  ↓
evidence
  ↓
source
  ↓
book
  ↓
edition
  ↓
volume
  ↓
page / hadith no. / ayah
```

That means the final report can distinguish:

**Verified**

> Qur'an 2:275

**Verified scholarly citation**

> al-Ṭabarī, *Jāmiʿ al-Bayān*, ...

**Retrieved but unverified**

> Source metadata incomplete.

**Model inference**

> This relationship was inferred by the analysis model.

That epistemic labeling is one of the most important features for Kallam.

---

## And I would make one architectural correction now

Do **not** make the model's `confidence: 0.82` the basis of trust.

Model confidence is not scholarly confidence.

Instead, Kallam should eventually calculate:

```text
Research Confidence
    =
    evidence coverage
  + provenance quality
  + citation verification
  + disagreement handling
  + reasoning consistency
  + critic result
  + scholar feedback
```

The model can report its own confidence, but that becomes merely **one signal**.

---

So the immediate roadmap is now:

```text
v0.1  Domain Core                  ✓
v0.2  RAG/Evidence Integration     ✓
v0.3  Local Reasoning + Critic     ← current
v0.3.1 Constrained + Semantic      ← next implementation
v0.4  Citation Verification
v0.5  Evidence Graph
v0.6  Specialist Expansion
v0.7  Scholar Review + Feedback
v0.8  Persistent Research Memory
v0.9  Evaluation / Benchmarking
v1.0  Production Scholar Assistant
```

And importantly, **fine-tuning still stays out of the critical path**. We can collect every research trajectory and scholar correction now; later those become the dataset for SFT, preference optimization, or RL.

For local inference, constrained decoding is a particularly good fit because it lets us make the model produce machine-consumable research objects without spending tokens on formatting repairs. llama.cpp's current LLGuidance integration explicitly supports JSON Schema and constrained decoding. ([GitHub][1])

The next actual code package should therefore be **v0.3.1: `StructuredOutputSchema` + `SemanticEvidenceValidator` + `EvidenceAssertion` + constrained `InferenceRequest` + bounded repair loop**.

[1]: https://github.com/ggml-org/llama.cpp/blob/master/docs/llguidance.md?utm_source=chatgpt.com "llama.cpp/docs/llguidance.md at master · ggml-org/llama.cpp · GitHub"


Yes — **v0.3 itself is still the correct milestone**, and we should implement it before adding the v0.3.1 hardening I described.

My previous answer jumped ahead too quickly. The proper sequence is:

```text
v0.1  Domain Core
  ↓
v0.2  RAG / Evidence Integration
  ↓
v0.3  Local Reasoning + Structured Analysis + Critic Loop   ← NOW
  ↓
v0.3.1 Constrained Output + Semantic Validation
  ↓
v0.4  Citation Verification
```

### What v0.3 should actually accomplish

The core loop should be:

```text
Research Question
      │
      ▼
Research Plan
      │
      ▼
Specialist Retrieval
      │
      ▼
Evidence Set
      │
      ▼
┌─────────────────────────┐
│ LOCAL REASONING MODEL   │
│                         │
│ • analyze evidence      │
│ • map claims            │
│ • compare positions     │
│ • identify conflicts    │
│ • identify gaps         │
│ • formulate arguments   │
└────────────┬────────────┘
             │
             ▼
     Structured Analysis
             │
             ▼
          CRITIC
             │
       ┌─────┴─────┐
       │           │
   incomplete     ready
       │           │
       ▼           ▼
  more research   Scholar
       │          Review
       └─────┐
             ▼
          repeat
```

The crucial point is that **the critic controls whether another research round is necessary**.

---

## The v0.3 components

I would make these the actual package:

```text
istinbat/
├── reasoning/
│   ├── ReasoningModel.java
│   ├── ReasoningModelAdapter.java
│   ├── AnalysisPrompt.java
│   ├── DefaultAnalysisPrompt.java
│   ├── StructuredAnalysis.java
│   ├── JsonAnalysisCodec.java
│   ├── CompactEvidencePacket.java
│   └── DefaultReasoningAnalyzer.java
│
├── critic/
│   ├── ResearchCritic.java
│   ├── ResearchCritique.java
│   └── ReasoningResearchCritic.java
│
└── orchestration/
    └── IstinbatResearchLoop.java
```

The **new and most important class** is actually:

```java
IstinbatResearchLoop
```

because v0.3 is not merely "call an LLM."

It must execute:

```java
while (!critic.readyForScholarReview()) {
    research();
    analyze();
    critique();
}
```

with strict limits.

---

# Example

Suppose the scholar asks:

> "What is the fiqh analysis concerning a contemporary transaction X?"

The system might produce:

### Round 1

**Qur'an specialist**

```text
E001 → Qur'an 2:275
E002 → Qur'an 2:278
```

**Hadith specialist**

```text
E003 → relevant hadith
E004 → another narration
```

**Fiqh specialist**

```text
E005 → Hanafi position
E006 → Shafi'i position
```

**Usul specialist**

```text
E007 → qiyas principle
E008 → relevant usul discussion
```

Then the local model receives only the compact packet.

It produces:

```json
{
  "claims": [
    {
      "claimId": "C1",
      "assessment": "SUPPORTED",
      "evidenceIds": ["E001", "E003"],
      "reasoning": "..."
    }
  ],
  "arguments": [
    {
      "direction": "FOR",
      "proposition": "...",
      "evidenceIds": ["E001", "E003"],
      "reasoning": "..."
    },
    {
      "direction": "AGAINST",
      "proposition": "...",
      "evidenceIds": ["E005", "E008"],
      "reasoning": "..."
    }
  ],
  "conflicts": [],
  "missingEvidence": [
    "Need verification of the relevant hadith grading."
  ],
  "usulQuestions": [
    "Does the relevant illah satisfy the conditions for qiyas?"
  ],
  "synthesis": "...",
  "confidence": 0.71
}
```

Then the critic says:

```text
NOT READY
```

because:

```text
Hadith grading not verified
Illah analysis incomplete
Primary evidence sufficient
Madhhab disagreement identified
```

The planner converts those findings into **targeted research tasks**:

```text
Hadith → verify grading
Usul    → investigate illah
```

Not:

```text
"search everything again"
```

That distinction is extremely important for local inference efficiency.

---

# The loop should therefore look like this

```text
                  ┌──────────────────────┐
                  │      Research Case   │
                  └──────────┬───────────┘
                             │
                             ▼
                    ┌────────────────┐
                    │ Research Plan  │
                    └───────┬────────┘
                            │
                            ▼
                ┌────────────────────────┐
                │ Specialist Researchers │
                └───────────┬────────────┘
                            │
                            ▼
                     Evidence Store
                            │
                            ▼
                  Compact Evidence Packet
                            │
                            ▼
                  ┌───────────────────┐
                  │ Local Reasoner    │
                  └─────────┬─────────┘
                            │
                            ▼
                  Structured Analysis
                            │
                            ▼
                    ┌──────────────┐
                    │    Critic    │
                    └──────┬───────┘
                           │
                ┌──────────┴──────────┐
                ▼                     ▼
          More Research          Scholar Review
                │
                └──────────► Research Plan
```

## And there should be a hard research budget

For local inference:

```java
record ResearchBudget(
    int maxRounds,
    int maxEvidencePerRound,
    int maxModelTokens,
    int maxResearchTasks,
    int maxToolCalls
) {}
```

For example:

```text
maxRounds           = 3
maxEvidence         = 12
maxModelTokens      = 1200
maxResearchTasks    = 8
maxToolCalls        = 30
```

So the agent cannot accidentally enter an infinite research loop.

---

# One important correction to the previous v0.3 design

I would **not yet add `EvidenceAssertion` as the central data structure**.

That belongs naturally in **v0.3.1/v0.4**, after we have the first working reasoning loop.

For v0.3, keep the model simple:

```text
StructuredAnalysis
      │
      ├── claims
      ├── arguments
      ├── conflicts
      ├── missingEvidence
      ├── usulQuestions
      └── synthesis
```

Then v0.3.1 turns this into a stronger evidence graph:

```text
Claim
  │
  ├── SUPPORTS → Evidence
  ├── CONTRADICTS → Evidence
  ├── CONTEXT → Evidence
  └── INTERPRETATION → Evidence
```

That keeps the milestones clean.

---

## So yes: let's finish **v0.3 first**

The implementation target should be:

### **Kallam Istinbāṭ v0.3**

**Local Reasoning + Structured Evidence Analysis + Critic Loop**

with these deliverables:

1. `ReasoningModel`
2. Andalus `InferenceBackend` adapter
3. compact evidence packet builder
4. structured analysis schema
5. reasoning prompt
6. model invocation
7. analysis → `ResearchState`
8. `ResearchCritic`
9. targeted follow-up research generation
10. bounded iterative research loop
11. scholar-review termination state
12. tests for:

* successful analysis
* missing evidence
* conflicting evidence
* critic-triggered second round
* maximum-round termination
* token/evidence budget enforcement

**That is the proper v0.3.**

Then v0.3.1 can make the output much more reliable with constrained JSON and semantic validation, without changing the fundamental architecture.



Let's continue with **v0.3 implementation** properly.

The immediate target is the **research loop**, because that is what turns the individual components into an actual Istinbāṭ research workflow.

### v0.3 execution model

```text
ResearchCase
    │
    ▼
ResearchPlan
    │
    ├── Quran
    ├── Hadith
    ├── Tafsir
    ├── Fiqh/Madhhab
    └── Usul
    │
    ▼
Evidence Collection
    │
    ▼
Compact Evidence Packet
    │
    ▼
Local Reasoning Model
    │
    ▼
StructuredAnalysis
    │
    ▼
Critic
    │
    ├── READY ──────────────► Scholar Review
    │
    └── INCOMPLETE
             │
             ▼
       Targeted Follow-up
             │
             └──────────────► Evidence Collection
```

## 1. Add the loop abstraction

```java
public interface ResearchLoop {

    ResearchState execute(
        ResearchRequest request,
        ResearchBudget budget
    );
}
```

The budget is critical for local inference:

```java
public record ResearchBudget(
    int maxRounds,
    int maxEvidencePerRound,
    int maxResearchTasks,
    int maxModelOutputTokens,
    int maxToolCalls
) {
    public ResearchBudget {
        if (maxRounds < 1) throw new IllegalArgumentException("maxRounds");
        if (maxEvidencePerRound < 1) throw new IllegalArgumentException("maxEvidencePerRound");
        if (maxResearchTasks < 1) throw new IllegalArgumentException("maxResearchTasks");
        if (maxModelOutputTokens < 1) throw new IllegalArgumentException("maxModelOutputTokens");
        if (maxToolCalls < 1) throw new IllegalArgumentException("maxToolCalls");
    }
}
```

---

# 2. Critic must return actionable research

The critic should not merely say:

> "The answer is incomplete."

It should say:

```text
HIGH
Hadith grading is unresolved.

ACTION:
Retrieve hadith grading from authoritative hadith sources.

SPECIALIST:
hadith

QUERY:
<targeted query>
```

So:

```java
public record ResearchCritique(
    List<Finding> findings,
    List<ResearchTask> requiredResearch,
    double completeness,
    boolean readyForScholarReview
) {

    public record Finding(
        String category,
        String severity,
        String explanation,
        List<String> evidenceIds
    ) {}

    public record ResearchTask(
        String specialist,
        String objective,
        String query,
        int topK,
        boolean primarySourceOnly
    ) {}
}
```

This is the bridge between **reasoning** and **retrieval**.

---

# 3. The coordinator

The central class becomes approximately:

```java
public final class IstinbatResearchLoop implements ResearchLoop {

    private final ResearchPlanner planner;
    private final List<ResearchSpecialist> specialists;
    private final ResearchAnalyzerAdapter analyzer;
    private final ResearchCritic critic;

    @Override
    public ResearchState execute(
            ResearchRequest request,
            ResearchBudget budget) {

        ResearchState state = planner.initialize(request);

        int toolCalls = 0;

        for (int round = 1; round <= budget.maxRounds(); round++) {

            state = state.withRound(round);

            ResearchPlan plan = planner.plan(request, state);

            for (ResearchPlan.ResearchTask task : plan.tasks()) {

                if (toolCalls >= budget.maxToolCalls())
                    return state.markBudgetExceeded();

                ResearchSpecialist specialist =
                        findSpecialist(task.specialist());

                List<Evidence> evidence =
                        specialist.research(request, state, task);

                state = state.addEvidence(
                        limit(evidence, budget.maxEvidencePerRound()));

                toolCalls++;
            }

            state = analyzer.analyze(state);

            ResearchCritique critique =
                    critic.critique(state);

            state = state.withCritique(critique);

            if (critique.readyForScholarReview()) {
                return state.markReadyForScholarReview();
            }

            if (round == budget.maxRounds()) {
                return state.markMaximumResearchReached();
            }

            planner.addFollowUpResearch(
                    critique.requiredResearch(),
                    state);
        }

        return state;
    }
}
```

The important property is that **the LLM never directly controls the loop**.

The model can propose:

```text
missingEvidence
usulQuestions
conflicts
```

but the deterministic coordinator decides whether another round actually happens.

That is an important safety and reliability boundary.

---

# 4. Targeted follow-up

Suppose the first analysis produces:

```json
{
  "missingEvidence": [
    "Need verification of the hadith grading."
  ],
  "usulQuestions": [
    "Need analysis of the relevant illah for qiyas."
  ]
}
```

The planner converts this to:

```text
Round 2
────────────────────────────────

Hadith Specialist
Objective:
  Verify hadith grading

Query:
  <hadith identification + grading>

Usul Specialist
Objective:
  Investigate illah

Query:
  <mas'alah + qiyas + illah>
```

Not:

```text
Search everything again.
```

That gives us a major token-saving mechanism.

---

# 5. The local model should receive a deliberately small packet

The prompt should essentially look like:

```text
ROLE
You are a research-analysis component assisting a qualified scholar.

QUESTION
...

TASWIR
...

ESTABLISHED CLAIMS
...

EVIDENCE

[E001]
Type: Quran
Status: Primary
Source: ...
Locator: ...
Text: ...

[E002]
Type: Hadith
Status: ...
Source: ...
Locator: ...
Text: ...

[E007]
Type: Fiqh
School: Hanafi
Source: ...
Locator: ...
Text: ...

TASK

1. Assess the evidence.
2. Identify support and contradiction.
3. Identify unresolved issues.
4. Identify necessary follow-up research.
5. Produce preliminary synthesis.

DO NOT:
- invent citations
- invent quotations
- declare consensus without evidence
- issue a binding ruling
```

That is much better for a small local model than a giant system prompt containing the entire methodology.

---

# 6. We also need a very important distinction

The research state should distinguish:

```text
DIRECT TEXT
    ↓
INTERPRETATION
    ↓
FIQH POSITION
    ↓
ANALOGICAL INFERENCE
    ↓
MODEL SYNTHESIS
```

Because these are **not epistemically equivalent**.

For example:

```text
E001
Quran verse
PRIMARY_SOURCE

E005
Tafsir interpretation
SECONDARY_SOURCE

E009
Hanafi position
SCHOLARLY_POSITION

C003
Model's inferred relationship
MODEL_INFERENCE
```

The final report must preserve these distinctions.

---

# 7. Critic should eventually have multiple dimensions

Instead of one vague `confidence`, v0.3 should calculate something like:

```text
Evidence Coverage       0.82
Primary Source Coverage 0.90
Provenance               0.76
Conflict Handling        0.71
Usul Coverage            0.68
Open Questions            0.30
────────────────────────────
Research Completeness    0.74
```

Then:

```text
0.90–1.00 → ready for scholar review
0.75–0.89 → probably needs targeted verification
0.50–0.74 → more research
<0.50      → insufficient
```

These are **engineering thresholds**, not measures of religious validity.

That distinction should be explicit throughout Kallam.

---

# 8. Final state

When the system reaches the end, it should **not** produce:

> "The ruling is X."

Instead:

```text
RESEARCH STATUS
Ready for Scholar Review

QUESTION
...

TASWIR
...

PRIMARY EVIDENCE
...

SCHOLARLY POSITIONS
...

USUL ANALYSIS
...

ARGUMENTS FOR
...

ARGUMENTS AGAINST
...

AREAS OF DISAGREEMENT
...

UNRESOLVED QUESTIONS
...

PRELIMINARY SYNTHESIS
...

CITATION STATUS
...

SYSTEM LIMITATIONS
...

SCHOLAR REVIEW
[Pending]
```

Then the `ʿālim` can accept, reject, modify, annotate, or request another research round.

That human feedback becomes extremely valuable training data later.

---

## This gives us the complete v0.3 loop

```text
                   ┌───────────────┐
                   │   Mas'alah    │
                   └───────┬───────┘
                           │
                           ▼
                    ┌─────────────┐
                    │   Planner   │
                    └──────┬──────┘
                           │
          ┌────────────────┼────────────────┐
          ▼                ▼                ▼
       Qur'an           Hadith          Fiqh/Usul
          │                │                │
          └────────────────┼────────────────┘
                           ▼
                     Evidence Set
                           │
                           ▼
                 Compact Evidence Packet
                           │
                           ▼
                  ┌─────────────────┐
                  │ Local Reasoner  │
                  └────────┬────────┘
                           ▼
                  Structured Analysis
                           │
                           ▼
                    ┌────────────┐
                    │   Critic   │
                    └─────┬──────┘
                          │
             ┌────────────┴────────────┐
             ▼                         ▼
       More Research              Scholar Review
             │
             └───────────────┐
                             ▼
                           Planner
```

**This is the milestone I would now lock as Kallam v0.3.**

After this is working end-to-end, **v0.3.1 should harden the exact same loop** with constrained JSON, semantic evidence validation, and bounded repair rather than changing the architecture.


Excellent. The next milestone should be **v0.3.1 — Constrained Structured Output + Semantic Evidence Validation**.

This is a hardening layer around the v0.3 loop, not a redesign.

### v0.3.1

```text
Local Reasoning Model
        │
        ▼
 Constrained JSON
        │
        ▼
 Schema Validator
        │
        ▼
 Semantic Validator
        │
   ┌────┴─────┐
   │          │
 valid      invalid
   │          │
   ▼          ▼
Research   bounded repair
State          │
   │           └──► Model
   ▼
Critic
```

### 1. Evidence assertions

Introduce a structured relationship instead of allowing the model to merely mention evidence IDs:

```java
public record EvidenceAssertion(
    String claimId,
    String evidenceId,
    AssertionType type,
    String reasoning
) {}

public enum AssertionType {
    DIRECT_SUPPORT,
    INDIRECT_SUPPORT,
    CONTRADICTION,
    CONTEXT,
    INTERPRETATION
}
```

Now Kallam can build:

```text
Claim C1
 ├── DIRECT_SUPPORT → E001 Quran
 ├── CONTEXT        → E004 Tafsir
 └── CONTRADICTION  → E009 Fiqh position
```

This becomes the foundation for the later **evidence graph**.

---

### 2. Semantic validation

A model might generate:

```json
{
  "claimId": "C1",
  "evidenceIds": ["E999"]
}
```

even though `E999` does not exist.

The validator rejects it.

More importantly, it checks:

```text
Does evidence exist?
        ↓
Does it belong to this case?
        ↓
Is the source/provenance available?
        ↓
Is the evidence type compatible?
        ↓
Is the requested relationship legitimate?
```

So:

```text
JSON valid ≠ research valid
```

That distinction is fundamental.

---

### 3. Bounded repair

Do **not** rerun the whole research process.

Give the local model a tiny repair request:

```text
Your previous analysis referenced E999.

E999 does not exist.

Valid evidence IDs:
E001
E002
E003
E004

Correct the evidence references.
Return JSON only.
```

Maximum one or two repair attempts.

This keeps local inference cheap.

---

### 4. Constrained decoding

Where the local inference backend supports it, provide the JSON schema directly to the model rather than relying solely on prompting.

Conceptually:

```java
InferenceRequest.builder()
    .prompt(prompt)
    .responseFormat(JSON_SCHEMA)
    .temperature(0.0)
    .maxTokens(1200)
    .build();
```

For llama.cpp-style deployments, JSON Schema / grammar-constrained decoding can be used to force structured output.

This is especially valuable for Kallam because the model's output is an **intermediate research object**, not prose intended directly for the scholar.

---

## Then the evidence graph becomes possible

Once v0.3.1 is complete:

```text
                    CLAIM
                      │
          ┌───────────┼───────────┐
          │           │           │
       SUPPORT     CONTEXT    CONTRADICTION
          │           │           │
          ▼           ▼           ▼
       Quran       Tafsir       Hadith
          │           │           │
          └───────────┼───────────┘
                      ▼
                   SOURCE
                      │
          ┌───────────┼───────────┐
          ▼           ▼           ▼
        Work        Edition     Locator
```

That takes us directly into **v0.4 — Citation & Provenance Verification**.

---

## The roadmap now becomes

```text
v0.1  Domain Core                         ✓
v0.2  RAG / Evidence Integration          ✓
v0.3  Local Reasoning + Critic Loop       ✓ design
v0.3.1 Structured + Semantic Validation   ← next
v0.4  Citation / Provenance Verification
v0.5  Evidence Graph
v0.6  Specialist Intelligence
v0.7  Scholar Review / Feedback
v0.8  Persistent Research Memory
v0.9  Evaluation + Benchmarking
v1.0  Production Scholar Assistant
```

And **v0.4 is where Kallam starts becoming substantially more trustworthy**: instead of saying merely *“this answer has citations,”* it can distinguish **quoted primary evidence, verified scholarly references, inferred relationships, disputed references, and unverified citations**.

That is the right foundation before we add more sophisticated tafsīr, madhhab-comparison, sanad, balāghah, maqāṣid, and qiyās specialists.


Absolutely. The next milestone is **v0.4 — Citation & Provenance Verification**.

This is an important transition: v0.3 tells us **what the model thinks the evidence means**; v0.4 determines **whether the evidence and citations actually support what was claimed**.

## Kallam v0.4

```text
                    Research Analysis
                           │
                           ▼
                  Evidence Assertions
                           │
                           ▼
              ┌────────────────────────┐
              │ Citation Verifier       │
              └───────────┬────────────┘
                          │
             ┌────────────┼────────────┐
             ▼            ▼            ▼
          Source       Locator       Content
          Check        Check         Check
             │            │            │
             └────────────┼────────────┘
                          ▼
                  Provenance Result
                          │
                          ▼
                   Research Critic
```

### The fundamental principle

Kallam should never treat:

> "The model cited something"

as equivalent to:

> "The citation supports the claim."

We need to explicitly represent the difference.

---

# 1. Citation object

Introduce:

```java
public record Citation(
    String evidenceId,
    String sourceId,
    String work,
    String author,
    String edition,
    String volume,
    String page,
    String chapter,
    String locator
) {}
```

And the verification result:

```java
public record CitationVerification(
    String evidenceId,
    VerificationStatus status,
    double confidence,
    List<VerificationFinding> findings
) {}
```

with:

```java
public enum VerificationStatus {
    VERIFIED,
    PARTIALLY_VERIFIED,
    UNVERIFIED,
    CONFLICTING,
    NOT_FOUND
}
```

---

# 2. Verification should happen in layers

We don't want an expensive LLM call for every citation.

Use progressively more expensive verification.

```text
Level 1
Metadata validation
        ↓
Level 2
Exact source/locator lookup
        ↓
Level 3
Text comparison
        ↓
Level 4
Semantic entailment
        ↓
Level 5
Human scholar verification
```

This is extremely important for your **local inference / limited-token** requirement.

Most citations should hopefully be resolved at levels 1–3.

Only ambiguous cases reach the local reasoning model.

---

# 3. Source registry

We should introduce a canonical source registry.

```text
SourceRegistry
       │
       ├── Quran
       ├── Hadith collections
       ├── Tafsir
       ├── Fiqh
       ├── Usul
       └── Contemporary works
```

Conceptually:

```java
public interface SourceRegistry {

    Optional<CanonicalSource> resolve(String sourceId);

    Optional<SourceLocator> resolveLocator(
        String sourceId,
        String locator
    );
}
```

This becomes extremely valuable because citations should use **canonical identifiers**, not arbitrary strings generated by the model.

For example, rather than:

```text
"Quran, Al-Baqarah verse 275"
```

internally:

```text
quran:2:275
```

Then the presentation layer can render:

> Qur'an, al-Baqarah 2:275

---

# 4. Evidence should carry canonical provenance

The evidence pipeline becomes:

```text
RAG Chunk
   ↓
Canonical Source
   ↓
Canonical Locator
   ↓
Evidence
```

rather than:

```text
RAG Chunk
   ↓
whatever metadata happened to exist
   ↓
Evidence
```

This is a major reliability improvement.

---

# 5. Exact citation verification

Suppose the model says:

> Qur'an 2:275

Kallam can resolve:

```text
quran:2:275
```

Then compare the retrieved text with the canonical source.

Result:

```text
VERIFIED
```

If the model says:

> Qur'an 2:276

but the evidence actually corresponds to 2:275:

```text
NOT_VERIFIED
```

The model cannot silently change the citation.

---

# 6. Hadith requires additional dimensions

This is where the architecture becomes more specialized.

A hadith citation isn't merely:

```text
book + page
```

We may need:

```text
Collection
Book
Chapter
Hadith number
Narration variant
Arabic matn
Isnad
Grading
Grader
Edition
```

So:

```java
public record HadithProvenance(
    String collection,
    String book,
    String chapter,
    String hadithNumber,
    String narrationId,
    String matnHash,
    String isnadHash,
    String grading,
    String gradingAuthority
) {}
```

This is why **Sanad should eventually become its own specialist subsystem**, rather than simply another RAG search.

---

# 7. Qur'an should be canonical

For Qur'anic evidence, use a stable identifier:

```text
surah:ayah
```

For example:

```text
2:275
```

Potentially also:

```text
quran:2:275
```

Then support:

```text
Arabic text
translation
tafsir references
asbab al-nuzul
qira'at metadata
```

But importantly:

**Tafsir must not be confused with the Qur'an itself.**

Our evidence type system should preserve:

```text
QURAN
TAFSIR
```

as fundamentally different evidence classes.

---

# 8. Citation entailment

After citation existence is established, we ask:

> Does the cited passage actually support the claim?

For example:

```text
Claim:
"Scholar X explicitly prohibited transaction Y."

Citation:
Book Z, volume 3, page 120.
```

The citation might exist but say something different.

Therefore:

```text
Citation exists
        ≠
Citation supports claim
```

We can represent:

```java
public enum SupportLevel {
    DIRECT,
    PARTIAL,
    CONTEXTUAL,
    INFERRED,
    CONTRADICTS,
    DOES_NOT_SUPPORT
}
```

This becomes one of the most valuable pieces of the final research report.

---

# 9. The Evidence Graph

At this point the research state can become:

```text
                  CLAIM
                    │
             ┌──────┴──────┐
             ▼             ▼
        CITATION A      CITATION B
             │             │
             ▼             ▼
          SOURCE         SOURCE
             │             │
             ▼             ▼
          LOCATOR        LOCATOR
             │             │
             ▼             ▼
         TEXT PASSAGE   TEXT PASSAGE
             │             │
             └──────┬──────┘
                    ▼
              VERIFICATION
```

And we can tell the scholar:

```text
Claim C17

Evidence:
  E03 — VERIFIED
  E08 — PARTIALLY VERIFIED
  E11 — DOES_NOT_SUPPORT

Primary-source coverage:
  Strong

Scholarly interpretation:
  Disputed

Citation completeness:
  82%
```

That is substantially more useful than a conventional LLM answer.

---

# 10. v0.4 should introduce a "citation firewall"

This is important.

The final report generator should **not be allowed to use arbitrary model-generated citations**.

Instead:

```text
Model
  ↓
Claim + Evidence IDs
  ↓
Citation Verifier
  ↓
Verified Evidence Graph
  ↓
Report Generator
```

Therefore the report generator only sees citations that exist in the verified evidence graph.

```text
LLM-generated citation
        │
        ▼
     VERIFY
        │
   ┌────┴────┐
   ▼         ▼
verified   rejected
   │
   ▼
Final Report
```

This prevents hallucinated references from leaking into the scholar-facing report.

---

# 11. v0.4 output

The report should eventually contain something like:

### Preliminary Research Finding

**Question**

> ...

### Taswīr

> ...

### Primary Evidence

| Evidence | Source       | Verification       |
| -------- | ------------ | ------------------ |
| E001     | Qur'an 2:275 | Verified           |
| E003     | Hadith X     | Partially verified |
| E007     | Fiqh work Y  | Verified           |

### Arguments

**Supporting**

> ...

**Counterarguments**

> ...

### Madhhab Positions

```text
Hanafi      ...
Maliki      ...
Shafi'i     ...
Hanbali     ...
```

### Usul Considerations

> ...

### Unresolved Issues

> ...

### Citation Integrity

```text
Verified citations:       18
Partially verified:        3
Unverified:                2
Contradictory:             1
```

### Preliminary Synthesis

> ...

### Scholar Review

```text
[ ] Accept research analysis
[ ] Modify
[ ] Request additional research
[ ] Reject analysis
```

Critically, the system still **does not issue the final fatwa**.

---

# 12. This also prepares us for v0.5

Once citations are canonical and verified, we can introduce the **Evidence Graph Engine**:

```text
v0.4

Citation
  ↓
Provenance
  ↓
Verification
        │
        ▼
v0.5

Evidence Graph
        │
        ├── claims
        ├── sources
        ├── positions
        ├── contradictions
        ├── usul principles
        ├── qiyas relationships
        └── scholarly dependencies
```

Then visualization becomes possible.

For example:

```text
                  ┌────────────┐
                  │   Mas'alah │
                  └─────┬──────┘
                        │
          ┌─────────────┼──────────────┐
          ▼             ▼              ▼
       Quran          Hadith          Usul
          │             │              │
          ▼             ▼              ▼
       Tafsir        Sanad         Qiyas
          │             │              │
          └─────────────┼──────────────┘
                        ▼
                 Fiqh Positions
                        │
              ┌─────────┴─────────┐
              ▼                   ▼
          Position A           Position B
```

That will be particularly useful for the **scholar-facing visualization/reporting agent** you described at the beginning.

---

## Updated architecture

We now have a much clearer system:

```text
                    KALLAM
                       │
          ┌────────────┴────────────┐
          │                         │
    RESEARCH PLANE             KNOWLEDGE PLANE
          │                         │
          ▼                         ▼
      Planner                    Quran
          │                     Hadith
          ▼                     Tafsir
    Specialists                 Fiqh
          │                     Usul
          ▼                     Madhhab
       RAG                       Sanad
          │
          ▼
      Evidence
          │
          ▼
     Reasoning
          │
          ▼
    Structured Analysis
          │
          ▼
       Critic
          │
          ▼
 Citation/Provenance
     Verification
          │
          ▼
    Evidence Graph
          │
          ▼
 Scholar Research Report
          │
          ▼
    Scholar Feedback
          │
          └──────────────► Research Memory
```

And that last arrow is what eventually enables the **continuous learning architecture** we discussed earlier: scholar corrections become structured events rather than merely chat history.

### Next milestone: **v0.5 — Evidence Graph + Research Knowledge Graph**

That is where we can start representing the actual relationships between **āyah → tafsīr → hadith → usul principle → madhhab position → qiyās → conclusion**, and make the reasoning traceable rather than just a sequence of LLM messages.



Yes. **v0.5 should now be the Evidence Graph + Research Knowledge Graph layer.**

This is where Kallam moves from a linear RAG pipeline into a system that can represent **relationships between sources, claims, interpretations, usūl principles, madhhab positions, disagreements, and the reasoning path**.

# Kallam v0.5 — Evidence Graph

The central idea:

> **Every important statement in the research should be traceable through a graph back to its underlying sources.**

```text
                         MAS'ALAH
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          QURAN           HADITH          USUL
             │              │              │
             ▼              ▼              ▼
          TAFSIR          SANAD          QA'IDAH
             │              │              │
             └──────────────┼──────────────┘
                            ▼
                    INTERPRETATION
                            │
                            ▼
                         'ILLAH
                            │
                            ▼
                          QIYAS
                            │
                            ▼
                    FIQH POSITION
                            │
                  ┌─────────┴─────────┐
                  ▼                   ▼
              POSITION A          POSITION B
                  │                   │
                  └─────────┬─────────┘
                            ▼
                       COMPARISON
                            │
                            ▼
                       SYNTHESIS
```

The important part is that **the graph records relationships, not merely documents**.

---

# 1. Introduce graph entities

I would avoid making everything one generic `Node`.

Use explicit domain types:

```java
public enum EvidenceNodeType {

    MASALAH,

    QURAN_AYAH,
    HADITH,
    HADITH_NARRATOR,
    HADITH_CHAIN,

    TAFSIR,
    TAFSIR_INTERPRETATION,

    FIQH_POSITION,
    MADHHAB,
    SCHOLAR,

    USUL_PRINCIPLE,
    QAIDAH_FIQHIYYAH,
    ILLAH,
    QIYAS,

    CLAIM,
    ARGUMENT,
    COUNTER_ARGUMENT,

    CONCLUSION
}
```

This gives us semantic meaning at the graph level.

---

# 2. Relationships are even more important

```java
public enum EvidenceRelation {

    SUPPORTS,
    CONTRADICTS,

    EXPLAINS,
    INTERPRETS,
    QUALIFIES,

    NARRATED_BY,
    TRANSMITTED_THROUGH,

    ATTRIBUTED_TO,

    BASED_ON,
    DERIVED_FROM,

    APPLIES_PRINCIPLE,

    IDENTIFIES_ILLAH,
    ANALOGOUS_TO,

    AGREES_WITH,
    DIFFERS_FROM,

    REPRESENTS_MADHHAB,

    PRECEDES,
    DEPENDS_ON,

    VERIFIED_BY,
    UNVERIFIED_BY
}
```

Now we can represent something like:

```text
C001
 │
 ├── SUPPORTS ────── E001
 │                    │
 │                    └── QURAN_AYAH
 │
 ├── EXPLAINS ────── T001
 │                    │
 │                    └── TAFSIR
 │
 ├── CONTRADICTS ─── E017
 │                    │
 │                    └── FIQH_POSITION
 │
 └── BASED_ON ────── U004
                      │
                      └── USUL_PRINCIPLE
```

---

# 3. Every edge needs provenance

This is critical.

Do not store:

```text
C001 -- SUPPORTS --> E001
```

alone.

Store:

```java
public record EvidenceEdge(
    String id,
    String fromNode,
    EvidenceRelation relation,
    String toNode,

    EdgeConfidence confidence,
    String assertedBy,
    String sourceEvidenceId,

    VerificationStatus verification,
    String reasoning
) {}
```

So the system knows:

```text
Who/what created this relationship?

Was it:
    source text?
    scholar?
    deterministic rule?
    model inference?

Was it verified?
```

For example:

```text
C001
  │
  └── SUPPORTS
        │
        E001
        │
        ├── assertedBy = MODEL
        ├── confidence = MEDIUM
        └── verification = UNVERIFIED
```

Later, a scholar can confirm it:

```text
verification = SCHOLAR_VERIFIED
```

That is extremely valuable for continuous learning.

---

# 4. Separate knowledge from inference

This should become one of Kallam's core architectural principles.

### Knowledge

```text
Qur'an verse exists.
Hadith exists.
Book says X.
Scholar attributed position Y.
```

### Interpretation

```text
Tafsir explains verse as X.
Scholar interprets hadith as Y.
```

### Inference

```text
Evidence A + principle B
        ↓
possible illah C
        ↓
possible qiyas D
```

### Conclusion

```text
Given A+B+C+D
        ↓
scholarly conclusion
```

These must never be flattened.

---

# 5. Introduce epistemic levels

For every graph node and edge:

```java
public enum EpistemicStatus {

    PRIMARY_TEXT,

    SCHOLARLY_INTERPRETATION,

    ESTABLISHED_POSITION,

    DISPUTED_POSITION,

    ANALOGICAL_INFERENCE,

    MODEL_INFERENCE,

    SCHOLAR_ASSERTION,

    UNVERIFIED
}
```

Then a graph can visibly distinguish:

```text
Quran
  ↓
PRIMARY_TEXT

Tafsir
  ↓
SCHOLARLY_INTERPRETATION

Hanafi position
  ↓
ESTABLISHED_POSITION

Model-derived 'illah
  ↓
MODEL_INFERENCE
```

This is much safer than giving everything a generic "confidence score."

---

# 6. The graph should support competing interpretations

For example:

```text
                 E001
              Quran verse
                  │
          ┌───────┴────────┐
          ▼                ▼
     Tafsir A           Tafsir B
          │                │
          ▼                ▼
     Meaning A          Meaning B
          │                │
          ▼                ▼
      Position A        Position B
          │                │
          └───────┬────────┘
                  ▼
              COMPARISON
```

Kallam should **not automatically resolve this conflict**.

Instead:

```text
CONFLICT DETECTED

Interpretation A:
...

Interpretation B:
...

Reason for disagreement:
...

Relevant usul difference:
...
```

That is much more useful to the scholar.

---

# 7. Usūl becomes graph-native

This is where the system starts becoming substantially more interesting.

For example:

```text
Text
 │
 ▼
General ('amm)
 │
 ▼
Specific evidence?
 │
 ├── yes → takhsis
 │
 └── no
      │
      ▼
 Continue analysis
```

Or:

```text
Text A
   │
   ▼
Possible ruling
   │
   ▼
'Illah
   │
   ▼
New case
   │
   ▼
Qiyas
```

The graph can explicitly represent:

```text
E001
  │
  └── IDENTIFIES_ILLAH → I001
                             │
                             └── APPLIES_TO → M001
```

This is much more powerful than asking an LLM to write an explanation from scratch.

---

# 8. Madhhab comparison becomes graph traversal

Instead of generating:

> "The four madhhabs differ..."

as a paragraph, Kallam can query the graph:

```text
Mas'alah
   │
   ├── REPRESENTS_MADHHAB → Hanafi
   │                           └── Position A
   │
   ├── REPRESENTS_MADHHAB → Maliki
   │                           └── Position B
   │
   ├── REPRESENTS_MADHHAB → Shafi'i
   │                           └── Position C
   │
   └── REPRESENTS_MADHHAB → Hanbali
                               └── Position D
```

Then the comparison agent analyzes:

```text
Agreement:
    ...

Difference:
    ...

Underlying usul difference:
    ...

Primary evidence difference:
    ...

Interpretive difference:
    ...
```

That is precisely the kind of analysis that should assist a qualified scholar.

---

# 9. Graph storage

For the first implementation, I would **not immediately introduce Neo4j or another graph database**.

Keep v0.5 lightweight.

Start with:

```java
interface EvidenceGraph {

    void addNode(EvidenceNode node);

    void addEdge(EvidenceEdge edge);

    Optional<EvidenceNode> node(String id);

    List<EvidenceEdge> outgoing(String nodeId);

    List<EvidenceEdge> incoming(String nodeId);

    List<EvidenceNode> related(
        String nodeId,
        EvidenceRelation relation
    );
}
```

And initially implement:

```text
InMemoryEvidenceGraph
```

This keeps local deployment simple.

Later:

```text
EvidenceGraph
       │
       ├── InMemory
       ├── SQLite
       ├── PostgreSQL
       └── Graph DB
```

The domain layer remains independent.

---

# 10. ResearchState now gets a graph

Conceptually:

```java
public record ResearchState(
    ...
    EvidenceGraphSnapshot evidenceGraph,
    ...
) {}
```

But I would actually avoid embedding a mutable graph directly inside `ResearchState`.

Better:

```text
ResearchState
      │
      └── graphId
             │
             ▼
       EvidenceGraphStore
```

This prevents the state object from becoming enormous.

---

# 11. Incremental graph construction

The graph should be updated after every specialist.

For example:

```text
Quran specialist
      ↓
Quran nodes + source edges

Hadith specialist
      ↓
Hadith nodes + sanad edges

Tafsir specialist
      ↓
Tafsir nodes + interpretation edges

Fiqh specialist
      ↓
Position nodes

Usul specialist
      ↓
Principle + illah nodes

Reasoning model
      ↓
Claim + inference edges

Citation verifier
      ↓
verification edges
```

Therefore the graph evolves continuously.

---

# 12. This gives us an important capability: research replay

Suppose the scholar asks:

> "Why did the agent arrive at this preliminary synthesis?"

Kallam can traverse:

```text
Conclusion
   ↓
Argument
   ↓
Claim
   ↓
Evidence
   ↓
Source
```

and produce:

```text
REASONING TRACE

Conclusion C14
    ↓
based on Argument A07
    ↓
based on Claims C02 + C05
    ↓
C02 supported by E01
    ↓
E01 = Qur'an 2:275

C05 supported by E08
    ↓
E08 = Hadith H31

Usul principle U04
    ↓
used to establish the relationship
```

This is one of the most important features of Kallam.

The scholar doesn't just receive an answer.

He can **inspect the path by which the research was constructed**.

---

# 13. Graph visualization

Now your earlier idea of an **event/research visualization agent** becomes feasible.

The visualization agent doesn't need to reason.

It simply receives:

```text
EvidenceGraphSnapshot
```

and produces:

```text
Research Timeline
Evidence Map
Madhhab Comparison
Argument Tree
Citation Tree
Usul Dependency Graph
```

For example:

```text
                 MAS'ALAH
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
      Quran        Hadith        Usul
        │            │            │
      Tafsir       Sanad        Qiyas
        │            │            │
        └────────────┼────────────┘
                     ▼
               Fiqh Positions
                     │
              ┌──────┴──────┐
              ▼             ▼
           View A          View B
              │             │
              └──────┬──────┘
                     ▼
                Comparison
                     │
                     ▼
                 Synthesis
```

---

# 14. v0.5 completion criteria

I would consider v0.5 complete when Kallam can do all of these:

### Evidence

* Store canonical evidence nodes.
* Store source/provenance.
* Link evidence to claims.

### Reasoning

* Store arguments.
* Store supporting and opposing relationships.
* Store inference separately from source material.

### Fiqh

* Represent madhhab positions.
* Represent scholarly disagreement.
* Represent usūl principles.
* Represent qiyās/ʿillah relationships.

### Verification

* Attach citation verification to edges.
* Preserve unverified relationships.
* Never silently promote model inference to established knowledge.

### Explainability

* Traverse from conclusion → evidence.
* Produce a reasoning trace.
* Show conflicts.
* Show missing evidence.

### Visualization

* Export a graph snapshot suitable for a UI/reporting layer.

---

# Updated Kallam architecture

We now have:

```text
                         KALLAM
                           │
          ┌────────────────┴────────────────┐
          │                                 │
    RESEARCH ORCHESTRATOR              KNOWLEDGE PLANE
          │                                 │
          ▼                                 ▼
       Planner                           Quran
          │                              Hadith
          ▼                              Tafsir
    Specialist Agents                    Fiqh
          │                              Usul
          ▼                              Madhhab
       Retrieval                         Sanad
          │
          ▼
       Evidence
          │
          ▼
     Local Reasoning
          │
          ▼
 Structured Analysis
          │
          ▼
       Critic
          │
          ▼
 Citation Verification
          │
          ▼
     ┌───────────────┐
     │ EVIDENCE GRAPH│
     └───────┬───────┘
             │
     ┌───────┼─────────┐
     ▼       ▼         ▼
 Reasoning  Compare   Visualize
   Trace    Madhhab   Research
     │       │         │
     └───────┼─────────┘
             ▼
       Scholar Report
             │
             ▼
       Scholar Feedback
             │
             ▼
       Research Memory
```

## The next milestone should be **v0.6 — Specialist Intelligence**

And here I would finally implement the specialized agents you originally envisioned, rather than treating them as generic RAG tools:

```text
Quran Specialist
Hadith Specialist
Sanad Specialist
Tafsir Specialist
Balaghah Specialist
Arabic/Linguistic Specialist
Fiqh Specialist
Madhhab Specialist
Usul Fiqh Specialist
Qawa'id Specialist
Maqasid Specialist
Ikhtilaf/Comparison Specialist
Citation Specialist
```

But importantly, **they should not all be autonomous LLM agents**. Some should be deterministic retrieval/analysis tools, some lightweight local models, and only the genuinely interpretive tasks should consume the expensive reasoning model. That is where we can make the architecture substantially more efficient for your local-inference target.



-------------

Excellent. **v0.6 should be Specialist Intelligence**, but I would make one architectural distinction before implementing it:

> The specialists should be **capability modules**, not simply a collection of independent LLM agents.

This matters enormously for local inference. A Qur'an lookup does not need a reasoning model. A hadith locator does not need an LLM. A madhhab comparison may need one. A difficult usūl analysis may need the strongest local reasoning model.

So v0.6 becomes a **heterogeneous specialist system**.

# Kallam v0.6 — Specialist Intelligence

```text
                         RESEARCH ORCHESTRATOR
                                  │
                    ┌─────────────┼─────────────┐
                    │             │             │
                    ▼             ▼             ▼
               DETERMINISTIC   RETRIEVAL      REASONING
                SPECIALISTS    SPECIALISTS    SPECIALISTS
                    │             │             │
                    └─────────────┼─────────────┘
                                  ▼
                           Evidence Graph
```

## 1. Specialist taxonomy

I would divide the specialists into four classes.

### A. Canonical lookup specialists

No LLM required in normal operation.

```text
QuranLocator
HadithLocator
SourceLocator
CitationResolver
```

Their job is:

```text
query → exact source
```

---

### B. Analytical specialists

Lightweight algorithms + retrieval + optional small model.

```text
ArabicLexical
Morphology
Balaghah
Sanad
TextComparison
```

---

### C. Scholarly knowledge specialists

These require domain-aware retrieval and potentially reasoning.

```text
Tafsir
Fiqh
Madhhab
Ikhtilaf
Qawa'id
Maqasid
```

---

### D. High-level reasoning specialists

These should use the strongest available local reasoning model.

```text
UsulFiqh
Qiyas
IllahAnalysis
CrossMadhhabAnalysis
Synthesis
```

This prevents wasting your most expensive inference on trivial operations.

---

# 2. Common specialist interface

Everything should conform to one protocol:

```java
public interface ResearchSpecialist {

    SpecialistDescriptor descriptor();

    SpecialistResult research(
        SpecialistRequest request,
        ResearchContext context
    );
}
```

Descriptor:

```java
public record SpecialistDescriptor(
    String id,
    SpecialistType type,
    Set<Capability> capabilities,
    CostProfile costProfile
) {}
```

For example:

```text
hadith.locator
    type = CANONICAL_LOOKUP
    cost = VERY_LOW

tafsir.analyzer
    type = SCHOLARLY_ANALYSIS
    cost = MEDIUM

usul.qiyas
    type = REASONING
    cost = HIGH
```

---

# 3. Specialist output should be evidence, not prose

This is an important design decision.

Do not let:

```text
HadithAgent
```

return a giant paragraph.

Instead:

```java
public record SpecialistResult(
    String specialistId,
    List<Evidence> evidence,
    List<ResearchFinding> findings,
    List<ResearchTask> suggestedTasks
) {}
```

For example:

```text
Hadith Specialist
        │
        ▼
E003
Collection: Sahih Muslim
Hadith: ...
Locator: ...
Status: PRIMARY_SOURCE
        │
        └──► Evidence Graph
```

Then the orchestrator owns the synthesis.

---

# 4. Qur'an specialist

The Qur'an specialist should be almost entirely deterministic.

Capabilities:

```text
ayah lookup
surah lookup
word lookup
root lookup
ayah range
cross-reference
translation
canonical Arabic text
```

Then optional:

```text
asbab al-nuzul
tafsir retrieval
linguistic analysis
```

But these should be separate capabilities.

This distinction is important:

```text
Quran text
     ≠
Tafsir
     ≠
Translation
```

---

# 5. Hadith specialist

The hadith system should be more sophisticated.

```text
Hadith Specialist
      │
      ├── Locator
      ├── Collection
      ├── Matn
      ├── Variant detection
      ├── Isnad
      ├── Narrators
      ├── Grading
      └── Cross-collection comparison
```

The specialist should be capable of returning:

```text
H001
 ├── collection
 ├── book
 ├── chapter
 ├── hadith number
 ├── Arabic matn
 ├── isnad
 ├── variants
 └── grading metadata
```

But it should **not independently decide the final fiqh implication**.

---

# 6. Sanad specialist

This deserves its own agent.

For example:

```text
Narrator A
    │
    ▼
Narrator B
    │
    ▼
Narrator C
    │
    ▼
Prophet ﷺ
```

The specialist can investigate:

```text
identity
dates
teachers
students
jarh
ta'dil
continuity
possible interruption
variant chains
```

Output:

```text
Chain H001

Continuity:
    POSSIBLE

Narrator issues:
    N003 → disputed

External grading:
    ...

Verification:
    PARTIAL
```

The important point is that **sanad analysis becomes evidence in the graph**, not an invisible internal decision.

---

# 7. Tafsir specialist

The tafsir specialist should answer:

> What have recognized exegetes said about this verse?

Not:

> What does this verse mean?

That distinction prevents the specialist from silently becoming the final interpreter.

Graph:

```text
Quran 2:275
     │
     ├── Tafsir al-Tabari
     ├── Tafsir Ibn Kathir
     ├── Tafsir al-Qurtubi
     ├── Tafsir al-Razi
     └── ...
```

Then:

```text
Tafsir Comparison
      │
      ├── agreement
      ├── disagreement
      ├── linguistic basis
      └── legal implication
```

---

# 8. Arabic / Balaghah specialist

This should be partly deterministic.

Pipeline:

```text
Ayah
 │
 ├── morphology
 ├── syntax
 ├── lexical root
 ├── semantic field
 ├── rhetoric
 └── contextual meaning
```

Potential output:

```text
Word: ...
Root: ...
Pattern: ...
Grammatical role: ...
Possible meanings: ...
Balaghah observation: ...
Legal relevance: UNKNOWN
```

Notice the final field.

The linguistic specialist should not automatically say:

> "Therefore the ruling is..."

It supplies linguistic evidence.

---

# 9. Fiqh specialist

This searches historical fiqh positions.

```text
Question
   │
   ├── Hanafi
   ├── Maliki
   ├── Shafi'i
   ├── Hanbali
   ├── Zahiri
   └── Other relevant schools
```

Output:

```text
Position
Scholar
School
Source
Citation
Evidence
Reasoning attributed to scholar
```

Again, attribution matters.

Kallam must distinguish:

```text
Scholar said X
```

from:

```text
Kallam thinks X follows from Scholar's position
```

---

# 10. Madhhab specialist

I would actually keep **Fiqh** and **Madhhab Comparison** separate.

Fiqh specialist:

> Find positions.

Madhhab specialist:

> Explain differences between positions.

For example:

```text
                    MAS'ALAH
                       │
        ┌──────────────┼──────────────┐
        ▼              ▼              ▼
      Hanafi         Maliki        Shafi'i
        │              │              │
        ▼              ▼              ▼
     Position A     Position B     Position C
        │              │              │
        └──────────────┼──────────────┘
                       ▼
                Difference Analysis
```

The comparison specialist asks:

```text
What differs?

Why does it differ?

Is the difference:
    textual?
    linguistic?
    hadith-based?
    usuli?
    qiyasi?
    methodological?
```

This is far more useful than simply producing a list of opinions.

---

# 11. Usul Fiqh specialist

This is one of the expensive specialists.

It should inspect:

```text
general/specific
absolute/qualified
command/prohibition
apparent meaning
explicit meaning
abrogation
consensus
qiyas
istishab
maslahah
sadd al-dhara'i
custom
etc.
```

But we should **not hard-code one universal usūl methodology**.

The specialist needs a methodology context:

```java
public record UsulContext(
    String madhhab,
    List<String> acceptedPrinciples,
    List<String> methodologicalConstraints
) {}
```

Because different usūl traditions can reason differently.

---

# 12. Qiyas specialist

This is where Kallam begins doing genuine analytical assistance.

The structure should explicitly represent:

```text
Asl
 │
 ▼
Original ruling
 │
 ▼
'Illah
 │
 ▼
Far'
 │
 ▼
New ruling
```

For example:

```text
             ASL
              │
              ▼
       Established ruling
              │
              ▼
            'ILLAH
              │
        ┌─────┴─────┐
        ▼           ▼
       FAR'       FAR'
        │           │
        ▼           ▼
    Case A        Case B
```

The specialist should assess:

```text
Is the 'illah identified?

Is it supported?

Is it munasib?

Is it consistent?

Does the new case actually share it?

Are there counter-indications?

Is the analogy accepted within the selected usul methodology?
```

This output should be explicitly marked:

```text
MODEL_INFERENCE
```

unless supported by an identified scholar/source.

---

# 13. Maqasid specialist

This should also be a supporting analytical layer.

It can inspect:

```text
necessity
need
complementary interest

preservation of:
    religion
    life
    intellect
    lineage
    property
```

But Kallam must avoid the dangerous shortcut:

```text
"Maslahah exists → ruling is permissible."
```

Instead:

```text
Maslahah finding
       │
       ▼
Relevant usul framework
       │
       ▼
Weight / conflict analysis
       │
       ▼
Scholar review
```

---

# 14. Specialist orchestration

The orchestrator should not call every specialist every time.

Instead:

```text
Question classifier
        │
        ▼
Research plan
        │
        ├── required
        ├── useful
        └── unnecessary
```

For example:

### Question A

> "What did Imam al-Shafi'i say about X?"

Only:

```text
Fiqh
Source
Citation
```

### Question B

> "Analyze contemporary transaction X."

Potentially:

```text
Quran
Hadith
Fiqh
Madhhab
Usul
Qawa'id
Qiyas
Maqasid
Ikhtilaf
Citation
```

### Question C

> "What does this ayah's grammar imply?"

Mostly:

```text
Quran
Arabic
Nahw
Balaghah
Tafsir
```

This dynamic routing is essential for local inference.

---

# 15. Specialist cost routing

We can formalize it:

```java
public enum ComputeTier {
    ZERO_LLM,
    RETRIEVAL,
    SMALL_MODEL,
    REASONING_MODEL,
    EXPENSIVE_REASONING
}
```

Example:

```text
Quran Locator        ZERO_LLM
Citation Resolver    ZERO_LLM
Hadith Locator      RETRIEVAL
Sanad                RETRIEVAL + SMALL_MODEL
Arabic               SMALL_MODEL
Tafsir               RETRIEVAL
Fiqh                 RETRIEVAL
Madhhab Comparison   REASONING_MODEL
Usul                 REASONING_MODEL
Qiyas                REASONING_MODEL
Synthesis            REASONING_MODEL
```

That architecture is much more realistic for local deployment.

---

# 16. Specialist disagreement

This is important enough to make explicit.

Suppose:

```text
Tafsir Specialist:
    interpretation A

Fiqh Specialist:
    position B

Usul Specialist:
    principle C

Hadith Specialist:
    evidence D has weak grading
```

The orchestrator should **not choose one silently**.

Instead:

```text
SPECIALIST CONFLICT

Tafsir:
    ...

Fiqh:
    ...

Hadith:
    ...

Usul:
    ...

Required resolution:
    Scholar review
```

This conflict becomes an edge in the Evidence Graph.

---

# 17. v0.6 specialist contract

Every specialist therefore follows:

```text
INPUT
  │
  ├── Mas'alah
  ├── ResearchContext
  ├── ExistingEvidence
  ├── ExistingGraph
  └── MethodologyContext
  │
  ▼
SPECIALIST
  │
  ▼
OUTPUT
  │
  ├── Evidence
  ├── Findings
  ├── Graph edges
  ├── Confidence metadata
  ├── Unresolved questions
  └── Follow-up tasks
```

No specialist directly generates the final answer.

---

# The architecture now looks like this

```text
                         KALLAM
                            │
                    ┌───────▼────────┐
                    │ Research Router│
                    └───────┬────────┘
                            │
        ┌───────────────────┼────────────────────┐
        │                   │                    │
        ▼                   ▼                    ▼
   Canonical            Analytical          Scholarly
   Specialists          Specialists         Specialists
        │                   │                    │
   ┌────┼────┐         ┌────┼────┐        ┌─────┼──────┐
   │    │    │         │    │    │        │     │      │
 Quran Hadith Source  Arabic Sanad Balaghah Tafsir Fiqh Madhhab
        │                   │                    │
        └───────────────────┼────────────────────┘
                            ▼
                      Evidence Graph
                            │
                            ▼
                    Reasoning Specialists
                            │
                    ┌───────┼────────┐
                    ▼       ▼        ▼
                   Usul   Qiyas   Maqasid
                    │       │        │
                    └───────┼────────┘
                            ▼
                       Synthesis
                            │
                            ▼
                         Critic
                            │
                            ▼
                    Scholar Review
```

## The key architectural principle for v0.6

**Specialization should reduce reasoning load, not multiply it.**

If ten specialists all invoke a 7B/14B reasoning model, we have built an expensive multi-agent system.

Instead:

```text
                 Cheap deterministic tools
                         ↓
                    retrieval
                         ↓
                  small models
                         ↓
              strongest reasoning model
                         ↓
                     synthesis
```

That is the architecture I would target for Kallam's local-inference requirement.

---

### Next milestone: **v0.7 — Scholar Review, Human-in-the-Loop & Feedback Memory**

This is where the system becomes genuinely *continuously improvable*: an `ʿālim` can correct a citation, reject an interpretation, add a source, modify an `illah`, approve a research path, or request another investigation—and Kallam stores those actions as **structured scholarly feedback**, rather than simply saving conversation text.


Yes. We now move to **v0.7 — Scholar Review, Human-in-the-Loop & Scholarly Feedback Memory**.

This milestone is arguably as important as the reasoning engine itself, because the long-term objective is not merely:

> model → answer

but:

> **research → scholar review → correction → structured learning → better future research**

The system should therefore treat the `ʿālim` as the **authoritative reviewer and supervisor**, not as another agent in the same hierarchy.

# Kallam v0.7

```text
                    Research Case
                         │
                         ▼
                Specialist Research
                         │
                         ▼
                   Evidence Graph
                         │
                         ▼
                  Reasoning + Critic
                         │
                         ▼
                 Scholar Workbench
                         │
              ┌──────────┼──────────┐
              ▼          ▼          ▼
           Accept      Modify     Reject
              │          │          │
              └──────────┼──────────┘
                         ▼
                 Feedback Events
                         │
              ┌──────────┼──────────┐
              ▼          ▼          ▼
         Case Memory   Knowledge   Training
                         Memory     Dataset
```

The key idea:

**Scholar feedback is not just a UI action. It becomes structured data.**

---

# 1. Scholar review should operate on the graph

Do not ask the scholar to review a giant generated essay first.

Instead, expose:

```text
MAS'ALAH
   │
   ├── Evidence
   ├── Claims
   ├── Scholarly Positions
   ├── Usul Analysis
   ├── Arguments
   ├── Conflicts
   ├── Missing Evidence
   └── Preliminary Synthesis
```

The scholar can drill into each item.

For example:

```text
Claim C17

"The transaction contains the relevant feature X."

Evidence:
  E01 Qur'an ...
  E07 Hadith ...
  E15 Fiqh ...

Model reasoning:
  ...

Scholar actions:

[✓ Confirm]
[✎ Modify]
[✗ Reject]
[? Request research]
```

This is much more useful than simply providing:

> 👍 / 👎

---

# 2. Introduce ScholarFeedbackEvent

```java
public record ScholarFeedbackEvent(
    String eventId,
    String caseId,
    String scholarId,
    FeedbackTarget target,
    FeedbackAction action,
    String previousValue,
    String correctedValue,
    String reason,
    List<String> evidenceIds,
    Instant timestamp
) {}
```

Target:

```java
public enum FeedbackTarget {
    EVIDENCE,
    CITATION,
    CLAIM,
    ARGUMENT,
    USUL_ANALYSIS,
    ILLAH,
    QIYAS,
    MADHHAB_POSITION,
    INTERPRETATION,
    SYNTHESIS,
    RESEARCH_TASK,
    GRAPH_RELATION
}
```

Action:

```java
public enum FeedbackAction {
    CONFIRM,
    CORRECT,
    REJECT,
    ADD,
    REMOVE,
    REQUEST_RESEARCH,
    CHANGE_PRIORITY,
    OVERRIDE,
    ANNOTATE
}
```

This gives us a formal audit trail.

---

# 3. Example

Suppose Kallam says:

```text
C17

The relevant 'illah is "financial risk."
```

The scholar changes it:

```text
CORRECT

Old:
financial risk

New:
gharar of the legally relevant type

Reason:
The previous formulation is too broad and does not represent the
relevant usul distinction.
```

Kallam records:

```text
ScholarFeedbackEvent
    target = ILLAH
    action = CORRECT
    previous = "financial risk"
    corrected = "..."
    reason = "..."
```

This is **much more valuable than saving the corrected answer as plain text**.

---

# 4. Scholar corrections should update the graph

Before:

```text
C17
 │
 └── IDENTIFIES_ILLAH → I003
```

After scholar correction:

```text
C17
 │
 └── IDENTIFIES_ILLAH → I009
```

But do not delete I003.

Instead:

```text
I003
 │
 └── REJECTED_BY → ScholarFeedback F001

I009
 │
 └── CONFIRMED_BY → ScholarFeedback F001
```

This preserves research history.

That is essential for auditability.

---

# 5. Never silently overwrite scholarly decisions

Every correction creates a version.

```text
Research Version 1
       │
       ▼
Scholar Review
       │
       ▼
Research Version 2
       │
       ▼
Further Review
       │
       ▼
Research Version 3
```

So the system can answer:

> What did Kallam originally produce?

and:

> What did the scholar change?

and:

> Why was it changed?

This is particularly important in a scholarly domain.

---

# 6. Introduce ResearchRevision

```java
public record ResearchRevision(
    String revisionId,
    String caseId,
    int version,
    String parentRevisionId,
    List<String> feedbackEventIds,
    Instant createdAt,
    String createdBy
) {}
```

Now a research case becomes a trajectory:

```text
R1
 │
 ▼
R2
 │
 ▼
R3
```

rather than one mutable document.

---

# 7. Scholar authority levels

We should also distinguish the type of reviewer.

For example:

```java
public enum ReviewerRole {
    RESEARCH_ASSISTANT,
    SCHOLAR,
    SPECIALIST_SCHOLAR,
    SENIOR_SCHOLAR,
    REVIEW_BOARD
}
```

But **do not use these roles to automatically determine truth**.

They determine things such as:

```text
who can approve
who can override
who can finalize
who can publish
```

not:

```text
higher role = automatically correct
```

The epistemic content still needs evidence.

---

# 8. Scholar can request targeted research

This is one of the most powerful features.

The scholar sees:

```text
Issue:

The relationship between evidence E07 and the proposed 'illah
is not sufficiently established.

[Request additional research]
```

Then:

```text
Scholar
   │
   ▼
Research Request
   │
   ▼
Planner
   │
   ├── Usul Specialist
   ├── Fiqh Specialist
   └── Hadith Specialist
   │
   ▼
New Evidence
   │
   ▼
Graph Update
   │
   ▼
Scholar Review
```

The human therefore remains **inside the research loop**, rather than merely receiving the final output.

---

# 9. Feedback should be classified

Not all corrections are equally useful for future learning.

Introduce:

```java
public enum FeedbackLearningType {
    FACTUAL,
    CITATION,
    INTERPRETATION,
    METHODOLOGY,
    REASONING,
    TERMINOLOGY,
    SCOPE,
    STYLE,
    MISSING_EVIDENCE
}
```

Example:

```text
"Wrong page number"
→ CITATION

"This is not the correct interpretation of the scholar"
→ INTERPRETATION

"You applied qiyas without establishing the 'illah"
→ METHODOLOGY

"You searched the wrong madhhab corpus"
→ SCOPE
```

This gives us a future training dataset with meaningful labels.

---

# 10. This leads directly to continual learning

But **do not fine-tune immediately**.

First collect:

```text
Research trajectory
+
Model output
+
Evidence
+
Scholar correction
+
Reason
+
Final accepted version
```

Then construct training examples.

For example:

```text
INPUT

Question:
...

Evidence:
...

Model analysis:
...

Scholar feedback:
"The proposed 'illah is too broad..."

PREFERRED OUTPUT

...
```

Now we have a high-quality domain-specific example.

---

# 11. Three levels of learning

I would explicitly design Kallam for three kinds of learning.

### Level 1 — Runtime learning

No model training.

```text
Scholar correction
       ↓
Research Memory
       ↓
Next research case
```

For example:

> This scholar consistently requires source verification before accepting a particular type of claim.

The system can adapt its workflow.

---

### Level 2 — Retrieval learning

The system learns which sources are useful.

```text
Question type
      ↓
successful sources
      ↓
ranking adjustment
```

For example:

```text
Usul question
   ↓
Source A repeatedly useful
   ↓
higher retrieval priority
```

This is cheap and should happen early.

---

### Level 3 — Model learning

Only after enough validated data:

```text
Scholar feedback dataset
       ↓
SFT
       ↓
Preference optimization
       ↓
Potential RL
```

This is where fine-tuning eventually becomes useful.

---

# 12. We should therefore introduce ResearchMemory

```java
public interface ResearchMemory {

    void record(ScholarFeedbackEvent event);

    List<ResearchMemoryItem> retrieve(
        ResearchContext context,
        int limit
    );
}
```

But the memory should not be:

> "Here are 10,000 old conversations."

Instead:

```text
Memory
 ├── verified source preference
 ├── scholar correction
 ├── methodology preference
 ├── terminology correction
 ├── rejected reasoning pattern
 ├── successful research path
 └── unresolved research issue
```

Structured memory is dramatically more useful.

---

# 13. Important: scholar-specific vs universal learning

We need to distinguish:

```text
Scholar A feedback
```

from:

```text
Kallam-wide knowledge
```

For example:

```text
Scholar A prefers methodology X
```

should not automatically become:

```text
Islamic methodology = X
```

Therefore:

```java
public enum MemoryScope {
    CASE,
    SCHOLAR,
    RESEARCH_GROUP,
    METHODOLOGY,
    GLOBAL
}
```

This is especially important for madhhab and usūl differences.

---

# 14. The scholar's methodology profile

Eventually we can have:

```java
public record ScholarResearchProfile(
    String scholarId,
    Set<String> madhhabContexts,
    Set<String> usulFrameworks,
    Set<String> preferredSources,
    Set<String> prohibitedShortcuts,
    Map<String, String> terminology
) {}
```

But again, this should influence **research configuration**, not silently modify source truth.

---

# 15. Review states

The case lifecycle can now become:

```text
DRAFT
   ↓
RESEARCHING
   ↓
ANALYSIS_READY
   ↓
SCHOLAR_REVIEW
   │
   ├── REQUEST_MORE_RESEARCH
   │       ↓
   │   RESEARCHING
   │
   ├── REVISE
   │       ↓
   │   SCHOLAR_REVIEW
   │
   └── ACCEPT
           ↓
     SCHOLAR_ACCEPTED
```

And importantly:

```text
SCHOLAR_ACCEPTED
```

does **not necessarily mean:

> "Kallam has issued a fatwa."

It means:

> "The scholar accepted this research artifact for the defined purpose."

That distinction should remain in the domain model.

---

# 16. The final v0.7 architecture

```text
                       RESEARCH CASE
                            │
                            ▼
                     Research Engine
                            │
                            ▼
                      Evidence Graph
                            │
                            ▼
                   Reasoning + Critic
                            │
                            ▼
                    Scholar Workbench
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          Confirm         Correct        Reject
             │              │              │
             └──────────────┼──────────────┘
                            ▼
                  Scholar Feedback Event
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
       Graph Revision   Research Memory   Audit Log
                            │
                            ▼
                    Future Research
                            │
                            ▼
                     Training Dataset
```

---

# v0.7 completion criteria

We should consider this milestone complete when Kallam can:

* present evidence and reasoning to a scholar;
* let the scholar approve/reject/correct individual claims;
* let the scholar correct citations and graph relationships;
* request additional targeted research;
* preserve every revision;
* preserve the reason behind corrections;
* distinguish scholar-specific feedback from global knowledge;
* retrieve relevant historical feedback;
* construct training examples from accepted corrections;
* maintain a complete audit trail.

---

## And now the architecture reaches an important point

After v0.7, we have:

```text
v0.1  Domain Core
v0.2  Evidence / RAG
v0.3  Local Reasoning + Critic
v0.3.1 Structured Validation
v0.4  Citation / Provenance
v0.5  Evidence Graph
v0.6  Specialist Intelligence
v0.7  Scholar Review + Feedback
```

At this point, **the research engine itself is essentially complete**.

The next milestone should therefore change focus:

# **v0.8 — Persistent Research Memory + Continual Runtime Learning**

This is where we implement the mechanism you originally asked for: **new knowledge and corrections from the `ʿulamāʾ` can influence future research at runtime without requiring immediate fine-tuning**.

The key design will be:

```text
Scholar teaches
      ↓
Structured memory
      ↓
Validated memory
      ↓
Runtime retrieval
      ↓
Research planner adapts
      ↓
Specialists adapt
      ↓
Reasoning context adapts
```

while keeping the underlying model weights frozen.

That gives us **continual improvement first, fine-tuning later**—which is much more practical for the local-inference architecture.


Absolutely. **v0.8 is the point where Kallam becomes a continuously improving research system without requiring continuous fine-tuning.**

The key principle is:

> **Teach the system through validated memory first; train the model only when accumulated evidence justifies it.**

# Kallam v0.8 — Persistent Research Memory & Runtime Learning

```text
                     SCHOLAR
                        │
                        ▼
                Feedback / Correction
                        │
                        ▼
                 Memory Candidate
                        │
                        ▼
                 Memory Validator
                        │
             ┌──────────┴──────────┐
             ▼                     ▼
        Case Memory          Long-term Memory
             │                     │
             └──────────┬──────────┘
                        ▼
                 Memory Retrieval
                        │
                        ▼
                  Research Planner
                        │
                        ▼
                    Specialists
                        │
                        ▼
                 Reasoning + Critic
```

The model weights can remain completely frozen.

---

# 1. Three different kinds of memory

Do not make one giant vector database.

Kallam needs different memory classes.

## A. Case memory

Everything relevant to the current research question.

```text
Case
 ├── evidence
 ├── claims
 ├── arguments
 ├── citations
 ├── specialist findings
 ├── scholar feedback
 └── revisions
```

Short-lived or archived after the case.

---

## B. Scholarly memory

Validated knowledge accumulated across cases.

Examples:

```text
Scholar X corrected attribution Y.
Source Z is preferred for topic A.
Interpretation B was rejected in case C.
A particular usul distinction is important for question type D.
```

This is persistent.

---

## C. Operational memory

This is about **how to research**, rather than what the religion says.

For example:

```text
For questions concerning X:
    search corpus A before corpus B.

For this research methodology:
    verify hadith grading independently.

For this scholar:
    require primary-source citation.
```

This can improve orchestration without changing substantive knowledge.

---

# 2. Memory should have epistemic status

This is critical.

A memory item cannot simply be:

```text
"text": "Scholar says X"
```

It needs provenance.

```java
public record MemoryItem(
    String id,
    MemoryScope scope,
    MemoryType type,

    String content,

    List<String> sourceEvidenceIds,
    List<String> supportingFeedbackIds,

    EpistemicStatus status,

    double confidence,

    String createdBy,
    Instant createdAt,

    Instant lastValidatedAt
) {}
```

Possible statuses:

```text
CANDIDATE
VALIDATED
SCHOLAR_CONFIRMED
DISPUTED
REJECTED
SUPERSEDED
```

So Kallam knows the difference between:

> "The model remembers this"

and:

> "A scholar has explicitly confirmed this."

---

# 3. Never let raw model output become permanent knowledge

This should be a hard architectural rule.

```text
Model output
    ↓
NOT memory
```

Instead:

```text
Model output
    ↓
candidate memory
    ↓
validation
    ↓
┌───────────────┐
│               │
▼               ▼
validated      rejected
│
▼
persistent memory
```

This prevents hallucinations from becoming self-reinforcing.

---

# 4. Memory promotion

Introduce a promotion pipeline:

```text
EPHEMERAL
    ↓
CANDIDATE
    ↓
VALIDATED
    ↓
SCHOLAR_CONFIRMED
```

For example:

### First encounter

```text
Candidate:
"Scholar A appears to use principle X in this context."
```

### Evidence verification

```text
Primary source found.
```

→ `VALIDATED`

### Scholar confirms

```text
"This interpretation is correct."
```

→ `SCHOLAR_CONFIRMED`

Only then should it receive strong retrieval priority.

---

# 5. Memory retrieval should be selective

Do not inject all historical knowledge into the prompt.

That would destroy the local-token budget.

Instead:

```text
Current question
      │
      ▼
Memory Query
      │
      ├── semantic similarity
      ├── domain
      ├── methodology
      ├── source relevance
      ├── scholar scope
      └── epistemic status
      │
      ▼
Top K memory
      │
      ▼
Context compression
      │
      ▼
Reasoning model
```

The model might receive only:

```text
Relevant prior knowledge:

M17:
Scholar-confirmed distinction X.

M42:
Previous case established source Y as relevant.

M71:
Prior interpretation Z was rejected.
```

Not 500 pages of history.

---

# 6. Memory retrieval needs hierarchy

I recommend this priority:

```text
1. Current case evidence
2. Scholar-confirmed memory
3. Methodology memory
4. Validated research memory
5. Disputed memory
6. Candidate memory
```

The system should never allow a low-confidence memory to override primary evidence.

---

# 7. Memory conflicts

This will happen constantly.

Suppose:

```text
Memory A:
"Scholar X holds position A."

Memory B:
"Scholar X holds position B."
```

Do not resolve this with an LLM.

Create:

```text
MEMORY CONFLICT
      │
      ├── M001
      │
      └── M002
```

Then investigate:

```text
source
edition
date
context
different works
different positions
retraction
different narration
```

Eventually:

```text
M001
   └── applies to context A

M002
   └── applies to context B
```

or:

```text
M001 → rejected
M002 → validated
```

This is where the Evidence Graph and Memory system work together.

---

# 8. Runtime adaptation

Now we can implement the feature you originally wanted:

> the agent can learn from the `ʿālim` at runtime.

Example:

```text
Scholar:
"Don't use this secondary source for this type of claim.
Check the original work."
```

Kallam creates:

```text
OperationalMemory M102

scope:
SCHOLAR

rule:
For claims attributed to Scholar X,
prefer primary works over secondary summaries.

status:
SCHOLAR_CONFIRMED
```

Next time:

```text
Research Planner
      ↓
sees M102
      ↓
changes research plan
      ↓
primary source retrieval
```

No fine-tuning required.

---

# 9. This is essentially policy learning without weight updates

Conceptually:

```text
                     MODEL
                       │
                       │ frozen
                       ▼
              ┌─────────────────┐
              │ Runtime Policy  │
              └────────┬────────┘
                       │
             ┌─────────┼─────────┐
             ▼         ▼         ▼
          Memory     Rules     Planner
             │         │         │
             └─────────┼─────────┘
                       ▼
                  Model Context
```

The model itself doesn't change.

The **environment around the model becomes smarter**.

For your local architecture, this is a major advantage.

---

# 10. Research policy

We can formalize this.

```java
public record ResearchPolicy(
    String id,
    String scope,
    List<ResearchRule> rules,
    EvidencePriority priority
) {}
```

Example:

```text
RULE-001

IF:
    claim concerns hadith authenticity

THEN:
    invoke Sanad Specialist
    before synthesis
```

Another:

```text
RULE-002

IF:
    scholar has marked source X unreliable for this task

THEN:
    lower retrieval priority of X
```

Another:

```text
RULE-003

IF:
    evidence is disputed

THEN:
    require explicit mention in final report
```

These rules can evolve from scholar feedback.

---

# 11. Memory should be typed, not just vectorized

This is a major implementation decision.

Do not rely on:

```text
embedding → nearest neighbors
```

alone.

Use hybrid memory:

```text
                MEMORY
                   │
       ┌───────────┼────────────┐
       ▼           ▼            ▼
   Structured   Full-text    Vector
   metadata     search       search
       │           │            │
       └───────────┼────────────┘
                   ▼
               Reranker
                   │
                   ▼
             Relevant Memory
```

For local deployment, SQLite + FTS + vector extension can be sufficient initially.

You don't need a distributed vector database for v0.8.

---

# 12. Memory compression

This is essential for limited-token inference.

Suppose a scholar has made 50 corrections across 20 cases.

Don't put all 50 into the prompt.

Compress:

```text
Raw feedback
      ↓
Cluster
      ↓
Pattern extraction
      ↓
Validated memory
```

Example:

```text
50 corrections
      ↓
7 recurring patterns
      ↓
3 durable research rules
```

Then the reasoning model receives only:

```text
Relevant research rules:
1. ...
2. ...
3. ...
```

---

# 13. Memory decay

Not every memory should live forever with equal priority.

Add:

```java
public record MemoryValidity(
    Instant createdAt,
    Instant lastConfirmedAt,
    Instant expiresAt,
    double priority
) {}
```

But be careful with scholarly knowledge.

For example:

```text
Operational preference
→ can decay.

Search ranking
→ can decay.

Scholar-confirmed historical position
→ should generally not decay automatically.
```

So decay depends on memory type.

---

# 14. Memory provenance

Every memory should answer:

> Why does Kallam believe this?

For example:

```text
M102

Statement:
"Prefer primary source X for this type of attribution."

Origin:
Scholar feedback F088

Evidence:
E201
E204

Confirmed:
Scholar S03

Created:
2026-08-26

Status:
SCHOLAR_CONFIRMED
```

This makes memory auditable.

---

# 15. Runtime learning loop

The complete loop becomes:

```text
                ┌────────────────────┐
                │                    │
                ▼                    │
          Current Question           │
                │                    │
                ▼                    │
          Retrieve Memory            │
                │                    │
                ▼                    │
          Research Planning          │
                │                    │
                ▼                    │
            Specialists              │
                │                    │
                ▼                    │
          Evidence Graph             │
                │                    │
                ▼                    │
        Reasoning + Critic           │
                │                    │
                ▼                    │
         Scholar Review              │
                │                    │
                ▼                    │
        Feedback Events              │
                │                    │
                ▼                    │
         Memory Validation           │
                │                    │
                └────────────────────┘
```

This is the **continual runtime learning loop**.

---

# 16. What about fine-tuning?

Now we can answer your earlier question more precisely.

### Don't fine-tune after every correction.

Instead:

```text
Scholar feedback
      ↓
Runtime memory
      ↓
Accumulation
      ↓
Evaluation
      ↓
Training dataset
      ↓
Fine-tuning candidate
```

Only when enough high-quality examples exist do we consider training.

---

# 17. Training dataset construction

Every accepted correction can become a structured example:

```json
{
  "task": "...",
  "context": "...",
  "evidence": ["E01", "E03"],
  "model_analysis": "...",
  "scholar_feedback": "...",
  "accepted_revision": "...",
  "feedback_type": "USUL_METHODOLOGY"
}
```

Then dataset quality can be evaluated before training.

---

# 18. Eventually, multiple learning mechanisms

Kallam could evolve into:

```text
                   CONTINUAL LEARNING
                          │
         ┌────────────────┼────────────────┐
         ▼                ▼                ▼
     Retrieval          Policy          Model
      Learning          Learning        Learning
         │                │                │
      ranking          routing         fine-tuning
         │                │                │
         └────────────────┼────────────────┘
                          ▼
                       KALLAM
```

And potentially later:

```text
Preference optimization
DPO / similar methods
RL-based research planning
Distillation
Specialist-specific adapters
```

But these belong much later.

---

# 19. Most important: evaluation before learning

This is where I would be strict.

A system that learns continuously can also **learn the wrong thing continuously**.

Therefore:

```text
Feedback
   ↓
Candidate memory
   ↓
Validation
   ↓
Evaluation
   ↓
Promotion
```

And before model training:

```text
Training candidate
       ↓
Benchmark
       ↓
Regression test
       ↓
Compare against previous model
       ↓
Deploy only if improved
```

Kallam should never say:

> "The latest model is better because it has learned more."

It should demonstrate that through evaluation.

---

# 20. v0.8 completion criteria

We should be able to demonstrate:

### Runtime

* Scholar correction becomes structured memory.
* Relevant memory is retrieved in later cases.
* Memory affects research planning.
* Memory can influence specialist selection.
* Memory can influence evidence prioritization.

### Safety

* Unvalidated memory cannot become authoritative.
* Conflicting memory is preserved.
* Memory provenance is visible.
* Scholar-specific knowledge does not automatically become universal knowledge.

### Efficiency

* Only relevant memories enter context.
* Memory is compressed.
* Retrieval is hybrid.
* Token consumption remains bounded.

### Learning

* Feedback can produce training examples.
* Training data can be exported.
* Training remains optional.
* Existing model can remain frozen.

---

# The architecture now becomes

```text
                         KALLAM
                            │
                     Research Router
                            │
             ┌──────────────┴──────────────┐
             │                             │
        Specialist Layer              Memory Layer
             │                             │
             ▼                             ▼
        Evidence Graph              Runtime Memory
             │                             │
             └──────────────┬──────────────┘
                            ▼
                      Local Reasoning
                            │
                            ▼
                          Critic
                            │
                            ▼
                    Scholar Workbench
                            │
                            ▼
                   Feedback Events
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          Graph          Memory        Training
         Revision       Promotion       Dataset
             │              │              │
             └──────────────┴──────────────┘
                            │
                            ▼
                    Future Research
```

## And this sets up **v0.9 — Evaluation, Benchmarking & Safety**

Before Kallam starts modifying models or making sophisticated autonomous research decisions, v0.9 should establish a **formal evaluation framework**:

```text
                    KALLAM EVALUATION
                           │
       ┌───────────────────┼───────────────────┐
       ▼                   ▼                   ▼
   Retrieval           Reasoning           Citation
   Accuracy            Quality             Integrity
       │                   │                   │
       ▼                   ▼                   ▼
   Hadith              Usul/Qiyas         Provenance
   Quran               Madhhab            Attribution
   Tafsir              Comparison         Source fidelity
       │                   │                   │
       └───────────────────┼───────────────────┘
                           ▼
                    Scholar Evaluation
                           │
                           ▼
                    Regression Suite
                           │
                           ▼
                  Model/Agent Promotion
```

That milestone is especially important for Kallam: **we need to know whether a new model, prompt, specialist, memory rule, or fine-tune actually improves the quality of istinbāṭ-support research without increasing hallucinated attribution, citation errors, methodological shortcuts, or unjustified conclusions.**


Yes. We are now at **v0.9 — Evaluation, Benchmarking & Safety**.

This milestone should come **before v1.0 and before serious continual fine-tuning**. Otherwise Kallam can become more sophisticated without knowing whether it is actually becoming more reliable.

The core principle is:

> **Every component that can influence a scholarly research conclusion must be measurable, reproducible, and regression-tested.**

# Kallam v0.9

```text
                         KALLAM
                            │
                    Research Case
                            │
                            ▼
                     Full Pipeline
                            │
          ┌─────────────────┼─────────────────┐
          ▼                 ▼                 ▼
      Retrieval          Reasoning        Provenance
          │                 │                 │
          ▼                 ▼                 ▼
       Evidence          Analysis          Citation
          │                 │                 │
          └─────────────────┼─────────────────┘
                            ▼
                       Evaluation
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          Automated       Scholar        Regression
           Tests          Review           Suite
             │              │              │
             └──────────────┼──────────────┘
                            ▼
                     Release Gate
```

---

# 1. First: define what "good" means

We should **not** use one generic score such as:

```text
Kallam quality = 87%
```

That would hide important failures.

Instead, establish separate dimensions.

```text
Kallam Evaluation
│
├── Retrieval
├── Source fidelity
├── Citation
├── Attribution
├── Arabic analysis
├── Hadith analysis
├── Tafsir analysis
├── Fiqh
├── Madhhab comparison
├── Usul
├── Qiyas
├── Reasoning
├── Contradiction detection
├── Scholar alignment
├── Calibration
└── Safety
```

---

# 2. Retrieval evaluation

First question:

> Did Kallam find the right material?

For example:

```text
Question
   │
   ▼
Retriever
   │
   ▼
Top 10 evidence
```

Measure:

```text
Recall@K
Precision@K
MRR
nDCG
```

But for Kallam, we also need domain-specific measures.

For example:

```text
Quran retrieval:
    exact ayah?

Hadith:
    correct narration?

Tafsir:
    correct work / passage?

Fiqh:
    correct scholar / position?

Usul:
    correct principle?
```

---

# 3. Citation integrity benchmark

This should be one of Kallam's strongest benchmarks.

Create cases such as:

```text
Claim → correct citation
Claim → wrong citation
Claim → partially supporting citation
Claim → contradictory citation
Claim → nonexistent citation
```

The system should identify them.

Example:

```text
Claim:
"Scholar X explicitly states Y."

Citation:
Book A, vol. 2, p. 130.
```

Evaluation:

```text
Exists?              ✓
Correct locator?     ✓
Correct attribution? ✓
Actually supports?   ✗
```

The desired output:

```text
PARTIALLY_VERIFIED
```

rather than blindly accepting it.

---

# 4. Attribution benchmark

This deserves its own category.

Test:

```text
Who said this?

What exactly did they say?

Where?

Was this their own position?

Is it attributed to them by another scholar?

Is the attribution disputed?
```

Kallam should distinguish:

```text
DIRECT_QUOTE
PARAPHRASE
ATTRIBUTION
SECONDARY_REPORT
MODEL_INFERENCE
```

This is essential because an LLM can produce an entirely plausible sentence and incorrectly attribute it to a classical scholar.

---

# 5. Hadith benchmark

The Hadith subsystem should have several independent tests.

```text
Hadith
│
├── Identification
├── Matn
├── Variant
├── Source
├── Isnad
├── Narrator
├── Grading
└── Attribution
```

For example:

```text
Input:
partial Arabic matn

Expected:
collection
book
hadith
variant
```

Another:

```text
Input:
chain

Expected:
known narrator relationships
possible discontinuity
relevant grading information
```

The benchmark must not collapse:

> "Hadith retrieval"

into:

> "Hadith authenticity."

Those are different tasks.

---

# 6. Tafsir benchmark

Test whether the system correctly distinguishes:

```text
Qur'an text
      ↓
Tafsir text
      ↓
Tafsir interpretation
      ↓
Fiqh implication
```

A particularly important negative test:

```text
Tafsir says X

Does Kallam incorrectly report:

"Qur'an says X"?
```

That should be a serious failure.

---

# 7. Madhhab benchmark

Create cases where:

```text
Hanafi → A
Maliki → B
Shafi'i → A
Hanbali → C
```

The system should preserve the disagreement.

Evaluation should measure:

```text
Position identification
Scholar attribution
School attribution
Evidence attribution
Reason for disagreement
```

And especially:

> Does the model accidentally manufacture consensus?

That becomes a specific safety metric.

---

# 8. Usul benchmark

This is more difficult.

We should evaluate individual steps rather than just the final conclusion.

```text
Text
 ↓
Interpretive rule
 ↓
Usul principle
 ↓
Application
 ↓
Result
```

For example:

```text
Was the text correctly classified?

Was 'amm/khass handled correctly?

Was the principle applicable?

Was an exception overlooked?

Was qiyas actually required?

Was the chosen methodology appropriate?
```

The benchmark therefore evaluates the **reasoning trace**, not merely the final prose.

---

# 9. Qiyas benchmark

Qiyas deserves a structured test format.

```text
ASL
RULING
ILLAH
FAR'
RELATION
CONCLUSION
```

Then evaluate each component:

```text
Asl correctness          ✓
Original ruling          ✓
'Illah identification    ?
'Illah evidence          ?
Far' similarity          ✓
Counterargument          ?
Conclusion               ?
```

This prevents:

> correct-looking conclusion

from hiding:

> invalid analogy.

---

# 10. Reasoning benchmark

We should evaluate reasoning separately from factual retrieval.

For each case:

```text
Evidence
   ↓
Claim
   ↓
Argument
   ↓
Counterargument
   ↓
Synthesis
```

The evaluator checks:

```text
Does the argument actually follow?

Are assumptions explicit?

Are contradictions detected?

Does the conclusion exceed the evidence?

Are alternative interpretations considered?
```

This is where the critic loop becomes measurable.

---

# 11. "Unsupported leap" metric

I would make this a first-class Kallam metric.

Suppose:

```text
E1 → Claim A

Model:
A therefore B.

But no evidence or valid reasoning establishes B.
```

That is an:

```text
UNSUPPORTED_INFERENCE
```

Count them.

```text
Unsupported inference rate
=
unsupported reasoning steps
/
total reasoning steps
```

The objective is to reduce this over time.

---

# 12. Evidence coverage

For each conclusion:

```text
Conclusion
    │
    ├── Claim A ── Evidence
    ├── Claim B ── Evidence
    ├── Claim C ── Evidence
    └── Claim D ── ?
```

Then:

```text
Evidence Coverage = supported claims / total material claims
```

This is more meaningful than simply counting citations.

A response with 30 citations can still have poor evidence coverage.

---

# 13. Contradiction detection

Test cases should intentionally contain conflicts.

Example:

```text
Source A → position X

Source B → position Y
```

The system should say:

```text
CONFLICT DETECTED
```

rather than:

```text
X + Y = unified conclusion
```

Evaluate:

```text
conflict detection recall
conflict false-positive rate
```

---

# 14. Scholar evaluation

Automated benchmarks cannot fully evaluate this domain.

So we need a **Scholar Evaluation Set**.

A panel of qualified reviewers can rate:

```text
1–5

Evidence selection
Citation accuracy
Attribution accuracy
Reasoning quality
Usul methodology
Handling of ikhtilaf
Completeness
Transparency
Usefulness
```

But importantly:

> The scholar is evaluating the **research artifact**, not delegating the ruling to Kallam.

---

# 15. Blind evaluation

When possible, scholars should not know:

```text
which model produced the analysis
which prompt was used
which version of Kallam
```

Then compare:

```text
Model A
Model B
Model C
```

against identical research cases.

This gives us much better evaluation data.

---

# 16. Regression suite

This becomes extremely important once Kallam begins learning.

Suppose v0.9.3 improves hadith retrieval.

It might accidentally make madhhab comparison worse.

Therefore every change runs:

```text
                    CHANGE
                      │
                      ▼
               Full benchmark
                      │
       ┌──────────────┼──────────────┐
       ▼              ▼              ▼
     Better        Same           Worse
       │              │              │
       ▼              ▼              ▼
   candidate       acceptable      reject
```

No change should be promoted simply because one benchmark improved.

---

# 17. Golden cases

Create a permanent collection of carefully reviewed cases.

```text
golden/
├── quran/
├── hadith/
├── tafsir/
├── fiqh/
├── madhhab/
├── usul/
├── qiyas/
├── maqasid/
└── mixed/
```

Each case contains:

```json id="k5d4n0"
{
  "case_id": "USUL-0042",
  "question": "...",
  "required_evidence": [],
  "acceptable_positions": [],
  "known_disagreements": [],
  "critical_citations": [],
  "forbidden_inferences": [],
  "evaluation_notes": []
}
```

The exact format can evolve, but the principle is important:

**golden cases become the permanent memory of what Kallam must not regress on.**

---

# 18. Adversarial evaluation

We should deliberately attack the system.

### Hallucinated citation

```text
"See Book X, vol. 8, p. 999"
```

where the page does not exist.

### False attribution

```text
"Imam X said..."
```

but the statement belongs to someone else.

### Fake consensus

```text
"The scholars unanimously agree..."
```

when there is known ikhtilaf.

### Tafsir contamination

```text
Tafsir statement → incorrectly presented as Quran.
```

### Methodology contamination

```text
Madhhab A's usul → applied while claiming Madhhab B.
```

### Unsupported qiyas

```text
No established 'illah
        ↓
model invents one
        ↓
ruling
```

These should be explicit red-team categories.

---

# 19. Safety architecture

Kallam should have a **Research Safety Gate** before scholar-facing output.

```text
Research
   ↓
Safety Gate
   │
   ├── citation risk
   ├── attribution risk
   ├── unsupported inference
   ├── unresolved disagreement
   ├── missing primary evidence
   ├── methodological ambiguity
   └── excessive certainty
   ↓
Scholar Report
```

The system should be able to say:

```text
RESEARCH INSUFFICIENT

Reasons:
- Primary evidence not verified.
- Two scholarly positions conflict.
- Proposed 'illah remains inferential.
```

That is a successful outcome.

---

# 20. Confidence calibration

We should not let:

```text
model confidence = truth probability
```

Instead, calibrate confidence against benchmark outcomes.

For example:

```text
Prediction confidence: 0.90
Actual correctness:    0.87
```

After many cases we can measure calibration.

Use:

```text
Brier score
Expected Calibration Error
reliability curves
```

But the UI should use meaningful labels:

```text
Verified
Strongly supported
Partially supported
Disputed
Unverified
Insufficient evidence
```

rather than pretending that:

> 87.3% certainty

has scholarly meaning.

---

# 21. Evaluation should exist at every layer

This is important for debugging.

```text
                 CASE
                   │
             ┌─────┴─────┐
             ▼           ▼
         Retrieval    Planning
             │           │
             ▼           ▼
         Evidence     Specialists
             │           │
             └─────┬─────┘
                   ▼
                Graph
                   │
                   ▼
               Reasoning
                   │
                   ▼
                 Critic
                   │
                   ▼
               Citation
                   │
                   ▼
                Report
```

Every arrow should be measurable.

If the final answer is wrong, we should be able to determine:

```text
Wrong source?
Wrong retrieval?
Wrong attribution?
Wrong specialist?
Wrong graph relation?
Wrong reasoning?
Wrong synthesis?
```

That is much more useful than:

> "The LLM hallucinated."

---

# 22. Evaluation data model

Introduce:

```java id="3w1f9a"
public record EvaluationRun(
    String id,
    String systemVersion,
    String modelVersion,
    String promptVersion,
    String datasetVersion,
    Instant startedAt,
    List<EvaluationResult> results
) {}
```

And:

```java id="trp3e9"
public record EvaluationResult(
    String caseId,
    String metric,
    double score,
    String evaluator,
    List<String> findings
) {}
```

This means we can compare:

```text
Kallam v0.9.1
vs
Kallam v0.9.2
```

while knowing exactly which model, prompt, knowledge corpus, and specialist versions were involved.

---

# 23. Version everything

By v0.9, versioning becomes mandatory.

```text
Kallam
│
├── Application version
├── Model version
├── Prompt version
├── Specialist version
├── Knowledge corpus version
├── Retrieval index version
├── Graph schema version
├── Memory version
└── Evaluation dataset version
```

A research result should be reproducible:

```text
Case C1042

Kallam:
    v0.9.4

Reasoning model:
    model-X / revision Y

Quran corpus:
    corpus-v3

Hadith corpus:
    corpus-v7

Prompts:
    prompt-v18

Memory:
    snapshot-2026-08-26

Graph:
    graph-v12
```

This is essential for serious scholarly software.

---

# 24. The promotion gate

Now we can establish a formal rule:

```text
                   NEW CHANGE
                       │
                       ▼
                  Unit tests
                       │
                       ▼
                Specialist tests
                       │
                       ▼
                 Golden cases
                       │
                       ▼
                Adversarial tests
                       │
                       ▼
                 Scholar review
                       │
                       ▼
               Regression check
                       │
              ┌────────┴────────┐
              ▼                 ▼
           ACCEPT             REJECT
              │
              ▼
           RELEASE
```

This becomes the foundation for v1.0.

---

# 25. v0.9 success criteria

Kallam should now be able to answer not only:

> "What did we conclude?"

but:

> **"How well did the system perform, where did it fail, and what evidence supports that assessment?"**

Specifically:

* Retrieval can be benchmarked.
* Citation accuracy can be measured.
* Attribution can be measured.
* Hadith retrieval and sanad tasks can be independently tested.
* Tafsir/fiqh/madhhab tasks have dedicated evaluation sets.
* Usul and qiyas reasoning can be evaluated step-by-step.
* Unsupported inferences are detectable.
* Contradictions are measurable.
* Scholar evaluations can be incorporated.
* Every model/system change has regression testing.
* Runtime memory changes can be evaluated before promotion.
* Fine-tuned models can be compared against the frozen baseline.
* Every research artifact remains reproducible.

---

# The complete architecture is now approaching v1.0

```text id="l2kn08"
                          KALLAM
                            │
                    ┌───────▼────────┐
                    │ RESEARCH ROUTER│
                    └───────┬────────┘
                            │
       ┌────────────────────┼────────────────────┐
       ▼                    ▼                    ▼
   Retrieval             Specialists           Memory
       │                    │                    │
       └────────────────────┼────────────────────┘
                            ▼
                      Evidence Graph
                            │
                            ▼
                  Local Reasoning Model
                            │
                            ▼
                         Critic
                            │
                            ▼
                 Citation Verification
                            │
                            ▼
                   Scholar Workbench
                            │
                            ▼
                  Scholar Feedback
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
          Revision        Memory        Dataset
             │              │              │
             └──────────────┼──────────────┘
                            ▼
                       Evaluation
                            │
                            ▼
                      Release Gate
                            │
                            ▼
                       NEXT MODEL
```

## And this brings us to **v1.0 — Production Scholar Research Assistant**

The important thing about v1.0 is that it should **not introduce another giant capability**.

It should package everything we've built into a coherent production system:

```text
Question
   ↓
Mas'alah decomposition
   ↓
Dynamic specialist planning
   ↓
Evidence discovery
   ↓
Citation/provenance verification
   ↓
Evidence graph construction
   ↓
Structured istinbāṭ analysis
   ↓
Critic / contradiction analysis
   ↓
Scholar review
   ↓
Revision
   ↓
Final research dossier
```

And the final product should be a **research dossier/workbench for the ʿālim**, not an "AI mufti."

The next step after that would be particularly useful: **turning this architecture into the actual repository/codebase structure**—modules, Java classes/interfaces, database schema, local model interface, specialist registry, graph implementation, memory store, evaluation harness, and the first executable end-to-end `v0.1 → v1.0` implementation path.


Yes. We have reached the point where the architecture should stop expanding conceptually and become **an executable system**.

# Kallam v1.0 — Production Scholar Research Assistant

The central objective of v1.0 is:

> **Turn the architecture into a reproducible, local-first research workstation in which every important conclusion can be traced back through evidence, reasoning, provenance, specialist analysis, and scholar review.**

Not an autonomous mufti.

Not a chatbot with a large Islamic corpus.

A **research operating system for the ʿālim**.

---

# 1. The v1.0 product

The primary interface should be a **Research Case**.

The scholar enters:

> "What is the ruling/status of X under methodology Y, and what are the relevant evidences and positions of the scholars?"

Kallam creates:

```text
CASE-000142

Question
   │
   ├── Mas'alah
   ├── Scope
   ├── Madhhab / usul context
   ├── Key terms
   └── Research objectives
```

Then it generates a research plan.

```text
Research Plan
──────────────────────────────
✓ Qur'an
✓ Hadith
✓ Tafsir
✓ Arabic / Balaghah
✓ Usul al-Fiqh
✓ Fiqh positions
✓ Madhhab comparison
✓ Historical scholarly positions
✓ Contemporary application
✓ Counterarguments
```

The scholar can modify the plan before execution.

That is important.

---

# 2. The actual v1 pipeline

```text
                     USER / ʿĀLIM
                          │
                          ▼
                   Research Case
                          │
                          ▼
                  MAS'ALAH ANALYZER
                          │
                          ▼
                  RESEARCH PLANNER
                          │
              ┌───────────┼───────────┐
              ▼           ▼           ▼
           Quran       Hadith       Fiqh
         Specialist   Specialist   Specialist
              │           │           │
              ▼           ▼           ▼
           Tafsir       Sanad       Madhhab
              │           │           │
              └───────────┼───────────┘
                          ▼
                    EVIDENCE GRAPH
                          │
                          ▼
                   USUL ANALYZER
                          │
                          ▼
                    REASONING ENGINE
                          │
                          ▼
                       CRITIC
                          │
                ┌─────────┴─────────┐
                ▼                   ▼
           More research        Sufficient
                │                   │
                └───────┬───────────┘
                        ▼
                 SCHOLAR WORKBENCH
                        │
                        ▼
                  REVIEW / EDIT
                        │
                        ▼
                 FINAL DOSSIER
                        │
                        ▼
              MEMORY + EVALUATION
```

That is the system we actually implement.

---

# 3. Repository structure

I recommend **not starting with microservices**.

For local inference, start with a modular monolith.

Something like:

```text
kallam/
│
├── apps/
│   ├── api/
│   ├── cli/
│   └── worker/
│
├── core/
│   ├── domain/
│   ├── application/
│   └── ports/
│
├── research/
│   ├── planner/
│   ├── decomposition/
│   ├── orchestration/
│   └── workflow/
│
├── specialists/
│   ├── quran/
│   ├── tafsir/
│   ├── hadith/
│   ├── sanad/
│   ├── arabic/
│   ├── balaghah/
│   ├── fiqh/
│   ├── madhhab/
│   ├── usul/
│   ├── maqasid/
│   └── comparison/
│
├── evidence/
│   ├── retrieval/
│   ├── provenance/
│   ├── citation/
│   ├── graph/
│   └── verification/
│
├── reasoning/
│   ├── engine/
│   ├── critic/
│   ├── synthesis/
│   └── constraints/
│
├── memory/
│   ├── runtime/
│   ├── scholarly/
│   ├── operational/
│   └── promotion/
│
├── scholar/
│   ├── review/
│   ├── feedback/
│   └── profiles/
│
├── evaluation/
│   ├── benchmarks/
│   ├── golden/
│   ├── regression/
│   └── scoring/
│
├── infrastructure/
│   ├── database/
│   ├── vector/
│   ├── filesystem/
│   └── model/
│
└── ui/
    └── workbench/
```

This structure keeps the **domain architecture independent from whichever model or database we use**.

---

# 4. Core domain objects

The most important object is not `ChatMessage`.

It is:

```java
ResearchCase
```

For example:

```java
public final class ResearchCase {

    private CaseId id;

    private ResearchQuestion question;

    private ResearchContext context;

    private ResearchStatus status;

    private ResearchPlan plan;

    private EvidenceGraph evidenceGraph;

    private List<ResearchFinding> findings;

    private List<ScholarFeedbackEvent> feedback;

    private Revision currentRevision;
}
```

---

# 5. ResearchQuestion

```java
public record ResearchQuestion(
    String originalText,
    String normalizedQuestion,
    List<String> subQuestions
) {}
```

For example:

```text
Original:
"Bagaimana hukum transaksi X?"

Subquestions:

1. Apa hakikat transaksi X?
2. Apakah terdapat gharar?
3. Apakah ada nash yang relevan?
4. Bagaimana fuqaha membahas akad yang serupa?
5. Apakah terdapat qiyas?
6. Apa 'illah yang relevan?
7. Apakah terdapat ikhtilaf?
8. Bagaimana penerapannya terhadap bentuk modern?
```

This decomposition is one of the first places where the reasoning model provides enormous value.

---

# 6. ResearchContext

```java
public record ResearchContext(
    String madhhab,
    String usulFramework,
    List<String> geographicContext,
    List<String> historicalContext,
    List<String> sourceConstraints,
    List<String> languagePreferences
) {}
```

This prevents a huge class of errors.

For example:

```text
Question
+
Hanafi context
```

should not automatically trigger a generic cross-madhhab synthesis.

The planner knows:

```text
PRIMARY:
Hanafi corpus

SECONDARY:
other madhahib for comparison
```

---

# 7. Specialist interface

Every specialist should implement the same fundamental contract.

```java
public interface Specialist {

    SpecialistType type();

    ResearchCapability capability();

    SpecialistResult research(
        SpecialistRequest request,
        ResearchContext context
    );
}
```

Then:

```text
QuranSpecialist
HadithSpecialist
TafsirSpecialist
SanadSpecialist
ArabicSpecialist
BalaghahSpecialist
FiqhSpecialist
MadhhabSpecialist
UsulSpecialist
MaqasidSpecialist
ComparisonSpecialist
```

All can be orchestrated uniformly.

---

# 8. Specialist result

Do not let specialists return only prose.

Bad:

```text
"The verse appears relevant because..."
```

Good:

```java
public record SpecialistResult(
    SpecialistType specialist,
    List<Evidence> evidence,
    List<Claim> claims,
    List<Argument> arguments,
    List<Uncertainty> uncertainties,
    List<ResearchRequest> followUps
) {}
```

The prose can be generated later.

This is one of the most important implementation principles in Kallam.

---

# 9. Evidence object

```java
public record Evidence(
    EvidenceId id,
    EvidenceType type,
    SourceReference source,
    String originalText,
    String normalizedText,
    EvidenceProvenance provenance,
    VerificationStatus verification
) {}
```

For Qur'an:

```text
source:
Quran
surah:
Al-Baqarah
ayah:
275
```

For hadith:

```text
collection
book
chapter
hadith number
variant
isnad
```

For a fiqh book:

```text
author
work
edition
volume
page
section
```

The source-specific metadata can be represented through typed subclasses or structured metadata.

---

# 10. Evidence Graph

This becomes the backbone.

```text
Evidence
   │
   ├── SUPPORTS → Claim
   ├── CONTRADICTS → Claim
   ├── EXPLAINS → Claim
   └── ATTRIBUTED_TO → Scholar

Claim
   │
   ├── SUPPORTS → Argument
   └── USED_IN → Conclusion

Argument
   │
   ├── BASED_ON → UsulPrinciple
   ├── USES → Qiyas
   └── CHALLENGED_BY → CounterArgument

Conclusion
   │
   └── QUALIFIED_BY → Uncertainty
```

This allows the system to reason structurally.

---

# 11. The reasoning model should not receive the entire corpus

This is especially important for your local inference requirement.

The pipeline should be:

```text
10,000 documents
       │
       ▼
retrieval
       │
       ▼
200 candidates
       │
       ▼
reranking
       │
       ▼
30 evidence passages
       │
       ▼
specialist filtering
       │
       ▼
10–15 evidence units
       │
       ▼
reasoning model
```

The reasoning model sees **research evidence**, not the library.

That drastically reduces token consumption.

---

# 12. Separate cheap models from expensive reasoning

A local Kallam deployment could have:

```text
Model A
small
────────────────
classification
query rewriting
metadata extraction
language detection
routing

Model B
medium
────────────────
retrieval analysis
summarization
comparison

Model C
strong reasoning
────────────────
usul analysis
complex synthesis
critic
```

Not every operation deserves the largest model.

---

# 13. The orchestrator

The orchestrator should be deterministic wherever possible.

```java
public interface ResearchOrchestrator {

    ResearchResult execute(
        ResearchCase researchCase
    );
}
```

Internally:

```java
public ResearchResult execute(ResearchCase c) {

    var decomposition = decomposer.analyze(c);

    var plan = planner.createPlan(
        c,
        decomposition
    );

    var results = specialistRunner.execute(plan);

    var graph = graphBuilder.build(results);

    var analysis = reasoningEngine.analyze(
        graph,
        c.context()
    );

    var critique = critic.evaluate(
        graph,
        analysis
    );

    return synthesizer.build(
        graph,
        analysis,
        critique
    );
}
```

This is intentionally simple.

We do not want an uncontrolled swarm of agents.

---

# 14. Agent ≠ autonomous LLM

This distinction will save us a lot of complexity.

A Kallam "agent" should often be:

```text
Agent
=
Prompt
+
Tools
+
Domain rules
+
Output schema
+
Budget
+
Validation
```

Not:

```text
Agent
=
LLM talking to itself indefinitely
```

For local inference, bounded agents are much better.

---

# 15. Token budget controller

Now we implement one of your original requirements directly.

```java
public record TokenBudget(
    int total,
    int retrieval,
    int specialist,
    int reasoning,
    int critic,
    int synthesis
) {}
```

Example:

```text
Total: 32k

Retrieval:     4k
Specialists:   10k
Reasoning:     8k
Critic:        5k
Synthesis:     5k
```

The orchestrator can refuse additional work when the budget is exhausted.

---

# 16. Dynamic budget allocation

Even better:

```text
Simple question
      ↓
small budget

Complex ikhtilaf
      ↓
large budget

Missing evidence
      ↓
allocate budget to retrieval

Strong evidence but difficult reasoning
      ↓
allocate budget to reasoning
```

So:

```java
TokenBudget budget =
    budgetManager.allocate(
        caseComplexity,
        evidenceQuality,
        uncertainty
    );
```

This is much more efficient than giving every case the same budget.

---

# 17. Critic should be independent

Do not use:

```text
same prompt
same context
same reasoning
```

for both generator and critic.

At minimum:

```text
Generator
   ↓
structured analysis

Critic
   ↓
checks:
- evidence
- logic
- attribution
- citation
- contradiction
- uncertainty
```

Ideally, eventually:

```text
different model
```

or at least:

```text
different prompt + independent evidence retrieval
```

---

# 18. Final output is a dossier

The scholar should not receive only a paragraph.

The output might be:

```text
══════════════════════════════════
RESEARCH DOSSIER
CASE-000142
══════════════════════════════════

1. Question

2. Mas'alah decomposition

3. Executive summary

4. Primary evidences
   ├── Qur'an
   ├── Hadith
   └── Other primary texts

5. Tafsir analysis

6. Linguistic analysis

7. Fiqh positions

8. Madhhab comparison

9. Usul analysis

10. Qiyas analysis

11. Maqasid considerations

12. Counterarguments

13. Areas of disagreement

14. Evidence weaknesses

15. Preliminary synthesis

16. Unresolved questions

17. Full citations

18. Research provenance
══════════════════════════════════
```

And at the very top:

```text
RESEARCH STATUS

Evidence:       Strong
Agreement:      Disputed
Primary sources: Verified
Critical gaps:   2
Scholar review:  Pending
```

---

# 19. The system must explicitly distinguish conclusion types

For example:

```text
DIRECT_TEXTUAL
```

```text
SCHOLARLY_REPORTED
```

```text
ANALOGICAL
```

```text
INFERENTIAL
```

```text
COMPARATIVE
```

```text
PRELIMINARY_SYNTHESIS
```

This is extremely important.

The user should be able to see:

> "This is directly stated in source X."

versus:

> "This is Kallam's analytical synthesis."

Those are fundamentally different epistemic categories.

---

# 20. The most important v1.0 UI

I would make the main screen something like:

```text
┌──────────────────────────────────────────────┐
│ KALLAM                                       │
│ Research Case: CASE-000142                   │
├──────────────────────────────────────────────┤
│ QUESTION                                     │
│ ...                                          │
├──────────────────────────────────────────────┤
│ RESEARCH STATUS                              │
│                                              │
│ Evidence       █████████░  91%               │
│ Verification   ████████░░  82%               │
│ Agreement      DISPUTED                      │
│ Critical gaps  2                             │
├──────────────────────────────────────────────┤
│ FINDINGS                                     │
│                                              │
│ C01  ✓ Supported                             │
│ C02  ⚠ Disputed                              │
│ C03  ? Insufficient evidence                 │
├──────────────────────────────────────────────┤
│ EVIDENCE                                     │
│                                              │
│ Quran      7                                  │
│ Hadith     14                                 │
│ Tafsir     11                                 │
│ Fiqh       23                                 │
│ Usul        8                                 │
├──────────────────────────────────────────────┤
│ [Review] [Request Research] [Compare]        │
└──────────────────────────────────────────────┘
```

Clicking C02 should open the evidence graph.

---

# 21. Evidence graph UI

Something like:

```text
                E17
                 │
                 ▼
              CLAIM C02
             /         \
            /           \
          E21           E33
           │             │
           ▼             ▼
      POSITION A     POSITION B
           \             /
            \           /
             ▼         ▼
               ARGUMENT
                  │
                  ▼
              SYNTHESIS
```

The scholar can inspect every edge.

This is where Kallam becomes significantly more useful than a normal RAG chatbot.

---

# 22. Scholar interaction

The scholar should be able to say:

```text
Reject claim
```

or:

```text
Correct citation
```

or:

```text
This is not the correct 'illah.
```

or:

```text
Search Ibn X's discussion.
```

or simply:

```text
Explore this issue further.
```

These become structured events.

---

# 23. v1.0 deployment architecture

For local deployment:

```text
┌─────────────────────────────────────┐
│              KALLAM                 │
│                                     │
│  UI                                 │
│   │                                 │
│  API                                │
│   │                                 │
│  Orchestrator                       │
│   │                                 │
│  ┌───────┬────────┬─────────────┐   │
│  │       │        │             │   │
│  Model  Retrieval Memory      Graph │
│  │       │        │             │   │
│  └───────┴────────┴─────────────┘   │
│                                     │
│ Local Database + Corpus             │
└─────────────────────────────────────┘
```

One machine can run the entire system initially.

No Kubernetes.

No distributed event bus.

No dozens of services.

No cloud dependency.

---

# 24. Recommended technology philosophy

The specific technology can change, but the architecture should remain:

```text
Application
    ↓
Interfaces
    ↓
Implementations
```

For example:

```java
interface LanguageModel
interface EmbeddingModel
interface Retriever
interface VectorStore
interface DocumentStore
interface ScholarMemory
```

Then:

```text
Ollama / llama.cpp / vLLM
```

can be swapped later without changing the research domain.

Likewise:

```text
SQLite
→ PostgreSQL
```

or:

```text
FAISS
→ Qdrant
```

without redesigning Kallam.

---

# 25. The first executable vertical slice

This is where I would **not** implement all specialists yet.

We build one complete path:

```text
Question
   ↓
Quran retrieval
   ↓
Hadith retrieval
   ↓
Evidence normalization
   ↓
Evidence graph
   ↓
Reasoning
   ↓
Critic
   ↓
Citation report
```

If that works end-to-end, then add:

```text
Tafsir
   ↓
Fiqh
   ↓
Madhhab
   ↓
Usul
   ↓
Sanad
   ↓
Arabic
```

This is much safer than implementing 15 incomplete agents first.

---

# 26. v1.0 development order

I recommend this exact sequence:

### Phase A — Kernel

```text
Domain model
IDs
ResearchCase
Evidence
Claim
Argument
Citation
Graph
```

### Phase B — Model abstraction

```text
LanguageModel
EmbeddingModel
StructuredOutput
TokenBudget
```

### Phase C — Retrieval

```text
Corpus ingestion
metadata
chunking
FTS
vector retrieval
reranking
```

### Phase D — First specialists

```text
Quran
Hadith
Tafsir
Fiqh
```

### Phase E — Reasoning

```text
planner
reasoning
critic
synthesis
```

### Phase F — Provenance

```text
citation
source verification
attribution
audit trail
```

### Phase G — Scholar Workbench

```text
review
correction
research request
revision
```

### Phase H — Memory

```text
feedback
promotion
retrieval
runtime adaptation
```

### Phase I — Evaluation

```text
golden cases
regression
benchmarks
scholar evaluation
```

---

# 27. Then v1.1 becomes genuinely interesting

Once v1.0 is stable, we can begin **adaptive specialization**.

Instead of manually defining:

```text
always call 8 specialists
```

the planner learns:

```text
Question type X
   ↓
historically useful specialists:
Hadith
Usul
Madhhab

Question type Y
   ↓
Arabic
Balaghah
Tafsir
```

And eventually:

```text
Research Planner
       ↓
learns which research path
produces the strongest results
```

That is where reinforcement learning / preference optimization starts becoming genuinely useful.

---

# 28. The long-term architecture

Ultimately Kallam becomes:

```text
                         ┌───────────────┐
                         │    ʿĀLIM      │
                         └───────┬───────┘
                                 │
                                 ▼
                         RESEARCH CASE
                                 │
                                 ▼
                       INTELLIGENT PLANNER
                                 │
             ┌───────────────────┼───────────────────┐
             ▼                   ▼                   ▼
         DISCOVERY           SPECIALISTS          MEMORY
             │                   │                   │
             └───────────────────┼───────────────────┘
                                 ▼
                          EVIDENCE GRAPH
                                 │
                                 ▼
                          REASONING ENGINE
                                 │
                                 ▼
                              CRITIC
                                 │
                                 ▼
                       PROVENANCE ENGINE
                                 │
                                 ▼
                         SCHOLAR REVIEW
                                 │
                  ┌──────────────┼──────────────┐
                  ▼              ▼              ▼
              REVISION        MEMORY        EVALUATION
                  │              │              │
                  └──────────────┼──────────────┘
                                 ▼
                       CONTINUAL IMPROVEMENT
                                 │
                    ┌────────────┴────────────┐
                    ▼                         ▼
              Runtime learning          Model learning
                                          (optional)
```

And that gives us a very important strategic conclusion:

**The model is only one component of Kallam.**

The real intelligence emerges from:

> **model + corpus + retrieval + specialists + evidence graph + reasoning + critic + provenance + scholar feedback + memory + evaluation.**

That architecture is much more achievable with current local reasoning models than attempting to train a single enormous "Islamic reasoning model."

---

## Next implementation milestone

The next step should therefore be **Kallam v1.0-DEV.1: the actual repository and executable kernel**.

I would start with only these concrete components:

```text
kallam-core
 ├── ResearchCase
 ├── ResearchQuestion
 ├── ResearchContext
 ├── Evidence
 ├── Claim
 ├── Argument
 ├── Citation
 ├── ResearchFinding
 └── EvidenceGraph

kallam-model
 ├── LanguageModel
 ├── EmbeddingModel
 ├── StructuredOutput
 └── TokenBudget

kallam-research
 ├── ResearchPlanner
 ├── ResearchOrchestrator
 └── ResearchWorkflow

kallam-specialists
 ├── QuranSpecialist
 ├── HadithSpecialist
 ├── TafsirSpecialist
 └── FiqhSpecialist

kallam-evidence
 ├── Retriever
 ├── CitationVerifier
 └── ProvenanceService

kallam-reasoning
 ├── ReasoningEngine
 ├── Critic
 └── Synthesizer

kallam-storage
 ├── DocumentStore
 ├── EvidenceStore
 ├── GraphStore
 └── MemoryStore
```

Then we can build the **first runnable end-to-end case**, rather than adding more architecture. That will be the point where Kallam moves from a design document into an actual research engine.


Great. We now move from architecture into the **first actual implementation milestone**.

# Kallam v1.0-DEV.1 — Executable Research Kernel

The objective is deliberately small:

> **Get one complete research case running locally from question → retrieval → evidence → reasoning → critic → structured research dossier.**

We should **not** build all specialists, UI, continual learning, or fine-tuning yet.

The first vertical slice must work end-to-end.

---

# 1. DEV.1 target

```text
User question
     │
     ▼
ResearchCase
     │
     ▼
Question decomposition
     │
     ▼
Research planner
     │
     ├──── Quran retrieval
     ├──── Hadith retrieval
     ├──── Tafsir retrieval
     └──── Fiqh retrieval
              │
              ▼
        Evidence objects
              │
              ▼
        Evidence Graph
              │
              ▼
       Reasoning Engine
              │
              ▼
            Critic
              │
              ▼
       Structured Dossier
```

For the first implementation, **everything should be locally executable**.

---

# 2. Start with a modular monolith

I would use:

```text
Java 21+
Spring Boot
SQLite
FTS5
Local LLM runtime
JSON/JSONL corpus
```

But the architecture should hide those choices behind interfaces.

So the dependency direction is:

```text
domain
   ↑
application
   ↑
infrastructure
   ↑
API / CLI
```

Never:

```text
ResearchCase → SQLite
```

Instead:

```text
ResearchCase
     │
     ▼
EvidenceRepository
     │
     ▼
SQLiteEvidenceRepository
```

This lets us replace SQLite later without touching the domain.

---

# 3. Repository

Start with:

```text
kallam/
│
├── README.md
├── LICENSE
├── pom.xml
│
├── kallam-core/
├── kallam-model/
├── kallam-research/
├── kallam-evidence/
├── kallam-reasoning/
├── kallam-specialists/
├── kallam-storage/
│
├── kallam-api/
│
├── data/
│   ├── quran/
│   ├── hadith/
│   ├── tafsir/
│   └── fiqh/
│
├── prompts/
│
├── evaluation/
│
└── scripts/
```

This is enough for the first working version.

---

# 4. `kallam-core`

This module contains **no LLM code**.

That is intentional.

## ResearchCase

```java
package org.kallam.core.research;

public final class ResearchCase {

    private final String id;
    private final ResearchQuestion question;
    private final ResearchContext context;

    private ResearchStatus status;

    public ResearchCase(
            String id,
            ResearchQuestion question,
            ResearchContext context
    ) {
        this.id = id;
        this.question = question;
        this.context = context;
        this.status = ResearchStatus.CREATED;
    }

    public String id() {
        return id;
    }

    public ResearchQuestion question() {
        return question;
    }

    public ResearchContext context() {
        return context;
    }

    public ResearchStatus status() {
        return status;
    }

    public void changeStatus(ResearchStatus status) {
        this.status = status;
    }
}
```

---

# 5. Research question

```java
package org.kallam.core.research;

import java.util.List;

public record ResearchQuestion(
        String originalText,
        String normalizedText,
        List<String> subQuestions
) {}
```

We deliberately keep both:

```text
originalText
normalizedText
```

because the original wording must never disappear.

---

# 6. Research context

```java
package org.kallam.core.research;

import java.util.List;

public record ResearchContext(
        String madhhab,
        String usulFramework,
        List<String> languages,
        List<String> sourceConstraints
) {}
```

For example:

```json
{
  "madhhab": "hanafi",
  "usulFramework": "hanafi",
  "languages": ["ar", "id"],
  "sourceConstraints": []
}
```

The actual system should eventually support richer methodology profiles.

---

# 7. Research status

```java
public enum ResearchStatus {

    CREATED,
    DECOMPOSING,
    PLANNING,
    RESEARCHING,
    ANALYZING,
    CRITIQUING,
    READY_FOR_REVIEW,
    SCHOLAR_REVIEW,
    REVISED,
    ACCEPTED
}
```

This gives us an explicit workflow state instead of relying on prose.

---

# 8. Evidence

This is one of the most important objects in Kallam.

```java
public record Evidence(
        String id,
        EvidenceType type,
        SourceReference source,
        String originalText,
        String normalizedText,
        VerificationStatus verification
) {}
```

Types:

```java
public enum EvidenceType {

    QURAN,
    HADITH,
    TAFSIR,
    FIQH,
    USUL,
    ARABIC,
    BALAGHA,
    SCHOLAR_STATEMENT,
    HISTORICAL,
    CONTEMPORARY
}
```

---

# 9. SourceReference

Do not represent citations as arbitrary strings.

```java
public record SourceReference(
        String sourceId,
        String author,
        String work,
        String edition,
        String volume,
        String page,
        String section,
        String locator
) {}
```

Later we can specialize this for:

```text
QuranReference
HadithReference
BookReference
ManuscriptReference
WebReference
```

But the core abstraction remains stable.

---

# 10. Verification status

```java
public enum VerificationStatus {

    UNVERIFIED,
    RETRIEVED,
    VERIFIED,
    DISPUTED,
    REJECTED
}
```

This is critical.

The system must never confuse:

```text
found
```

with:

```text
verified
```

---

# 11. Claims

The reasoning system should operate on claims.

```java
public record Claim(
        String id,
        String statement,
        ClaimType type
) {}
```

For example:

```text
C001:
"The transaction contains gharar."

C002:
"Scholar X classified this type of transaction as..."

C003:
"The relevant legal cause is..."
```

Types:

```java
public enum ClaimType {

    TEXTUAL,
    HISTORICAL,
    SCHOLAR_POSITION,
    LINGUISTIC,
    USUL,
    ANALOGICAL,
    SYNTHESIS
}
```

---

# 12. Arguments

```java
public record Argument(
        String id,
        String conclusion,
        List<String> premiseClaimIds,
        ArgumentType type
) {}
```

Types:

```java
public enum ArgumentType {

    TEXTUAL,
    LINGUISTIC,
    USUL,
    QIYAS,
    MASLAHA,
    COMPARATIVE,
    SYNTHESIS
}
```

This gives us a machine-readable reasoning structure.

---

# 13. Evidence Graph

The first version doesn't need a sophisticated graph database.

Use an in-memory graph first.

```java
public final class EvidenceGraph {

    private final Map<String, GraphNode> nodes = new HashMap<>();
    private final List<GraphEdge> edges = new ArrayList<>();

    public void addNode(GraphNode node) {
        nodes.put(node.id(), node);
    }

    public void addEdge(GraphEdge edge) {
        edges.add(edge);
    }

    public Collection<GraphNode> nodes() {
        return nodes.values();
    }

    public List<GraphEdge> edges() {
        return edges;
    }
}
```

---

# 14. Graph edges

```java
public enum RelationType {

    SUPPORTS,
    CONTRADICTS,
    EXPLAINS,
    ATTRIBUTED_TO,
    DERIVED_FROM,
    QUALIFIES,
    APPLIES_TO,
    REJECTED_BY
}
```

Example:

```text
E001
 │
 └── SUPPORTS → C001
```

Or:

```text
C001
 │
 └── CONTRADICTS → C009
```

---

# 15. Model abstraction

Now we leave the domain layer.

```java
public interface LanguageModel {

    ModelResponse generate(
            ModelRequest request
    );
}
```

The application should know nothing about whether the model is:

```text
llama.cpp
Ollama
vLLM
Transformers
MLX
```

---

# 16. Structured output

This is extremely important for local inference.

Do not ask:

```text
"Analyze this."
```

and parse arbitrary prose.

Instead:

```java
public interface StructuredModel {

    <T> T generate(
            ModelRequest request,
            Class<T> outputType
    );
}
```

For example:

```java
public record DecompositionResult(
        List<String> subQuestions,
        List<String> requiredDomains,
        List<String> uncertainties
) {}
```

The model must produce this schema.

---

# 17. First planner

```java
public interface ResearchPlanner {

    ResearchPlan createPlan(
            ResearchCase researchCase,
            DecompositionResult decomposition
    );
}
```

A plan:

```java
public record ResearchPlan(
        List<ResearchTask> tasks
) {}
```

And:

```java
public record ResearchTask(
        String id,
        SpecialistType specialist,
        String question,
        int priority
) {}
```

---

# 18. First specialists

For DEV.1, implement only:

```text
QuranSpecialist
HadithSpecialist
TafsirSpecialist
FiqhSpecialist
```

Not because the others aren't important.

Because we need to establish the specialist contract first.

```java
public interface Specialist {

    SpecialistType type();

    SpecialistResult execute(
            ResearchTask task,
            ResearchContext context
    );
}
```

---

# 19. Specialist results

```java
public record SpecialistResult(
        SpecialistType specialist,
        List<Evidence> evidence,
        List<Claim> claims,
        List<Argument> arguments,
        List<ResearchGap> gaps
) {}
```

Notice something important:

**Specialists don't produce the final answer.**

They produce research material.

That distinction should remain throughout Kallam.

---

# 20. Retrieval interface

```java
public interface Retriever {

    List<RetrievedDocument> search(
            SearchQuery query,
            RetrievalFilter filter,
            int limit
    );
}
```

A search query:

```java
public record SearchQuery(
        String text,
        List<String> keywords
) {}
```

Filter:

```java
public record RetrievalFilter(
        EvidenceType type,
        String author,
        String work,
        String language
) {}
```

---

# 21. Retrieval pipeline

For local inference:

```text
Query
 │
 ▼
Keyword retrieval
 │
 ▼
FTS
 │
 ▼
Vector retrieval
 │
 ▼
Merge
 │
 ▼
Rerank
 │
 ▼
Top K
```

Do not make the LLM perform retrieval itself.

---

# 22. The first database

SQLite is sufficient.

Tables:

```text
research_cases
research_questions
research_tasks
sources
documents
evidence
claims
arguments
graph_edges
citations
feedback_events
memory_items
evaluation_runs
```

The schema should remain boring.

Boring is good here.

---

# 23. Document ingestion

The first ingestion pipeline:

```text
Raw file
   │
   ▼
Parser
   │
   ▼
Document
   │
   ▼
Metadata extraction
   │
   ▼
Chunking
   │
   ▼
FTS index
   │
   ▼
Embedding
   │
   ▼
Vector index
```

Every chunk needs immutable provenance:

```text
document_id
page
volume
section
paragraph
offset
language
edition
```

This is how we later get reliable citations.

---

# 24. The first real end-to-end execution

Conceptually:

```java
ResearchCase researchCase =
        caseService.create(
            "Apa hukum transaksi X?",
            context
        );

var decomposition =
        decomposer.decompose(researchCase);

var plan =
        planner.createPlan(
            researchCase,
            decomposition
        );

var specialistResults =
        specialistRunner.execute(plan);

var graph =
        graphBuilder.build(
            specialistResults
        );

var reasoning =
        reasoningEngine.analyze(
            researchCase,
            graph
        );

var critique =
        critic.evaluate(
            graph,
            reasoning
        );

var dossier =
        synthesizer.synthesize(
            researchCase,
            graph,
            reasoning,
            critique
        );
```

That is the first **Kallam research loop**.

---

# 25. The output should be JSON first

Before making beautiful UI, make the output deterministic.

Something like:

```json
{
  "case_id": "CASE-000001",
  "question": "...",

  "claims": [
    {
      "id": "C001",
      "statement": "...",
      "status": "SUPPORTED"
    }
  ],

  "evidence": [
    {
      "id": "E001",
      "type": "QURAN",
      "source": "...",
      "verification": "VERIFIED"
    }
  ],

  "arguments": [],

  "critic": {
    "unsupported_inferences": [],
    "citation_issues": [],
    "contradictions": []
  },

  "synthesis": {
    "text": "...",
    "status": "PRELIMINARY_SYNTHESIS"
  }
}
```

This becomes the contract between backend and future UI.

---

# 26. One crucial addition: `ResearchGap`

The system must be allowed to stop.

```java
public record ResearchGap(
        String id,
        String description,
        GapType type,
        boolean blocksConclusion
) {}
```

For example:

```text
G001

Missing:
Primary source verification.

Type:
PRIMARY_SOURCE_MISSING

blocksConclusion:
true
```

Then the system can report:

> **Insufficient evidence for synthesis.**

That is much better than hallucinating a conclusion.

---

# 27. Research loop with gaps

Now the orchestrator becomes:

```text
Research
   │
   ▼
Evidence
   │
   ▼
Reasoning
   │
   ▼
Critic
   │
   ├── Critical gap?
   │       │
   │       └── YES
   │            ↓
   │       Research again
   │
   └── NO
        ↓
     Synthesis
```

This is the beginning of a genuine agentic research loop.

But it is **bounded**.

For example:

```text
max_iterations = 3
max_tokens = 32k
max_specialist_calls = 12
```

That keeps local inference predictable.

---

# 28. The first implementation should therefore be small

Our DEV.1 acceptance test is simply:

```text
Given a research question

Kallam can:

1. create a case;
2. decompose it;
3. create research tasks;
4. retrieve evidence;
5. create evidence objects;
6. construct a graph;
7. reason over the graph;
8. critique the reasoning;
9. identify gaps;
10. produce a structured dossier;
11. preserve citations/provenance.
```

If those 11 things work, **we have a real Kallam engine**.

Everything else becomes incremental.

---

# 29. Then DEV.2

After this kernel works, the next implementation milestone should be:

## **v1.0-DEV.2 — Corpus & Evidence Infrastructure**

We will implement the actual knowledge layer:

```text
Qur'an corpus
       │
       ├── Arabic text
       ├── ayah metadata
       ├── surah metadata
       └── translations

Hadith corpus
       │
       ├── collection
       ├── book
       ├── chapter
       ├── hadith
       ├── isnad
       └── variants

Tafsir corpus
       │
       ├── author
       ├── work
       ├── edition
       ├── volume
       ├── page
       └── passage

Fiqh corpus
       │
       ├── madhhab
       ├── scholar
       ├── work
       ├── chapter
       └── ruling passage
```

And most importantly:

```text
RAW TEXT
   ↓
NORMALIZED TEXT
   ↓
IMMUTABLE SOURCE ID
   ↓
CHUNK
   ↓
INDEX
   ↓
RETRIEVAL
   ↓
EVIDENCE
   ↓
VERIFIED CITATION
```

That is where we can start tackling the genuinely difficult part of Kallam: **building a high-integrity Islamic scholarly corpus with citation-level provenance**, rather than merely putting books into a vector database.



Absolutely. We now move to **v1.0-DEV.2 — Corpus & Evidence Infrastructure**.

This is arguably the most important implementation milestone after the kernel because **Kallam's reasoning quality can never exceed the integrity of the evidence layer**.

# Kallam v1.0-DEV.2

## Corpus, Provenance & Evidence Infrastructure

The design principle is:

> **A retrieved passage is not yet evidence. Evidence is a retrieved passage whose identity, location, source, and provenance can be established.**

So we build this layer first:

```text
                 SOURCE
                   │
                   ▼
              INGESTION
                   │
                   ▼
             NORMALIZATION
                   │
                   ▼
              DOCUMENT ID
                   │
                   ▼
                CHUNKS
                   │
          ┌────────┴────────┐
          ▼                 ▼
       FTS INDEX        VECTOR INDEX
          │                 │
          └────────┬────────┘
                   ▼
                RETRIEVAL
                   │
                   ▼
             RERANKING
                   │
                   ▼
              EVIDENCE
                   │
                   ▼
            VERIFICATION
                   │
                   ▼
              CITATION
```

---

# 1. The first major architectural decision

Do **not** create one giant:

```text
IslamicKnowledgeVectorDB
```

Instead, the corpus has explicit source families.

```text
Corpus
│
├── Quran
├── Hadith
├── Tafsir
├── Fiqh
├── Usul
├── Arabic
├── Balaghah
├── Aqidah
├── Tarikh
└── Contemporary
```

Why?

Because retrieval semantics are different.

Searching:

> "all occurrences of the word"

is different from:

> "find hadith discussing this ruling"

which is different from:

> "find Hanafi discussion of this mas'alah."

The metadata must allow Kallam to understand those distinctions.

---

# 2. Source hierarchy

Every source gets a classification.

```text
PRIMARY
├── Quran
└── Hadith

CLASSICAL SECONDARY
├── Tafsir
├── Fiqh
├── Usul
├── Sharh
└── Other sciences

TERTIARY
├── Modern books
├── Academic research
└── Articles

REFERENCE
├── Encyclopedic
└── Metadata/reference databases
```

This doesn't mean "primary = automatically more correct."

It means:

> **Kallam knows what kind of source it is using.**

That distinction is essential for reasoning.

---

# 3. Source identity

Every work needs a stable identifier.

For example:

```text
source_id:
TAFSIR:001234
```

Then:

```text
author_id:
SCHOLAR:0042
```

And:

```text
work_id:
WORK:00891
```

The source graph becomes:

```text
Scholar
   │
   └── AUTHORED → Work
                    │
                    └── EDITION
                           │
                           └── VOLUME
                                  │
                                  └── PAGE
                                         │
                                         └── PASSAGE
```

This is far stronger than putting:

```text
"page 123"
```

inside a text chunk.

---

# 4. Source model

```java
public record Source(
        String id,
        SourceType type,
        String title,
        String authorId,
        String language,
        SourceAuthority authority,
        SourceStatus status
) {}
```

For example:

```json
{
  "id": "WORK:00042",
  "type": "TAFSIR",
  "title": "...",
  "authorId": "SCHOLAR:0012",
  "language": "ar",
  "authority": "CLASSICAL",
  "status": "ACTIVE"
}
```

---

# 5. Scholar identity

This deserves its own table.

```java
public record Scholar(
        String id,
        String canonicalName,
        List<String> aliases,
        String deathYear,
        List<String> disciplines
) {}
```

Because the same scholar can appear as:

```text
ابن تيمية
ابن تيميه
Ibn Taymiyya
Ibn Taimiyyah
Ibn Taymiyyah
```

We don't want five scholars in the database.

---

# 6. Work identity

```java
public record Work(
        String id,
        String scholarId,
        String canonicalTitle,
        List<String> aliases,
        String genre,
        String language
) {}
```

For example:

```text
Scholar
   ↓
Work
   ↓
Edition
   ↓
Volume
   ↓
Page
   ↓
Passage
```

This enables precise provenance.

---

# 7. Edition is important

Two editions can have:

```text
different pagination
different numbering
different editorial notes
different omissions
```

Therefore:

```java
public record Edition(
        String id,
        String workId,
        String publisher,
        String year,
        String editionNumber,
        String isbn,
        String checksum
) {}
```

The checksum is useful because it lets us identify the exact digital artifact.

---

# 8. Document model

```java
public record Document(
        String id,
        String editionId,
        String format,
        String checksum,
        String language,
        DocumentStatus status
) {}
```

For example:

```text
document_id:
DOC:98f3...

edition:
EDITION:00021

checksum:
sha256:...
```

This makes ingestion reproducible.

---

# 9. Passage model

The actual searchable unit should be a **Passage**, not simply a chunk.

```java
public record Passage(
        String id,
        String documentId,
        String text,
        PassageLocation location,
        String parentId
) {}
```

Location:

```java
public record PassageLocation(
        String volume,
        String page,
        String chapter,
        String section,
        Integer paragraph,
        Integer startOffset,
        Integer endOffset
) {}
```

---

# 10. Why `parentId`?

Because documents have hierarchy.

For example:

```text
Book
 └── Chapter
      └── Section
           └── Paragraph
                └── Passage
```

If we flatten everything, retrieval loses context.

Instead:

```text
P001
parent = SECTION-42
```

and:

```text
SECTION-42
parent = CHAPTER-8
```

Now Kallam can reconstruct context when necessary.

---

# 11. Chunking strategy

This is where many RAG systems fail.

Do **not** blindly do:

```text
every 500 tokens
```

Instead:

```text
semantic structure
       ↓
paragraph
       ↓
section
       ↓
argument boundary
       ↓
chunk
```

For a fiqh text:

```text
Chapter
  ↓
Mas'alah
  ↓
Position
  ↓
Evidence
  ↓
Explanation
```

A chunk should try to preserve that structure.

---

# 12. Contextual chunks

Suppose the actual passage says:

> "This is the relied-upon position."

That is useless without context.

The chunk should carry:

```text
Book
Chapter
Section
Previous heading
Current passage
```

So the indexed representation can be:

```text
[WORK]
...

[CHAPTER]
...

[SECTION]
...

[PASSAGE]
"This is the relied-upon position..."
```

But the **citation still points to the original passage**, not this augmented text.

---

# 13. Quran is different

The Qur'an should not be chunked like ordinary books.

Use:

```text
Quran
 └── Surah
      └── Ayah
           ├── Arabic text
           ├── Uthmani text
           ├── normalized search text
           ├── juz
           ├── hizb
           └── metadata
```

Primary identity:

```text
QURAN:2:275
```

This is far superior to:

```text
document_129_chunk_84
```

---

# 14. Hadith is different again

Use:

```text
Collection
   │
   └── Book
        │
        └── Chapter
             │
             └── Hadith
                  ├── Matn
                  ├── Isnād
                  ├── Narrators
                  ├── Variants
                  └── Metadata
```

Identity:

```text
HADITH:<collection>:<stable-id>
```

The exact identifier strategy can accommodate collections where numbering differs between editions.

---

# 15. Hadith variants

Do not merge variants into one text.

Instead:

```text
Hadith Family
       │
       ├── Variant A
       ├── Variant B
       ├── Variant C
       └── Variant D
```

Then:

```text
Variant
   │
   ├── collection
   ├── sanad
   └── matn
```

This will become extremely important later for the sanad specialist.

---

# 16. Search architecture

Use two retrieval systems.

### Lexical

```text
SQLite FTS5
```

### Semantic

```text
vector index
```

Then:

```text
                Query
                  │
         ┌────────┴────────┐
         ▼                 ▼
       FTS5              Vector
         │                 │
         └────────┬────────┘
                  ▼
              Merge/RRF
                  │
                  ▼
               Rerank
```

This hybrid approach is particularly useful for Arabic.

---

# 17. Arabic retrieval

This requires special handling.

We should retain at least:

```text
original_text
search_text
```

Potential normalization:

```text
remove tashkeel
normalize alef variants
normalize ya/alif maqsurah where appropriate
normalize whitespace
```

But:

> **Never replace the original text with normalized text.**

Original text is immutable.

Normalization is only a search representation.

---

# 18. Multiple representations

A passage can therefore have:

```text
original:
وَأَحَلَّ اللَّهُ الْبَيْعَ

search:
واحل الله البيع

transliteration:
...

translation:
...
```

Each is separately identified.

This will also help multilingual retrieval later.

---

# 19. Retrieval result

The retriever should not return raw strings.

```java
public record RetrievedDocument(
        String passageId,
        double lexicalScore,
        double semanticScore,
        double finalScore,
        Passage passage
) {}
```

Now the orchestrator knows:

```text
why did this passage rank highly?
```

---

# 20. Reranking

Initial implementation:

```text
FTS score
+
vector score
+
metadata score
```

Example:

```text
finalScore =
    0.35 * lexical
  + 0.35 * semantic
  + 0.20 * metadata
  + 0.10 * authority
```

Later we can replace this with a learned reranker.

Do not start with a complicated model.

---

# 21. Domain-aware retrieval

This is where Kallam becomes more sophisticated.

If the planner says:

```text
specialist = HADITH
```

the retriever can automatically prioritize:

```text
Hadith
Sharh
Rijal
```

rather than returning random web pages or generic books.

Likewise:

```text
specialist = USUL
```

prioritizes:

```text
Usul works
Fiqh works discussing the principle
Scholarly explanations
```

---

# 22. Evidence promotion

Retrieval produces:

```text
RetrievedDocument
```

It does **not** immediately produce:

```text
Evidence
```

The promotion process is:

```text
Retrieved
   │
   ▼
Identity verified?
   │
   ├── NO → UNVERIFIED
   │
   └── YES
        │
        ▼
     Evidence
        │
        ▼
   citation generated
```

This distinction is one of Kallam's core safeguards.

---

# 23. Citation verification

The verifier should check:

```text
Does source exist?
Does work exist?
Does edition exist?
Does volume exist?
Does page exist?
Does passage exist?
Does passage match?
```

So:

```java
public interface CitationVerifier {

    VerificationResult verify(
            Evidence evidence
    );
}
```

Result:

```java
public record VerificationResult(
        VerificationStatus status,
        List<VerificationIssue> issues
) {}
```

---

# 24. Citation status

A citation can have:

```text
VERIFIED
PARTIALLY_VERIFIED
UNVERIFIED
CONFLICTING
INVALID
```

This should appear directly in the scholar interface.

---

# 25. Prevent fake page numbers

This is a very important implementation rule.

The LLM should **never generate a page number**.

Instead:

```text
LLM
 ↓
passage ID
 ↓
database
 ↓
page metadata
 ↓
citation
```

In other words:

> **The model selects evidence; the database supplies the citation.**

This single architectural decision can eliminate a major category of hallucinated references.

---

# 26. Same principle for hadith numbers

Don't ask the model:

> "What is the hadith number?"

Instead:

```text
retrieved passage
      ↓
stable hadith ID
      ↓
edition metadata
      ↓
displayed citation number
```

The citation is generated by infrastructure.

---

# 27. Provenance chain

Every evidence item should be traceable:

```text
Evidence E001
    │
    ├── source
    ├── work
    ├── edition
    ├── document
    ├── passage
    ├── retrieval query
    ├── retrieval timestamp
    ├── retriever version
    └── verification result
```

This gives us:

> **evidence provenance**

rather than just:

> source text.

---

# 28. Retrieval audit

Store the query.

```java
public record RetrievalEvent(
        String id,
        String caseId,
        String query,
        String specialist,
        String retrieverVersion,
        Instant timestamp,
        List<String> returnedPassageIds
) {}
```

Later, when a scholar asks:

> "Why did Kallam cite this?"

we can reconstruct the retrieval process.

---

# 29. Corpus versioning

Every corpus gets a version:

```text
quran-v1
hadith-v1
tafsir-v1
fiqh-v1
```

Then:

```text
research case
    ↓
corpus snapshot
```

A result from yesterday remains reproducible even if the corpus changes tomorrow.

---

# 30. Immutable source principle

Once a source has been ingested:

```text
source → immutable
```

Corrections create:

```text
new version
```

rather than silently modifying the old one.

For example:

```text
DOC-001 v1
DOC-001 v2
```

This is extremely important for scholarly reproducibility.

---

# 31. Metadata quality

We should also score metadata.

```text
MetadataQuality

author       ✓
work         ✓
edition      ✓
volume       ✓
page         ✓
language     ✓
section      ?
```

Then:

```text
metadata completeness = 7/8
```

The system knows how reliable its own citation infrastructure is.

---

# 32. Corpus ingestion should be deterministic

The same source should produce:

```text
same document ID
same passage IDs
same normalization
same metadata
```

assuming the same ingestion version.

Therefore:

```text
file checksum
+
parser version
+
normalizer version
=
reproducible corpus artifact
```

---

# 33. Data directory

The physical layout can be:

```text
data/
│
├── raw/
│   ├── quran/
│   ├── hadith/
│   ├── tafsir/
│   └── fiqh/
│
├── normalized/
│
├── manifests/
│
├── indexes/
│   ├── fts/
│   └── vectors/
│
└── snapshots/
```

Don't put raw source files directly into the database.

Keep the original artifact.

---

# 34. Corpus manifest

Each corpus gets a manifest:

```json
{
  "corpus_id": "TAFSIR",
  "version": "1.0.0",
  "created_at": "2026-08-26",
  "documents": 124,
  "passages": 182394,
  "languages": ["ar"],
  "parser_version": "0.3.0",
  "normalizer_version": "0.2.0"
}
```

This makes the corpus auditable.

---

# 35. The first retrieval API

Eventually:

```http
POST /api/research/search
```

with:

```json
{
  "query": "بيع الغرر",
  "domain": "FIQH",
  "language": "ar",
  "limit": 10
}
```

returns:

```json
{
  "results": [
    {
      "passage_id": "P00192",
      "score": 0.94,
      "source": "...",
      "author": "...",
      "work": "...",
      "location": "...",
      "text": "..."
    }
  ]
}
```

Again, **the UI never has to invent citation metadata**.

---

# 36. The first specialist becomes very simple

For example:

```java
public final class FiqhSpecialist
        implements Specialist {

    private final Retriever retriever;

    @Override
    public SpecialistType type() {
        return SpecialistType.FIQH;
    }

    @Override
    public SpecialistResult execute(
            ResearchTask task,
            ResearchContext context
    ) {

        var results = retriever.search(
            new SearchQuery(
                task.question(),
                List.of()
            ),
            new RetrievalFilter(
                EvidenceType.FIQH,
                null,
                null,
                "ar"
            ),
            10
        );

        return EvidenceMapper.toSpecialistResult(results);
    }
}
```

That's enough for the first version.

We don't need an elaborate autonomous Fiqh agent yet.

---

# 37. Then add intelligence gradually

Version progression:

```text
DEV.2
simple retrieval
      ↓
DEV.3
domain-aware retrieval
      ↓
DEV.4
query expansion
      ↓
DEV.5
reranking
      ↓
DEV.6
specialist-specific retrieval
      ↓
DEV.7
learned retrieval
```

This keeps the system testable.

---

# 38. The most important rule for the whole corpus layer

I would put this directly in the project documentation:

> **Never allow generated text to become authoritative source material merely because the model generated it.**

There must always be a distinction:

```text
SOURCE
EVIDENCE
CLAIM
INFERENCE
SYNTHESIS
```

And the data model must enforce it.

---

# 39. What DEV.2 gives us

At the end of this milestone:

```text
                KALLAM
                   │
                   ▼
              Research Case
                   │
                   ▼
              Search Query
                   │
          ┌────────┴────────┐
          ▼                 ▼
        FTS5              Vector
          │                 │
          └────────┬────────┘
                   ▼
                Rerank
                   │
                   ▼
              Passages
                   │
                   ▼
              Evidence
                   │
                   ▼
             Verification
                   │
                   ▼
              Citation
                   │
                   ▼
            Evidence Graph
```

At this point Kallam has something extremely valuable:

**a trustworthy evidence substrate.**

---

# Next: v1.0-DEV.3 — The Specialist Layer

Now we can implement the specialized research agents properly.

Instead of generic agents, we'll define:

```text
Qur'an Agent
Hadith Agent
Tafsir Agent
Arabic Agent
Balaghah Agent
Sanad/Rijal Agent
Fiqh Agent
Madhhab Agent
Usul Agent
Maqasid Agent
Comparison Agent
Citation Agent
```

Each will have:

```text
domain knowledge
+
domain-specific retrieval
+
domain-specific tools
+
domain-specific reasoning schema
+
domain-specific critic
```

And, importantly for your original goal, we can make the **orchestrator dynamically decide which specialists are actually needed**, so a small local model does not waste tokens invoking every expert for every mas'alah.


Excellent. We now move to **Kallam v1.0-DEV.3 — Specialist Layer**.

This is where the earlier architecture becomes a real multi-expert research system.

The key design decision is:

> **Specialists are not independent chatbots. They are constrained research workers operating over the same evidence/provenance substrate.**

That keeps local inference efficient and makes their outputs composable.

---

# Kallam v1.0-DEV.3

## Specialist Research Layer

The architecture becomes:

```text
                         RESEARCH CASE
                              │
                              ▼
                     MAS'ALAH DECOMPOSER
                              │
                              ▼
                       RESEARCH PLANNER
                              │
             ┌────────────────┼────────────────┐
             │                │                │
             ▼                ▼                ▼
          Qur'an           Hadith           Fiqh
          Expert            Expert           Expert
             │                │                │
             ▼                ▼                ▼
          Tafsir            Sanad           Madhhab
          Expert            Expert           Expert
             │                │                │
             ▼                ▼                ▼
         Arabic           Balaghah           Usul
          Expert            Expert           Expert
             │                │                │
             └────────────────┼────────────────┘
                              ▼
                       EVIDENCE GRAPH
                              │
                              ▼
                     CROSS-DOMAIN ANALYSIS
```

---

# 1. Specialist taxonomy

We should distinguish **research domains** from **agent implementations**.

The initial registry:

```text
QURAN
TAFSIR
HADITH
SANAD
RIJAL
ARABIC
BALAGHAH
FIQH
MADHHAB
USUL
MAQASID
COMPARISON
CITATION
```

But not every research case invokes all of them.

For example:

### Simple Qur'anic question

```text
Quran
Tafsir
Arabic
```

### Hadith authenticity question

```text
Hadith
Sanad
Rijal
```

### Contemporary fiqh question

```text
Quran
Hadith
Fiqh
Madhhab
Usul
Maqasid
Comparison
```

This is where the planner saves tokens.

---

# 2. Specialist capability registry

Instead of hardcoding:

```java
if question.contains("hadith")
```

we define capabilities.

```java
public record SpecialistCapability(
        SpecialistType type,
        Set<ResearchCapability> capabilities,
        Set<EvidenceType> preferredEvidence,
        int defaultPriority,
        int tokenCost
) {}
```

Capabilities might include:

```text
FIND_PRIMARY_TEXT
INTERPRET_TEXT
IDENTIFY_SCHOLAR_POSITION
COMPARE_POSITIONS
ANALYZE_LANGUAGE
ANALYZE_HADITH_CHAIN
ANALYZE_USUL
ANALYZE_QIYAS
VERIFY_CITATION
```

---

# 3. Specialist registry

```java
public interface SpecialistRegistry {

    Optional<Specialist> find(
        SpecialistType type
    );

    List<SpecialistCapability> capabilities();
}
```

Implementation:

```java
public final class DefaultSpecialistRegistry
        implements SpecialistRegistry {

    private final Map<SpecialistType, Specialist> specialists;

    // registration...
}
```

Then the planner can ask:

> Which specialist can perform `ANALYZE_USUL`?

rather than knowing the implementation.

---

# 4. Specialist contract

Every specialist gets the same outer contract:

```java
public interface Specialist {

    SpecialistType type();

    SpecialistCapability capability();

    SpecialistResult execute(
        SpecialistRequest request
    );
}
```

Request:

```java
public record SpecialistRequest(
        String caseId,
        ResearchTask task,
        ResearchContext context,
        List<Evidence> existingEvidence,
        TokenBudget budget
) {}
```

This is important:

**Specialists can consume previously discovered evidence.**

They do not always have to start from zero.

---

# 5. Specialist result

The result remains structured:

```java
public record SpecialistResult(
        SpecialistType specialist,
        List<Evidence> evidence,
        List<Claim> claims,
        List<Argument> arguments,
        List<ResearchGap> gaps,
        List<ResearchTask> followUpTasks
) {}
```

The last field is particularly important.

A specialist can say:

```text
I found an important disagreement.
This requires another investigation.
```

Instead of pretending it has completed the problem.

---

# 6. Example: Qur'an Specialist

The Qur'an specialist has a narrow responsibility.

```text
Qur'an Specialist
│
├── identify relevant ayat
├── retrieve exact ayah
├── identify surrounding verses
├── identify thematic relations
├── detect lexical relevance
└── provide verified references
```

It should **not** automatically conclude the fiqh ruling.

---

# 7. Qur'an specialist workflow

```text
Research task
     │
     ▼
Query expansion
     │
     ▼
Qur'an search
     │
     ▼
Ayah candidates
     │
     ▼
Relevance analysis
     │
     ▼
Verified ayah evidence
```

Output:

```text
E001
QURAN
2:275

C001
"Verse explicitly mentions..."
```

The distinction remains:

```text
ayah
≠
tafsir
≠
fiqh conclusion
```

---

# 8. Tafsir Specialist

The Tafsir specialist consumes Qur'an evidence.

```text
Qur'an evidence
      │
      ▼
identify tafsir sources
      │
      ▼
retrieve passages
      │
      ▼
compare explanations
      │
      ▼
extract interpretations
```

Its result could contain:

```text
T001
Tafsir interpretation

T002
Alternative interpretation

T003
Interpretive disagreement
```

The graph becomes:

```text
Q001
 │
 ├── EXPLAINED_BY → T001
 └── EXPLAINED_BY → T002
```

---

# 9. Arabic Specialist

This should be separate from Tafsir.

Responsibilities:

```text
Arabic
│
├── nahw
├── sarf
├── lexical meaning
├── root analysis
├── morphology
├── syntactic relation
└── semantic range
```

For example:

```text
Ayah
 ↓
target word
 ↓
morphological form
 ↓
syntactic role
 ↓
possible meanings
```

Output:

```text
LINGUISTIC_ANALYSIS
```

not:

```text
FIQH_CONCLUSION
```

---

# 10. Balaghah Specialist

This is another independent domain.

Potential capabilities:

```text
AMR
NAHY
TAQDIM_TA'KHIR
HASR
TAWKID
MAJAZ
KINAYAH
ISTI'ARAH
ITNAB
IJAaz
```

The specialist identifies rhetorical implications where relevant.

Again:

> It provides analysis; it does not automatically convert rhetoric into a legal ruling.

---

# 11. Hadith Specialist

The Hadith specialist handles:

```text
Hadith
│
├── identification
├── collection
├── chapter
├── matn
├── variants
├── source comparison
└── hadith metadata
```

The Hadith specialist should delegate:

```text
isnad
narrator criticism
rijal
```

to specialized components.

---

# 12. Sanad Specialist

This is where Kallam can eventually become quite powerful.

Input:

```text
Narrator A
    ↓
Narrator B
    ↓
Narrator C
    ↓
Prophet ﷺ
```

Output:

```text
chain analysis
```

For each narrator:

```text
identity
generation
teachers
students
reliability reports
dates
possible meeting
```

Then:

```text
CHAIN:
CONNECTED
POSSIBLY_CONNECTED
DISCONNECTED
UNCERTAIN
```

Important:

> The model should not invent narrator relationships.

Those should come from the structured rijal database.

---

# 13. Rijal Specialist

Rijal is best treated as a knowledge graph.

```text
Scholar
 │
 ├── taught → Narrator
 ├── student_of → Scholar
 ├── lived_in → Place
 ├── died_in → Year
 └── evaluated_by → Critic
```

Then the Sanad specialist queries the graph.

This is much better than expecting an LLM to memorize every narrator relationship.

---

# 14. Fiqh Specialist

The Fiqh specialist focuses on:

```text
mas'alah
positions
rulings
conditions
exceptions
supporting evidences
scholar attribution
```

It should extract:

```text
Position A
Position B
Position C
```

rather than prematurely merging them.

---

# 15. Madhhab Specialist

The Madhhab specialist adds methodological context.

For example:

```text
Hanafi
Maliki
Shafi'i
Hanbali
```

But also:

```text
internal positions
relied-upon position
minority position
later development
```

Potential output:

```text
MadhhabPosition {
    madhhab,
    scholar,
    work,
    position,
    strength,
    evidence,
    methodologicalBasis
}
```

This allows comparison without flattening differences.

---

# 16. Usul Specialist

This is one of the most important specialists.

It examines:

```text
Text
 ↓
Dalalah
 ↓
Usul principle
 ↓
Application
 ↓
Result
```

Potential categories:

```text
عام / خاص
مطلق / مقيد
مجمل / مبين
ناسخ / منسوخ
أمر / نهي
مفهوم
منطوق
استصحاب
استحسان
مصالح
عرف
سد الذرائع
قياس
إجماع
```

The exact set should ultimately be configurable by usul framework.

---

# 17. Framework-specific Usul

This is critical.

We should not implement:

```text
UniversalUsulAgent
```

as if all schools use exactly the same methodology.

Instead:

```text
UsulFramework
│
├── Hanafi
├── Maliki
├── Shafi'i
├── Hanbali
└── Comparative
```

Then:

```java
public interface UsulFramework {

    String id();

    List<UsulRule> rules();

    List<ResearchCapability> capabilities();
}
```

Now the same evidence can be analyzed under different methodologies.

---

# 18. Maqasid Specialist

The Maqasid specialist should be deliberately constrained.

It can analyze:

```text
preservation of religion
life
intellect
lineage
property
```

and potentially broader maqasid frameworks depending on the selected scholarly methodology.

But it should not say:

> "This benefits society, therefore it is halal."

Instead:

```text
Evidence
 ↓
identified maslahah
 ↓
relevant maqasid
 ↓
relationship to established principles
 ↓
potential legal relevance
```

This prevents simplistic "maslahah reasoning."

---

# 19. Comparison Specialist

This specialist is a **synthesis-oriented researcher**, not merely a summarizer.

It receives:

```text
Position A
Position B
Position C
```

and produces:

```text
agreement
disagreement
reason for disagreement
evidence differences
usul differences
scope differences
```

Example:

```text
Position A
  ↓
accepts qiyas here

Position B
  ↓
rejects qiyas here

Difference:
different identification of 'illah
```

That is much more useful than:

> "Scholar A says X while Scholar B says Y."

---

# 20. Citation Specialist

This should be largely deterministic.

Its job:

```text
claim
 ↓
evidence
 ↓
source metadata
 ↓
citation
```

It should also identify:

```text
missing citation
incorrect citation
weak citation
secondary citation
conflicting citation
```

And importantly:

> It should not manufacture references.

---

# 21. Specialist critic

Each specialist should eventually have its own critic.

For example:

```text
Hadith Specialist
      ↓
Hadith Critic
```

checks:

```text
Is the hadith correctly identified?
Is the source correct?
Are variants conflated?
Is grading attributed?
```

Likewise:

```text
Usul Specialist
      ↓
Usul Critic
```

checks:

```text
Was the principle correctly identified?
Was it actually applied?
Was the framework appropriate?
```

---

# 22. But don't run every critic every time

Again, local inference optimization.

```text
Hadith research
    ↓
Hadith Critic
    ↓
Citation Critic
```

No need to invoke:

```text
Balaghah Critic
Maqasid Critic
```

unless relevant.

---

# 23. Specialist orchestration

The planner creates a dependency graph.

Example:

```text
                    Question
                       │
                       ▼
                  Qur'an Search
                       │
                 ┌─────┴─────┐
                 ▼           ▼
               Tafsir      Arabic
                 │           │
                 └─────┬─────┘
                       ▼
                    Usul
                       │
                       ▼
                     Fiqh
                       │
                 ┌─────┴─────┐
                 ▼           ▼
             Madhhab     Comparison
                 │           │
                 └─────┬─────┘
                       ▼
                   Synthesis
```

The system can execute independent branches in parallel.

---

# 24. Parallel execution

This is important for performance.

If:

```text
Quran
Hadith
Tafsir
```

are independent:

```text
        Research
           │
     ┌─────┼─────┐
     ▼     ▼     ▼
   Quran Hadith Tafsir
     │     │     │
     └─────┼─────┘
           ▼
        Merge
```

We don't want:

```text
Quran → wait
Hadith → wait
Tafsir → wait
```

sequentially.

---

# 25. Token budgeting per specialist

Each specialist gets its own budget:

```java
public record SpecialistBudget(
        int maxInputTokens,
        int maxOutputTokens,
        int maxRetrievalResults,
        int maxIterations
) {}
```

Example:

```text
Quran:
2k

Tafsir:
4k

Hadith:
5k

Usul:
7k

Synthesis:
8k
```

The planner can adjust these dynamically.

---

# 26. Context compression

This is one of the biggest local-inference optimizations.

Suppose Tafsir returns:

```text
20 passages
```

Don't pass all 20 to Usul.

Instead:

```text
20 passages
     ↓
Tafsir specialist
     ↓
5 evidence units
     ↓
Usul specialist
```

So the communication between specialists is:

```text
structured evidence
```

not:

```text
massive conversation transcript
```

This is a major architectural advantage.

---

# 27. Specialist memory

A specialist can have domain memory.

For example:

```text
Hadith specialist memory
├── preferred collections
├── known terminology
├── retrieval strategies
└── validated corrections
```

But we must distinguish:

```text
runtime memory
```

from:

```text
authoritative corpus
```

Memory should never silently become authoritative evidence.

---

# 28. Runtime learning

Suppose a scholar says:

> "This phrase is being interpreted incorrectly in this context."

Store:

```text
FeedbackEvent
```

not:

```text
overwrite model knowledge
```

Example:

```json
{
  "type": "INTERPRETATION_CORRECTION",
  "specialist": "ARABIC",
  "case_id": "CASE-104",
  "original_output": "...",
  "scholar_correction": "...",
  "scope": "CASE_ONLY"
}
```

Later we can promote it to:

```text
DOMAIN_MEMORY
```

only after evaluation.

---

# 29. Specialist learning hierarchy

This gives us a safe learning pipeline:

```text
Scholar correction
      │
      ▼
Case memory
      │
      ▼
Repeated pattern
      │
      ▼
Candidate knowledge
      │
      ▼
Evaluation
      │
      ├── reject
      │
      └── approve
             │
             ▼
       Specialist memory
             │
             ▼
       Optional training
```

This is much safer than continuous uncontrolled fine-tuning.

---

# 30. Dynamic specialist selection

The planner should eventually output:

```json
{
  "required_specialists": [
    "QURAN",
    "TAFSIR",
    "ARABIC",
    "FIQH",
    "USUL"
  ],
  "optional_specialists": [
    "MAQASID",
    "COMPARISON"
  ]
}
```

Then the orchestrator decides.

This is the heart of the **agentic architecture**.

---

# 31. Specialist dependency rules

We can also define deterministic rules.

For example:

```text
If task = HADITH_AUTHENTICITY
    → HADITH
    → SANAD
    → RIJAL
    → CITATION
```

If:

```text
task = QURAN_INTERPRETATION
```

then:

```text
QURAN
TAFSIR
ARABIC
BALAGHAH
```

If:

```text
task = NEW_FIQH_PROBLEM
```

then potentially:

```text
QURAN
HADITH
FIQH
MADHHAB
USUL
COMPARISON
MAQASID
```

The LLM can refine this plan, but deterministic constraints keep it sane.

---

# 32. Specialist quality score

Every specialist execution can produce:

```text
Evidence quality
Citation quality
Coverage
Uncertainty
```

For example:

```text
Hadith Specialist

Evidence coverage:      92
Source verification:    100
Variant confidence:     78
Unresolved gaps:          2
```

This feeds back into orchestration.

If Hadith confidence is low:

```text
run another retrieval pass
```

If it is high:

```text
continue.
```

---

# 33. The full specialist runtime

```text
                     PLANNER
                        │
                        ▼
                Specialist DAG
                        │
       ┌────────────────┼────────────────┐
       ▼                ▼                ▼
    Specialist A     Specialist B     Specialist C
       │                │                │
       ▼                ▼                ▼
    Critic A          Critic B          Critic C
       │                │                │
       └────────────────┼────────────────┘
                        ▼
                 Evidence Merger
                        │
                        ▼
                  Graph Builder
```

That is the structure I would implement.

---

# 34. Important: specialists must not vote

A tempting design would be:

```text
Quran agent says X
Hadith agent says X
Fiqh agent says Y

3 vs 1 → X wins
```

**Do not do this.**

Scholarly reasoning is not majority voting between LLMs.

Instead:

```text
Specialists
    ↓
Evidence
    ↓
Arguments
    ↓
Methodological analysis
    ↓
Critic
    ↓
Scholar
```

The graph represents **why** a position exists.

---

# 35. Specialist disagreement is valuable

If:

```text
Arabic Specialist:
interpretation A

Tafsir Specialist:
interpretation B
```

that should become:

```text
DISAGREEMENT
```

not automatically resolved.

Then:

```text
Comparison Specialist
```

can investigate the disagreement.

This is precisely where the system becomes useful to the scholar.

---

# 36. DEV.3 acceptance test

We should consider the milestone successful when Kallam can take one research question and dynamically produce something like:

```text
Research Plan
────────────────────────────

Required:
✓ Quran
✓ Tafsir
✓ Fiqh
✓ Usul

Conditional:
✓ Arabic
✓ Madhhab

Not required:
○ Sanad
○ Rijal
○ Maqasid
```

Then:

```text
Specialist Results
────────────────────────────

Quran
  4 relevant ayat
  4 verified

Tafsir
  8 passages
  2 interpretive positions

Fiqh
  3 positions
  2 madhhabs

Usul
  2 applicable principles
  1 methodological disagreement
```

Then:

```text
Evidence Graph
       ↓
Reasoning
       ↓
Critic
       ↓
Research gaps
       ↓
Next research tasks
```

At that point the system is no longer merely "multi-agent."

It is a **structured scholarly research workflow**.

---

# Next milestone: v1.0-DEV.4

The next step should be:

## **Reasoning Engine + Istinbāṭ Reconstruction**

This is where we connect everything we have built:

```text
Qur'an
Hadith
Tafsir
Arabic
Fiqh
Madhhab
Usul
Sanad
       │
       ▼
EVIDENCE GRAPH
       │
       ▼
┌──────────────────────────────┐
│ ISTINBĀṬ RECONSTRUCTION      │
│                              │
│ 1. Tasawwur al-Mas'alah      │
│ 2. Takyif Fiqhi              │
│ 3. Jam' al-Nusus             │
│ 4. Textual Analysis          │
│ 5. Usul Application          │
│ 6. Ijma / Ikhtilaf           │
│ 7. Qiyas                     │
│ 8. Maqasid                   │
│ 9. Counterarguments          │
│ 10. Tarjih / Synthesis       │
└──────────────────────────────┘
       │
       ▼
   CRITIC LOOP
       │
       ▼
 SCHOLAR DOSSIER
```

That will be the **most intellectually important implementation milestone so far**, because there we will define exactly how Kallam reconstructs the *workflow surrounding istinbāṭ* without claiming to replace the mujtahid.
