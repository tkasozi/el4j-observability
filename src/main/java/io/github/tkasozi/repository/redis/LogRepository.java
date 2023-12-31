package io.github.tkasozi.repository.redis;

import java.util.List;

import io.github.tkasozi.domain.redis.EventLog;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * Interface for implemented repository.
 * @param <T> extends type {@link EventLog}
 */
@NoRepositoryBean
public interface LogRepository<T> {
	/**
	 * Sorts by date.
	 *
	 * @return A list of sorted events.
	 */
	List<T> getAllSortedByDate();
	/**
	 * Filters out events by timeOffset.
	 *
	 * @param timeOffset Cut off time.
	 * @return List of logs within the given offset.
	 */
	List<T> getByTimeOffset(Long timeOffset);
}
