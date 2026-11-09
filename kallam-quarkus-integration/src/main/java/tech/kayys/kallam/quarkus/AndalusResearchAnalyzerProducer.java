package tech.kayys.kallam.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tech.kayys.andalus.harness.inference.DefaultInferenceRuntime;
import tech.kayys.andalus.harness.inference.InferenceProviderAdapter;
import tech.kayys.andalus.harness.inference.InferenceRuntime;
import tech.kayys.kallam.andalus.AndalusInferenceResearchAnalyzer;
import tech.kayys.kallam.istinbat.ResearchAnalyzer;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

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
            Instance<InferenceRuntime> inferenceRuntimes,
            Instance<InferenceProviderAdapter> providerAdapters,
            ObjectMapper objectMapper,
            @ConfigProperty(name = "kallam.reasoning.model-id", defaultValue = "") String modelId,
            @ConfigProperty(name = "kallam.reasoning.provider-id", defaultValue = "") String providerId,
            @ConfigProperty(name = "kallam.reasoning.timeout", defaultValue = "PT45S") Duration timeout) {
        if (modelId == null || modelId.isBlank()) {
            throw new IllegalStateException(
                    "kallam.reasoning.model-id must be configured when Kallam reasoning is enabled");
        }
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalStateException("kallam.reasoning.timeout must be a positive ISO-8601 duration");
        }
        Optional<String> selectedProvider = providerId == null || providerId.isBlank()
                ? Optional.empty() : Optional.of(providerId.trim());
        InferenceRuntime runtime = resolveRuntime(inferenceRuntimes, providerAdapters, modelId, selectedProvider);
        return new AndalusInferenceResearchAnalyzer(runtime, modelId, selectedProvider, objectMapper, timeout);
    }

    private static InferenceRuntime resolveRuntime(
            Instance<InferenceRuntime> inferenceRuntimes,
            Instance<InferenceProviderAdapter> providerAdapters,
            String modelId,
            Optional<String> providerId) {
        if (inferenceRuntimes.isAmbiguous()) {
            throw new IllegalStateException("Multiple Andalus InferenceRuntime beans are available; expose one runtime bean");
        }
        if (inferenceRuntimes.isResolvable()) {
            return inferenceRuntimes.get();
        }

        List<InferenceProviderAdapter> compatibleProviders = providerAdapters.stream()
                .filter(provider -> provider.supports(modelId))
                .filter(provider -> providerId.isEmpty() || provider.providerId().equals(providerId.get()))
                .toList();
        if (compatibleProviders.isEmpty()) {
            throw new IllegalStateException(
                    "No Andalus InferenceRuntime or provider adapter supports model " + modelId
                            + (providerId.isPresent() ? " via provider " + providerId.get() : ""));
        }
        if (compatibleProviders.size() > 1) {
            throw new IllegalStateException(
                    "Multiple providers support model " + modelId + "; set kallam.reasoning.provider-id explicitly");
        }
        return new DefaultInferenceRuntime(compatibleProviders);
    }
}
