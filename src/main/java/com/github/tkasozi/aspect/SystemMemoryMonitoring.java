package com.github.tkasozi.aspect;

import java.lang.management.ManagementFactory;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.github.tkasozi.domain.redis.MemoryEventLog;
import com.github.tkasozi.repository.redis.MemoryEventRepository;
import com.sun.management.OperatingSystemMXBean;
import lombok.NonNull;
import com.github.tkasozi.MetricsTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configures polling for system memory.
 */
public class SystemMemoryMonitoring {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemMemoryMonitoring.class);

	private final OperatingSystemMXBean resourceUtilizationBean;
	private final MemoryEventRepository memoryEventRepository;
	private final Long ttl;

	/**
	 * Constructor.
	 *
	 * @param memoryEventRepository persistence layer for Event log.
	 * @param ttl                   Time to live.
	 */
	public SystemMemoryMonitoring(final @NonNull MemoryEventRepository memoryEventRepository, final @NonNull Long ttl) {
		this.ttl = ttl;
		this.memoryEventRepository = memoryEventRepository;
		this.resourceUtilizationBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}

	/**
	 * Schedules an interval to poll memory usage.
	 */
	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void logMemUsage() {
		try {
			final double percentage = ((double) resourceUtilizationBean.getFreeMemorySize() /
					(double) resourceUtilizationBean.getTotalMemorySize()) * 100;
			final MemoryEventLog memoryLog = MemoryEventLog.builder()
					.id(UUID.randomUUID().toString())
					.ttl(ttl)
					.eventName(MetricsTypeEnum.MEMORY_UTILIZATION.name())
					.description(String.format("Memory Usage: %s", percentage))
					.build();
			memoryEventRepository.save(memoryLog);
		}
		catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			LOGGER.error("Error logging Memory event: {}", e.getMessage());
		}
	}

	public Long getMaxMemory() {
		return resourceUtilizationBean.getTotalMemorySize() / 1_048_576;
	}
}
