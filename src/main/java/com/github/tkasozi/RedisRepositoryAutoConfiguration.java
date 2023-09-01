package com.github.tkasozi;

import com.github.tkasozi.domain.redis.CpuEventLog;
import com.github.tkasozi.domain.redis.EventLog;
import com.github.tkasozi.domain.redis.MemoryEventLog;
import com.github.tkasozi.repository.redis.CpuEventLogRepository;
import com.github.tkasozi.repository.redis.EventLogRepository;
import com.github.tkasozi.repository.redis.LogRepositoryImplementation;
import com.github.tkasozi.repository.redis.MemoryEventRepository;
import lombok.NonNull;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;

@AutoConfigureAfter(DefaultAutoConfiguration.class)
@ConditionalOnSingleCandidate(LettuceConnectionFactory.class)
@Configuration
public class RedisRepositoryAutoConfiguration {

	@ConditionalOnMissingBean
	@Bean
	public RedisTemplate<String, Object> redisLogRedisTemplate(
			final @NonNull RedisConnectionFactory connectionFactory) {
		final RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		return template;
	}

	@ConditionalOnMissingBean
	@Bean
	public KeyValueOperations keyValueOperations(
			final @NonNull KeyValueAdapter adapter) {
		return new KeyValueTemplate(adapter);
	}

	@Bean
	public RedisRepositoryFactory redisRepositoryFactory(final @NonNull KeyValueOperations keyValueOperations) {
		return new RedisRepositoryFactory(keyValueOperations);
	}

	@Bean
	public CpuEventLogRepository cpuEventLogRepository(
			final @NonNull RedisRepositoryFactory factory,
			final @NonNull KeyValueOperations keyValueOperations) {
		return factory.getRepository(CpuEventLogRepository.class,
				new LogRepositoryImplementation<>(keyValueOperations, CpuEventLog.class));
	}

	@Bean
	public MemoryEventRepository memoryEventRepository(
			final @NonNull RedisRepositoryFactory factory,
			final @NonNull KeyValueOperations keyValueOperations) {
		return factory.getRepository(MemoryEventRepository.class,
				new LogRepositoryImplementation<>(keyValueOperations, MemoryEventLog.class));
	}

	@Bean
	public EventLogRepository eventLogRepository(
			final @NonNull RedisRepositoryFactory factory,
			final @NonNull KeyValueOperations keyValueOperations) {
		return factory.getRepository(EventLogRepository.class,
				new LogRepositoryImplementation<>(keyValueOperations, EventLog.class));
	}
}
