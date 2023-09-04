package io.github.tkasozi;

import io.github.tkasozi.annotation.TimeProfile;
import io.github.tkasozi.aspect.ExceptionLoggingAspect;
import io.github.tkasozi.aspect.InformationLoggingAspect;
import io.github.tkasozi.property.DefaultLoggingProperties;
import io.github.tkasozi.repository.redis.EventLogRepository;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.lang.NonNull;

/**
 * Configures and enables metrics created via Aspect.
 */
@AutoConfigureAfter(RedisRepositoryAutoConfiguration.class)
@ConditionalOnProperty("elf4j.metrics.logging.enabled")
@EnableAspectJAutoProxy
@Configuration
public class AspectAutoConfiguration {

	/**
	 * Creates a bean for default logging for example logging with {@link TimeProfile}.
	 *
	 * @param logRepository Repository used to save the log.
	 * @param properties    Default properties includes ttl.
	 * @return InformationLoggingAspect Bean.
	 */
	@Bean
	public InformationLoggingAspect el4jInformationLoggingAspect(
			final @NonNull EventLogRepository logRepository,
			final @NonNull DefaultLoggingProperties properties) {
		return new InformationLoggingAspect(logRepository, properties.ttl());
	}

	/**
	 * Creates a bean for logging all Exceptions.
	 *
	 * @param logRepository Repository used to save the log.
	 * @param properties    Default properties includes ttl.
	 * @return ExceptionLoggingAspect Bean.
	 */
	@ConditionalOnProperty("elf4j.metrics.logging.exceptions")
	@Bean
	public ExceptionLoggingAspect el4jExceptionLoggingAspect(
			final @NonNull EventLogRepository logRepository,
			final @NonNull DefaultLoggingProperties properties) {
		return new ExceptionLoggingAspect(logRepository, properties.ttl());
	}
}
