package tech.kayys.kallam.api.application;

import io.quarkus.arc.properties.UnlessBuildProperty;
import org.junit.jupiter.api.Test;
import tech.kayys.kallam.istinbat.ResearchAnalyzer;

import static org.junit.jupiter.api.Assertions.*;

class CoreDomainBeansTest {

    @Test
    void unconfiguredAnalyzerRemainsTheDefaultUnlessReasoningIsEnabled() throws Exception {
        var method = CoreDomainBeans.class.getMethod("researchAnalyzer");
        var condition = method.getAnnotation(UnlessBuildProperty.class);

        assertNotNull(condition);
        assertEquals("kallam.reasoning.enabled", condition.name());
        assertEquals("true", condition.stringValue());
        assertEquals(ResearchAnalyzer.class, method.getReturnType());
        assertInstanceOf(ResearchAnalyzer.class, new CoreDomainBeans().researchAnalyzer());
    }
}
