package com.github.tkasozi;

import com.github.tkasozi.aspect.ExceptionLoggingAspect;
import com.github.tkasozi.aspect.InformationLoggingAspect;
import com.github.tkasozi.aspect.SystemCPUMonitoring;
import com.github.tkasozi.aspect.SystemMemoryMonitoring;
import com.github.tkasozi.endpoint.v1.CpuPerformanceEndpoints;
import com.github.tkasozi.endpoint.v1.MemoryPerformanceEndpoints;
import com.github.tkasozi.endpoint.v1.MetricsEndpoints;
import com.github.tkasozi.endpoint.v1.UiPerformanceEndpoints;
import com.github.tkasozi.repository.redis.CpuEventLogRepository;
import com.github.tkasozi.repository.redis.EventLogRepository;
import com.github.tkasozi.repository.redis.MemoryEventRepository;

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
