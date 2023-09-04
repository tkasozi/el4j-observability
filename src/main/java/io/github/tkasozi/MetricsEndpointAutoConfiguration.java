package io.github.tkasozi;

import io.github.tkasozi.aspect.ExceptionLoggingAspect;
import io.github.tkasozi.aspect.InformationLoggingAspect;
import io.github.tkasozi.aspect.SystemCPUMonitoring;
import io.github.tkasozi.aspect.SystemMemoryMonitoring;
import io.github.tkasozi.endpoint.v1.CpuPerformanceEndpoints;
import io.github.tkasozi.endpoint.v1.MemoryPerformanceEndpoints;
import io.github.tkasozi.endpoint.v1.MetricsEndpoints;
import io.github.tkasozi.endpoint.v1.UiPerformanceEndpoints;
import io.github.tkasozi.repository.redis.CpuEventLogRepository;
import io.github.tkasozi.repository.redis.EventLogRepository;
import io.github.tkasozi.repository.redis.MemoryEventRepository;

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
		log.info("Initializing metrics endpoints");
		return new MetricsEndpoints(logRepository);
	}

	@ConditionalOnBean({InformationLoggingAspect.class, ExceptionLoggingAspect.class})
	@Bean
	public UiPerformanceEndpoints uiPerformanceEndpoints(final @NonNull EventLogRepository logRepository) {
		return new UiPerformanceEndpoints(logRepository);
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
}
