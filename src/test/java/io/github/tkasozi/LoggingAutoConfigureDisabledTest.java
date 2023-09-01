package io.github.tkasozi;

import org.junit.jupiter.api.Test;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/* default */class LoggingAutoConfigureDisabledTest {

	@Test
		/* default */void shouldDisableDefaultLoggingWhenPropertyIsFalse() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			assertThat(applicationContext.getBeanDefinitionNames())
					.as("InformationAspect should not be configured")
					.isNotEmpty()
					.doesNotContain("el4jInformationLoggingAspect");

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("ExceptionLoggingAspect should not be configured")
					.isNotEmpty()
					.doesNotContain("el4jExceptionLoggingAspect");

			assertThat(applicationContext.getBeanDefinitionNames())
					.as("userMetricsEndpoint should not be configured")
					.isNotEmpty()
					.doesNotContain("userMetricsEndpoint");
		}
	}
}
