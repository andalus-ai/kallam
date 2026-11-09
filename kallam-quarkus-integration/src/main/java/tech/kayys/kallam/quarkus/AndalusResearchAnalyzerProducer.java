package tech.kayys.kallam.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tech.kayys.andalus.harness.inference.InferenceRuntime;
import tech.kayys.kallam.andalus.AndalusInferenceResearchAnalyzer;
import tech.kayys.kallam.istinbat.ResearchAnalyzer;

import java.time.Duration;

/**
 * Quarkus-only composition root for the optional Andalus inference adapter.
 * The scholarly domain and the adapter module remain free of CDI dependencies.
 */
@ApplicationScoped
public class AndalusResearchAnalyzerProducer {

    @Produces
    @ApplicationScoped
    @IfBuildProperty(name = "kallam.reasoning.enabled", stringValue = "true")
    public ResearchAnalyzer configuredResearchAnalyzer(
            Instance<InferenceRuntime> inferenceRuntime,
            ObjectMapper objectMapper,
            @ConfigProperty(name = "kallam.reasoning.model-id", defaultValue = "") String modelId,
            @ConfigProperty(name = "kallam.reasoning.timeout", defaultValue = "PT45S") Duration timeout) {
        if (!inferenceRuntime.isResolvable()) {
            throw new IllegalStateException(
                    "kallam.reasoning.enabled=true but no Andalus InferenceRuntime bean is available");
        }
        if (modelId == null || modelId.isBlank()) {
            throw new IllegalStateException(
                    "kallam.reasoning.model-id must be configured when Kallam reasoning is enabled");
        }
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalStateException("kallam.reasoning.timeout must be a positive ISO-8601 duration");
        }
        return new AndalusInferenceResearchAnalyzer(inferenceRuntime.get(), modelId, objectMapper, timeout);
    }
}
