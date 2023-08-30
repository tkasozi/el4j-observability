package org.el4j.observability.repository.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.NonNull;
import org.el4j.observability.domain.redis.EventLog;

import org.springframework.data.keyvalue.core.KeyValueOperations;

/**
 * Extra functionality.
 *
 * @param <T> {@link org.el4j.observability.domain.redis.EventLog}
 */
public class LogRepositoryImplementation<T extends EventLog> implements LogRepository<T> {

	private final KeyValueOperations keyValueOperations;
	private final Class<T> clazz;

	/**
	 * Required args constructor.
	 *
	 * @param keyValueOperations For redis operations.
	 * @param clazz              For casting stored data.
	 */
	public LogRepositoryImplementation(
			final @NonNull KeyValueOperations keyValueOperations, final @NonNull Class<T> clazz) {
		this.keyValueOperations = keyValueOperations;
		this.clazz = clazz;
	}

	/**
	 * Sorts by date.
	 *
	 * @return A list of sorted events.
	 */
	@Override
	public List<T> getAllSortedByDate() {
		final List<T> logs = new ArrayList<>();
		keyValueOperations.findAll(clazz).forEach(logs::add);

		return logs.stream()
				.filter(Objects::nonNull)
				.sorted().toList();
	}

	/**
	 * Filters out events by timeOffset.
	 *
	 * @param timeOffset Cut off time.
	 * @return List of logs within the given offset.
	 */
	@Override
	public List<T> getByTimeOffset(final Long timeOffset) {

		final List<T> logs = new ArrayList<>();
		keyValueOperations.findAll(clazz).forEach(log -> {
			final Date cutLineDate = new Date(System.currentTimeMillis() - 3600L * 1000 * timeOffset);
			if (Objects.nonNull(log) &&
					cutLineDate.compareTo(log.getTimestamp()) < 0) {
				logs.add(log);
			}
		});

		return logs.stream().sorted()
				.toList();
	}
}
