package org.el4j.observability;

import org.el4j.observability.aspect.ExceptionLoggingAspect;
import org.el4j.observability.aspect.InformationLoggingAspect;
import org.el4j.observability.repository.redis.EventLogRepository;

import org.springframework.beans.factory.annotation.Value;
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
	 * Creates a bean for default logging for example logging with {@link org.el4j.observability.annotation.TimeProfile}.
	 *
	 * @param logRepository Repository used to save the log.
	 * @param ttl           Time To Live.
	 * @return KesselRunInformationLoggingAspect Bean.
	 */
	@Bean
	public InformationLoggingAspect el4jInformationLoggingAspect(
			final @NonNull EventLogRepository logRepository,
			final @NonNull @Value("${elf4j.metrics.logging.ttl}") Long ttl) {
		return new InformationLoggingAspect(logRepository, ttl);
	}

	/**
	 * Creates a bean for logging all Exceptions.
	 *
	 * @param logRepository Repository used to save the log.
	 * @param ttl           Time To Live.
	 * @return KesselRunExceptionLoggingAspect Bean.
	 */
	@ConditionalOnProperty("elf4j.metrics.logging.exceptions")
	@Bean
	public ExceptionLoggingAspect el4jExceptionLoggingAspect(
			final @NonNull EventLogRepository logRepository,
			final @NonNull @Value("${elf4j.metrics.logging.ttl}") Long ttl) {
		return new ExceptionLoggingAspect(logRepository, ttl);
	}
}
