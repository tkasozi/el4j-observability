package org.el4j.observability;

import lombok.val;
import org.el4j.observability.endpoint.v1.CpuPerformanceEndpoints;
import org.el4j.observability.endpoint.v1.MemoryPerformanceEndpoints;
import org.el4j.observability.endpoint.v1.MetricsEndpoints;
import org.el4j.observability.endpoint.v1.UiPerformanceEndpoints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.builder.SpringApplicationBuilder;

/* default */class MetricsEndpointAutoConfigurationTest {

	@Test
		/* default */void contextUserMetricsEndpoint() {
		try (val applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertNotNull(applicationContext.getBean(MetricsEndpoints.class));
			Assertions.assertNotNull(applicationContext.getBean(UiPerformanceEndpoints.class));
		}
	}

	@Test
		/* default */void contextCpuPerformanceEndpoints() {
		try (val applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.extra.enabled=true")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertNotNull(applicationContext.getBean(CpuPerformanceEndpoints.class));
			Assertions.assertNotNull(applicationContext.getBean(MemoryPerformanceEndpoints.class));
		}
	}

	@Test
		/* default */void contextWithoutExtraLogsConfigured() {
		try (val applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.extra.enabled=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertThrows(
					NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(CpuPerformanceEndpoints.class));
			Assertions.assertThrows(
					NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(MemoryPerformanceEndpoints.class));
		}
	}
}
