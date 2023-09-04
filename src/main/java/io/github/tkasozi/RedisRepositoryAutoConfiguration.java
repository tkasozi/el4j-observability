package io.github.tkasozi;

import io.github.tkasozi.domain.redis.CpuEventLog;
import io.github.tkasozi.domain.redis.EventLog;
import io.github.tkasozi.domain.redis.MemoryEventLog;
import io.github.tkasozi.repository.redis.CpuEventLogRepository;
import io.github.tkasozi.repository.redis.EventLogRepository;
import io.github.tkasozi.repository.redis.LogRepositoryImplementation;
import io.github.tkasozi.repository.redis.MemoryEventRepository;
import lombok.NonNull;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@AutoConfigureAfter({RedisAutoConfiguration.class, DefaultAutoConfiguration.class})
@ConditionalOnBean(LettuceConnectionFactory.class)
@Configuration
public class RedisRepositoryAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public RedisTemplate<String, Object> redisLogRedisTemplate(
            final @NonNull RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return template;
    }

    @ConditionalOnMissingBean
    @Bean
    public RedisRepositoryFactory redisRepositoryFactory(
            final @NonNull RedisKeyValueAdapter adapter,
            final @NonNull RedisMappingContext redisMappingContext) {
        final RedisKeyValueTemplate template = new RedisKeyValueTemplate(adapter, redisMappingContext);
        return new RedisRepositoryFactory(template);
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
