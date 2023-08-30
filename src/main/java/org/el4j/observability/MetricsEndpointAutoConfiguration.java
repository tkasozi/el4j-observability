package org.el4j.observability;

import org.el4j.observability.aspect.ExceptionLoggingAspect;
import org.el4j.observability.aspect.InformationLoggingAspect;
import org.el4j.observability.aspect.SystemCPUMonitoring;
import org.el4j.observability.aspect.SystemMemoryMonitoring;
import org.el4j.observability.endpoint.v1.CpuPerformanceEndpoints;
import org.el4j.observability.endpoint.v1.MemoryPerformanceEndpoints;
import org.el4j.observability.endpoint.v1.MetricsEndpoints;
import org.el4j.observability.endpoint.v1.UiPerformanceEndpoints;
import org.el4j.observability.repository.redis.CpuEventLogRepository;
import org.el4j.observability.repository.redis.EventLogRepository;
import org.el4j.observability.repository.redis.MemoryEventRepository;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;


@AutoConfigureAfter({ExtraLoggingAutoConfiguration.class, AspectAutoConfiguration.class})
@Configuration
public class MetricsEndpointAutoConfiguration {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MetricsEndpointAutoConfiguration.class);

	@ConditionalOnBean({InformationLoggingAspect.class, ExceptionLoggingAspect.class})
	@Bean
	public MetricsEndpoints userMetricsEndpoint(final @NonNull EventLogRepository logRepository) {
		log.info("Initializing user metrics endpoints");
		return new MetricsEndpoints(logRepository);
	}

	@ConditionalOnBean(SystemCPUMonitoring.class)
	@Bean
	public CpuPerformanceEndpoints cpuPerformanceEndpoints(
			final @NonNull CpuEventLogRepository cpuEventLogRepository) {
		log.info("Initializing cpu performance endpoints");
		return new CpuPerformanceEndpoints(cpuEventLogRepository);
	}

	@ConditionalOnBean(SystemMemoryMonitoring.class)
	@Bean
	public MemoryPerformanceEndpoints memoryPerformanceEndpoints(
			final @NonNull MemoryEventRepository memoryEventRepository,
			final @NonNull SystemMemoryMonitoring systemMemoryMonitoring) {
		log.info("Initializing memory performance endpoints");
		return new MemoryPerformanceEndpoints(memoryEventRepository, systemMemoryMonitoring);
	}

	@ConditionalOnBean({InformationLoggingAspect.class, ExceptionLoggingAspect.class})
	@Bean
	public UiPerformanceEndpoints uiPerformanceEndpoints(final @NonNull EventLogRepository logRepository) {
		log.info("Initializing UI metrics tracking endpoints");
		return new UiPerformanceEndpoints(logRepository);
	}
}
