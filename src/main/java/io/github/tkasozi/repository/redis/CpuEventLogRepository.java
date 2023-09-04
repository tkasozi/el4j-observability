package io.github.tkasozi.repository.redis;

import io.github.tkasozi.domain.redis.CpuEventLog;

import org.springframework.data.repository.ListCrudRepository;

/**
 * Persistence layer for Cpu Event logs.
 */
public interface CpuEventLogRepository
		extends ListCrudRepository<CpuEventLog, String>, LogRepository<CpuEventLog> {
}
