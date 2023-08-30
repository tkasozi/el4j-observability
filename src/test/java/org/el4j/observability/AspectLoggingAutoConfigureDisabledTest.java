package org.el4j.observability;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/* default */class AspectLoggingAutoConfigureDisabledTest {
	@Test
		/* default */void shouldDisableInformationAspectWhenPropertyIsFalse() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("Should be Null when elf4j.metrics.logging.enabled is false")
					.isNotEmpty()
					.doesNotContain("el4jInformationLoggingAspect");
		}
	}

	@Test
		/* default */void shouldDisableExceptionsAspectWhenPropertyIsFalse() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=true")
				.properties("elf4j.metrics.logging.exceptions=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("Should be Null when elf4j.metrics.logging.exceptions is false")
					.doesNotContain("el4jExceptionLoggingAspect");
		}
	}

	@Test
	@DisplayName("Should disable exception logging when logging is disabled.")
		/* default */void shouldDisableExceptionsAspectWhenLoggingPropertyIsFalse() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=false")
				.properties("elf4j.metrics.logging.exceptions=true")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("Should be disable when elf4j.metrics.logging.enabled is false")
					.doesNotContain("el4jExceptionLoggingAspect");
		}
	}
}
