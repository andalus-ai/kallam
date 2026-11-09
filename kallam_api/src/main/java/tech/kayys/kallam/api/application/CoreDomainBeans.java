package tech.kayys.kallam.api.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import io.quarkus.arc.properties.UnlessBuildProperty;
import java.util.List;
import tech.kayys.kallam.istinbat.BasicResearchCritic;
import tech.kayys.kallam.istinbat.DefaultResearchAnalyzer;
import tech.kayys.kallam.istinbat.DefaultResearchReportBuilder;
import tech.kayys.kallam.istinbat.IstinbatCoordinator;
import tech.kayys.kallam.istinbat.ResearchAnalyzer;
import tech.kayys.kallam.istinbat.ResearchCritic;
import tech.kayys.kallam.istinbat.ResearchPlanner;
import tech.kayys.kallam.istinbat.ResearchReportBuilder;
import tech.kayys.kallam.istinbat.ResearchSpecialist;
import tech.kayys.kallam.istinbat.SimpleResearchPlanner;

/**
 * kallam-core is a plain Java library with no CDI/Quarkus dependency on
 * purpose - this is the one place that wires its interfaces into beans.
 *
 * <p><b>Gap to close:</b> no {@link ResearchSpecialist} beans exist yet
 * (Quran/Hadith/fiqh/usul retrieval adapters, presumably calling into the
 * Andalus agent runtime). {@code IstinbatCoordinator} already tolerates an
 * empty specialist list (it just skips unmatched task ids), so this module
 * runs end-to-end today - it produces a taswir and an empty evidence set
 * per session until those adapters are added as their own beans here.
 */
@ApplicationScoped
public class CoreDomainBeans {

    @Produces
    @ApplicationScoped
    public ResearchPlanner researchPlanner() {
        return new SimpleResearchPlanner();
    }

    @Produces
    @ApplicationScoped
    @UnlessBuildProperty(name = "kallam.reasoning.enabled", stringValue = "true")
    public ResearchAnalyzer researchAnalyzer() {
        // Keep the safe no-model implementation as the default. When the
        // optional Andalus runtime profile is built and reasoning is enabled,
        // its conditional producer supplies the model-backed implementation.
        return new DefaultResearchAnalyzer();
    }

    @Produces
    @ApplicationScoped
    public ResearchCritic researchCritic() {
        return new BasicResearchCritic();
    }

    @Produces
    @ApplicationScoped
    public ResearchReportBuilder researchReportBuilder() {
        return new DefaultResearchReportBuilder();
    }

    @Produces
    @ApplicationScoped
    public IstinbatCoordinator istinbatCoordinator(
            ResearchPlanner planner,
            ResearchAnalyzer analyzer,
            ResearchCritic critic,
            ResearchReportBuilder reportBuilder,
            Instance<ResearchSpecialist> specialists) {
        List<ResearchSpecialist> resolved = specialists.stream().toList();
        return new IstinbatCoordinator(planner, analyzer, critic, reportBuilder, resolved);
    }
}
