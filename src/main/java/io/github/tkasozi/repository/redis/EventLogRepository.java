package io.github.tkasozi.repository.redis;

import io.github.tkasozi.domain.redis.EventLog;

import org.springframework.data.repository.ListCrudRepository;

/**
 * Persistence layer for Event logs.
 */
public interface EventLogRepository
		extends ListCrudRepository<EventLog, String>, LogRepository<EventLog> {
}
