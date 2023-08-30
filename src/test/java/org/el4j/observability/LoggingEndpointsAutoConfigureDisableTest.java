package org.el4j.observability;

import org.junit.jupiter.api.Test;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/* default */class LoggingEndpointsAutoConfigureDisableTest {

	@Test
		/* default */void contextUserMetricsEndpoint() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("Should be Null when elf4j.metrics.logging.enabled is false")
					.doesNotContain("userMetricsEndpoint");
		}
	}

	@Test
		/* default */void cpuPerformanceEndpoints() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.extra.monitoring=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("Should be Null when elf4j.metrics.logging.extra.monitoring is false")
					.isNotEmpty()
					.doesNotContain("cpuPerformanceEndpoints");

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("Should be Null when elf4j.metrics.logging.extra.monitoring is false")
					.isNotEmpty()
					.doesNotContain("memoryPerformanceEndpoints");
		}
	}
}
