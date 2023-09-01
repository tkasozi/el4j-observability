package io.github.tkasozi.endpoint.v1;

import io.github.tkasozi.repository.redis.EventLogRepository;
import lombok.RequiredArgsConstructor;
import io.github.tkasozi.domain.redis.EventLog;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests for UI performance metrics.
 */
@RequestMapping("/observer/v1/ui/performance")
@RequiredArgsConstructor
@RestController
@ResponseBody
public class UiPerformanceEndpoints {
	private final EventLogRepository eventLogRepository;

	/**
	 * Takes in a param and logs perf.
	 *
	 * @param pathName pathname for which metric is measured.
	 * @param time     time in milliseconds.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public void logPerformance(
			final @RequestParam("pathname") String pathName, final @RequestParam("time") float time) {
		final EventLog.EventLogBuilder<?> builder = new EventLog.EventLogBuilder<>();

		eventLogRepository.save(builder
				.eventName("UI_METRICS")
				.description(String.format("\"%s\" took %d ms to execute", pathName, Math.round(time)))
				.build());
	}
}
