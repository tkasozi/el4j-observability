package io.github.tkasozi.repository.redis;


import io.github.tkasozi.domain.redis.MemoryEventLog;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for Memory Event logs.
 */
@Repository
public interface MemoryEventRepository
		extends ListCrudRepository<MemoryEventLog, String>, LogRepository<MemoryEventLog> {
}
