package org.el4j.observability.endpoint.v1;

import lombok.RequiredArgsConstructor;
import org.el4j.observability.aspect.SystemMemoryMonitoring;
import org.el4j.observability.data.Logs;
import org.el4j.observability.domain.redis.MemoryEventLog;
import org.el4j.observability.repository.redis.MemoryEventRepository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Handles requests for memory performance metrics.
 */
@RequestMapping("/observer/v1/utilization/memory")
@RequiredArgsConstructor
@RestController
@ResponseBody
public class MemoryPerformanceEndpoints {

	private final MemoryEventRepository memoryEventRepository;
	private final SystemMemoryMonitoring systemMemMonitoring;

	/**
	 * GET.
	 *
	 * @return List of Memory usage Events.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Logs<MemoryEventLog> allMemoryEndpoint() {
		return new Logs<>(memoryEventRepository.getAllSortedByDate());
	}

	/**
	 * GET by timeOffset.
	 *
	 * @param timeOffset  Time within which you want to know the events that happened
	 * @return List of Memory usage events
	 */
	@GetMapping(value = "/{timeOffset}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Logs<MemoryEventLog> getMemoryUsageByTimeOffset(@PathVariable("timeOffset") final Long timeOffset) {
		return new Logs<>(memoryEventRepository.getByTimeOffset(timeOffset));
	}

	/**
	 * GET number size of memory in MB.
	 * @return The size of the System's memory.
	 */
	@GetMapping(value = "/maxSize", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long getMaxMemoryUtilizationEvents() {
		return systemMemMonitoring.getMaxMemory();
	}
}
