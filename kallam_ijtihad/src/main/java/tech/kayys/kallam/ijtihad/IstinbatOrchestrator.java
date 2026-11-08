package tech.kayys.kallam.ijtihad;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The "ijtihad expert" that coordinates the specialist panel: dispatch
 * every applicable {@link IjtihadExpertPlugin}, gate by
 * {@link TierPriorityResolver} (deterministic), then adjudicate any
 * within-tier conflict via {@link TarjihAdjudicator} (rule-card-grounded,
 * not deterministic). This class makes no judgment calls of its own - it
 * has no domain knowledge about hadith, balaghah, or qiyas. Its only job
 * is sequencing and refusing to let a lower-priority source override a
 * higher one, which is exactly the discipline that's easy for a single
 * end-to-end LLM call to blur under time pressure.
 */
public final class IstinbatOrchestrator {

    private final List<IjtihadExpertPlugin> experts;
    private final RuleCardRepository ruleCardRepository;
    private final TierPriorityResolver tierPriorityResolver;
    private final TarjihAdjudicator tarjihAdjudicator;

    public IstinbatOrchestrator(
            List<IjtihadExpertPlugin> experts,
            RuleCardRepository ruleCardRepository,
            TierPriorityResolver tierPriorityResolver,
            TarjihAdjudicator tarjihAdjudicator) {
        this.experts = List.copyOf(experts);
        this.ruleCardRepository = ruleCardRepository;
        this.tierPriorityResolver = tierPriorityResolver;
        this.tarjihAdjudicator = tarjihAdjudicator;
    }

    public IstinbatDetermination determine(MasalahContext context) {
        List<ExpertOpinionResult> opinions = dispatchExperts(context);
        TierResolution tierResolution = tierPriorityResolver.resolve(opinions);

        if (tierResolution.isFullyUnresolved()) {
            return IstinbatDetermination.unresolved(opinions, tierResolution);
        }

        List<ExpertFinding> governing = tierResolution.governingFindings();
        if (governing.size() == 1) {
            return IstinbatDetermination.resolved(governing.get(0), opinions, tierResolution, List.of());
        }

        List<RuleCard> tarjihCards =
                ruleCardRepository.findApplicable(tierResolution.governingTier(), RuleCategory.TARJIH, context.madhhab());
        List<String> tarjihCardIds = tarjihCards.stream().map(RuleCard::id).toList();

        Optional<TarjihResolution> resolution =
                tarjihAdjudicator.adjudicate(tierResolution.governingTier(), governing, context, tarjihCards);

        return resolution
                .map(r -> IstinbatDetermination.resolved(r.preferred(), opinions, tierResolution, tarjihCardIds))
                .orElseGet(() -> IstinbatDetermination.needsScholarTarjih(governing, opinions, tierResolution, tarjihCardIds));
    }

    private List<ExpertOpinionResult> dispatchExperts(MasalahContext context) {
        List<ExpertOpinionResult> opinions = new ArrayList<>();
        for (IjtihadExpertPlugin expert : experts) {
            List<RuleCard> cards = expert.methodologyRuleCardIds(context).stream()
                    .map(ruleCardRepository::findById)
                    .flatMap(Optional::stream)
                    .toList();
            opinions.add(expert.analyze(context, cards));
        }
        return opinions;
    }
}
