package com.github.tkasozi.repository.redis;

import com.github.tkasozi.domain.redis.EventLog;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for Event logs.
 */
@Repository
public interface EventLogRepository
		extends ListCrudRepository<EventLog, String>, LogRepository<EventLog> {
}
