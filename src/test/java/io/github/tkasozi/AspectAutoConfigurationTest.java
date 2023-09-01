package io.github.tkasozi;

import io.github.tkasozi.aspect.ExceptionLoggingAspect;
import io.github.tkasozi.aspect.InformationLoggingAspect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/* default */class AspectAutoConfigurationTest {

	@DisplayName("Can disable aspect logger")
	@Test
		/* default */void context() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertThrows(
					NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(AspectAutoConfiguration.class));
			Assertions.assertThrows(
					NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(InformationLoggingAspect.class));
			Assertions.assertThrows(
					NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(ExceptionLoggingAspect.class));
		}
	}

	@DisplayName("Should have InformationLoggingAspect enabled by default")
	@Test
		/* default */void el4jInformationLoggingAspect() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertNotNull(applicationContext.getBean(AspectAutoConfiguration.class));
			Assertions.assertNotNull(applicationContext.getBean(InformationLoggingAspect.class));
		}
	}

	@DisplayName("Should have ExceptionLoggingAspect enabled by default")
	@Test
		/* default */void exceptionsAspect() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertNotNull(applicationContext.getBean(ExceptionLoggingAspect.class));
		}
	}
}
