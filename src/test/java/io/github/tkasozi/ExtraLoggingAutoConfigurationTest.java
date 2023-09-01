package io.github.tkasozi;

import io.github.tkasozi.aspect.EventLogAppender;
import io.github.tkasozi.aspect.SystemCPUMonitoring;
import io.github.tkasozi.aspect.SystemMemoryMonitoring;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


/* default */class ExtraLoggingAutoConfigurationTest {

	@Test
		/* default */void context() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.enabled=false")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertThrows(NoSuchBeanDefinitionException.class,
					() -> applicationContext.getBean(EventLogAppender.class));
			Assertions.assertThrows(NoSuchBeanDefinitionException.class,
					() -> applicationContext.getBean(SystemCPUMonitoring.class));
			Assertions.assertThrows(NoSuchBeanDefinitionException.class,
					() -> applicationContext.getBean(SystemMemoryMonitoring.class));
		}
	}

	@Test
		/* default */void contextSystemMonitoring() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.extra.enabled=true")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertNotNull(applicationContext.getBean(SystemCPUMonitoring.class));
			Assertions.assertNotNull(applicationContext.getBean(SystemMemoryMonitoring.class));
		}
	}

	@Test
		/* default */void contextLoggingByProjectPackage() {
		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.extra.enabled=true")
				.properties("elf4j.metrics.logging.extra.packages[0]=mil.diux.radiance_backend.utils,ERROR")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertDoesNotThrow(() -> applicationContext.getBean(EventLogAppender.class));
		}

		try (ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
				.properties("server.port=8181")
				.properties("elf4j.metrics.logging.extra.enabled=true")
				.properties("spring.main.allow-bean-definition-overriding=true")
				.run()) {
			Assertions.assertThrows(
					NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(EventLogAppender.class));
		}
	}
}
