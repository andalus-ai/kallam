package tech.kayys.kallam.quarkus;

import io.quarkus.arc.properties.IfBuildProperty;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.istinbat.ResearchAnalyzer;

import static org.junit.jupiter.api.Assertions.*;

class AndalusResearchAnalyzerProducerTest {

    @Test
    void modelBackedAnalyzerIsOnlyProducedWhenExplicitlyEnabledAtBuildTime() throws Exception {
        var method = AndalusResearchAnalyzerProducer.class.getMethod(
                "configuredResearchAnalyzer",
                jakarta.enterprise.inject.Instance.class,
                jakarta.enterprise.inject.Instance.class,
                com.fasterxml.jackson.databind.ObjectMapper.class,
                String.class,
                String.class,
                java.time.Duration.class);

        var condition = method.getAnnotation(IfBuildProperty.class);
        assertNotNull(condition);
        assertEquals("kallam.reasoning.enabled", condition.name());
        assertEquals("true", condition.stringValue());
        assertEquals(ResearchAnalyzer.class, method.getReturnType());
    }
}
