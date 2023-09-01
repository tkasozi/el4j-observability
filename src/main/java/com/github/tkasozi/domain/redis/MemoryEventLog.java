package com.github.tkasozi.domain.redis;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "MemoryEventLog", timeToLive = 60)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemoryEventLog extends EventLog {
	@Serial
	private static final long serialVersionUID = 4L;

	private String percentUsage;

	public MemoryEventLog(final @NonNull MemoryUtilizationBuilder builder) {
		super(builder);
		this.percentUsage = builder.usage;
	}

	public static MemoryUtilizationBuilder builder() {
		return new MemoryUtilizationBuilder();
	}

	public static class MemoryUtilizationBuilder extends EventLog.EventLogBuilder<MemoryUtilizationBuilder> {
		private String usage;

		public MemoryUtilizationBuilder percentUsage(final String percentUsage) {
			this.usage = percentUsage;
			return this;
		}

		@Override
		public MemoryEventLog build() {
			return new MemoryEventLog(this);
		}
	}
}
