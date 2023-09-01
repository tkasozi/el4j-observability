package com.github.tkasozi.endpoint.v1;

import java.util.List;

import com.github.tkasozi.repository.redis.EventLogRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import com.github.tkasozi.data.Logs;
import com.github.tkasozi.domain.redis.EventLog;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests for user generated event.
 */
//@RestControllerEndpoint(id = "user-metrics") // to create endpoint via "/actuator/user-metrics"
@RequestMapping("/observer/v1/utilization/user")
@RequiredArgsConstructor
@RestController
@ResponseBody
public class MetricsEndpoints {

	private final EventLogRepository eventLogRepository;

	/**
	 * GET.
	 *
	 * @return List of user generated events.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Logs<EventLog> allEventsEndpoint() {
		return new Logs<>(eventLogRepository.getAllSortedByDate());
	}

	/**
	 * Given ids to delete, deletes from redis/db.
	 *
	 * @param uuids       list of uuids to be deleted.
	 * @return List of user generated events.
	 */
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Logs<EventLog> delete(
			final @RequestBody @NotEmpty List<String> uuids) {
		eventLogRepository.deleteAllById(uuids);
		return new Logs<>(eventLogRepository.getAllSortedByDate());
	}
}
