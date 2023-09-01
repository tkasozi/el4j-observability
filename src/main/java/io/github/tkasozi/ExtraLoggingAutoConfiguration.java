package io.github.tkasozi;

import java.util.List;
import java.util.Objects;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import io.github.tkasozi.aspect.EventLogAppender;
import io.github.tkasozi.aspect.SystemCPUMonitoring;
import io.github.tkasozi.aspect.SystemMemoryMonitoring;
import io.github.tkasozi.property.ExtraLoggingProperties;
import io.github.tkasozi.property.PackageLevelLog;
import io.github.tkasozi.repository.redis.CpuEventLogRepository;
import io.github.tkasozi.repository.redis.EventLogRepository;
import io.github.tkasozi.repository.redis.MemoryEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configures extra logging.
 * This includes:
 * <p>
 * 1. Cpu and memory monitoring.
 * <br>
 * 2. Package level logging.
 */
@AutoConfigureAfter({RedisRepositoryAutoConfiguration.class, AspectAutoConfiguration.class})
@ConditionalOnProperty({"elf4j.metrics.logging.enabled", "elf4j.metrics.logging.extra.enabled"})
@EnableScheduling
@Configuration
@Slf4j
public class ExtraLoggingAutoConfiguration {

	private final ExtraLoggingProperties properties;

	/**
	 * Constructor.
	 *
	 * @param properties Extra metrics properties.
	 */
	public ExtraLoggingAutoConfiguration(final @NonNull ExtraLoggingProperties properties) {
		this.properties = properties;
	}

	/**
	 * Creates bean for {@link SystemMemoryMonitoring}.
	 *
	 * @param systemUtilizationRepo Memory event log persistence layer.
	 * @return Bean.
	 */
	@Bean
	public SystemMemoryMonitoring monitoringMemAspect(
			final @NonNull MemoryEventRepository systemUtilizationRepo) {
		return new SystemMemoryMonitoring(systemUtilizationRepo, properties.ttl());
	}

	/**
	 * Creates bean for {@link SystemCPUMonitoring}.
	 *
	 * @param systemUtilizationRepo Cpu event log persistence layer.
	 * @return Bean.
	 */
	@Bean
	public SystemCPUMonitoring monitoringCpuAspect(
			final @NonNull CpuEventLogRepository systemUtilizationRepo) {
		return new SystemCPUMonitoring(systemUtilizationRepo, properties.ttl());
	}

	/**
	 * Creates bean for {@link EventLogAppender}.
	 *
	 * @param logRepository Default event log persistence layer.
	 * @return Bean.
	 */
	@Bean
	public EventLogAppender el4jLogAppender(final @NonNull EventLogRepository logRepository) {
		final List<PackageLevelLog> packages = properties.packages();
		if (Objects.isNull(packages)) {
			return null;
		}

		log.info("Initializing logging via package level and @Slf4j");

		final EventLogAppender eventLogAppender = new EventLogAppender(logRepository, properties.ttl());
		eventLogAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
		eventLogAppender.start();

		packages
				.forEach(packageFolder -> {
					final Logger logger = (Logger) LoggerFactory.getLogger(packageFolder.name());
					logger.setLevel(Level.toLevel(packageFolder.level()));
					logger.addAppender(eventLogAppender);
				});

		return eventLogAppender;
	}
}
