package io.github.tkasozi.aspect;

import java.lang.management.ManagementFactory;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.github.tkasozi.domain.redis.CpuEventLog;
import io.github.tkasozi.repository.redis.CpuEventLogRepository;
import com.sun.management.OperatingSystemMXBean;
import lombok.NonNull;
import io.github.tkasozi.MetricsTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *  * Configures polling for cpu usage.
 */
public class SystemCPUMonitoring {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemCPUMonitoring.class);

	private final OperatingSystemMXBean resourceUtilizationBean;
	private final CpuEventLogRepository cpuEventLogRepository;
	private final Long ttl;

	/**
	 * Constructor.
	 * @param cpuEventLogRepository persistence layer for Event log.
	 * @param ttl Time to live.
	 */
	public SystemCPUMonitoring(
			final @NonNull CpuEventLogRepository cpuEventLogRepository,
			final @NonNull Long ttl
	) {
		this.ttl = ttl;
		this.cpuEventLogRepository = cpuEventLogRepository;
		this.resourceUtilizationBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	}

	/**
	 * Schedules an interval to poll cpu usage.
	 */
	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void logCpuUsage() {
		try {
			final var eventType = MetricsTypeEnum.CPU_UTILIZATION;
			final var usage = Math.round(resourceUtilizationBean.getCpuLoad() * 100);

			final CpuEventLog cpuLog = CpuEventLog.builder()
					.id(UUID.randomUUID().toString())
					.ttl(ttl)
					.eventName(eventType.name())
					.description(String.format("CPU Usage: %s", usage))
					.build();

			cpuEventLogRepository.save(cpuLog);
		}
		catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			LOGGER.error("Error logging CPU event: {}", e.getMessage());
		}
	}
}
