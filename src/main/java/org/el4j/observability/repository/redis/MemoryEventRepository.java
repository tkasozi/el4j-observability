package org.el4j.observability.repository.redis;


import org.el4j.observability.domain.redis.MemoryEventLog;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for Memory Event logs.
 */
@Repository
public interface MemoryEventRepository
		extends ListCrudRepository<MemoryEventLog, String>, LogRepository<MemoryEventLog> {
}
