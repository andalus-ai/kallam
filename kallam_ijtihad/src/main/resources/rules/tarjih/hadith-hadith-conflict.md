---
id: tarjih.hadith-hadith-conflict
tier: HADITH
category: TARJIH
title: Tarjih between apparently conflicting hadith
appliesWhen: Two or more hadith appear to address the same mas'alah with apparently conflicting rulings
priority: 10
madhhab: common
---

# Tarjih between apparently conflicting hadith

As with Qur'an-Qur'an conflicts, attempt jam' (reconciliation) before
anything else. Only once genuine conflict remains does isnad/matn-level
tarjih apply, in roughly this order - later criteria break ties left by
earlier ones, they don't override them:

1. **Jam' first.** Do the hadith address different circumstances,
   different addressees, or different times? Reconcile before ranking.

2. **Naskh, if independently established.** As with Qur'an-Qur'an
   conflict, a later hadith on the same specific matter can abrogate an
   earlier one *if* the chronology is independently evidenced (asbab
   al-wurud, explicit naskh reports) - not inferred from the ruling
   conflict itself.

3. **Isnad strength.** Mutawatir outranks ahad. Among ahad hadith: sahih
   outranks hasan outranks da'if. A da'if hadith is not simply
   discarded if it corroborates a sahih/hasan hadith's conclusion, but it
   should never outweigh a sahih hadith on its own.

4. **Corroboration.** A hadith transmitted through multiple independent
   chains (even if individually ahad) carries more weight than a single
   isolated chain, all else equal.

5. **Fiqh al-hadith (closeness to the matter at hand).** A hadith that
   speaks directly and specifically to the mas'alah outweighs one that
   addresses it only by broader implication.

6. **Consistency with established usul principles and qawa'id
   fiqhiyyah** already accepted elsewhere in this research - a hadith
   whose apparent meaning would contradict a firmly established qa'idah
   should be checked for an alternative valid reading before being taken
   as settling the matter alone.

If none of the above yields a clear preference, record as unresolved and
route to scholar tarjih rather than defaulting to either hadith.

## What this module can resolve automatically

Step 3 (isnad grading comparison) is the one sub-step
{@code StructuredTarjihAdjudicator} in this module can apply as a plain
lookup, *provided* every conflicting finding cites evidence with a known
grading and the gradings actually differ. Equal gradings, or missing
grading data, correctly fall through to scholar review rather than being
guessed at - grading equality is exactly the case that needs steps 1, 2,
4, 5, and 6, none of which reduce to a lookup.

## Scoring guidance for the expert agent

- +30 for a hadith with an unbroken, scholar-graded isnad (cite the
  grading source)
- +20 for corroboration by an independent chain
- +15 for direct textual applicability to the specific mas'alah (versus
  general implication)
- -30 if grading is asserted without a citable source
- -20 if a da'if hadith is treated as decisive against a sahih one
