package org.el4j.observability.repository.redis;

import org.el4j.observability.domain.redis.EventLog;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for Event logs.
 */
@Repository
public interface EventLogRepository
		extends ListCrudRepository<EventLog, String>, LogRepository<EventLog> {
}
