package io.github.tkasozi.repository.redis;


import io.github.tkasozi.domain.redis.MemoryEventLog;

import org.springframework.data.repository.ListCrudRepository;

/**
 * Persistence layer for Memory Event logs.
 */
public interface MemoryEventRepository
		extends ListCrudRepository<MemoryEventLog, String>, LogRepository<MemoryEventLog> {
}
