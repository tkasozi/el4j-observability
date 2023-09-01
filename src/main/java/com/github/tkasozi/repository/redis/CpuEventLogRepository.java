package com.github.tkasozi.repository.redis;

import com.github.tkasozi.domain.redis.CpuEventLog;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer for Cpu Event logs.
 */
@Repository
public interface CpuEventLogRepository
		extends ListCrudRepository<CpuEventLog, String>, LogRepository<CpuEventLog> {
}
