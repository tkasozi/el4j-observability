package org.el4j.observability.repository.redis;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;


/**
 * Interface for implemented repository.
 * @param <T> extends type {@link org.el4j.observability.domain.redis.EventLog}
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
