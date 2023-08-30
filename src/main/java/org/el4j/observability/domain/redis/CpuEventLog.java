package org.el4j.observability.domain.redis;

import java.io.Serial;
import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.data.redis.core.RedisHash;

/**
 * CPU log.
 */
@RedisHash(value = "CpuEventLog", timeToLive = 60)
public class CpuEventLog extends EventLog {
	@Serial
	private static final long serialVersionUID = 3L;


	/**
	 * sets and get usage percentage as a {@link java.lang.String} value.
	 *
	 * @param percentUsage String value of usage.
	 * @return percentUsage as a String.
	 */
	@Getter
	@Setter
	private String percentUsage;

	/**
	 * constructor.
	 */
	public CpuEventLog() {
		super();
	}

	/**
	 * Builder getter.
	 *
	 * @return builder.
	 */
	public static CPUUtilizationBuilder builder() {
		return new CPUUtilizationBuilder();
	}

	/**
	 * Constructor builder.
	 *
	 * @param builder .
	 */
	public CpuEventLog(final @NonNull CPUUtilizationBuilder builder) {
		super(builder);
		this.percentUsage = builder.usage;
	}

	/**
	 * Is equal.
	 *
	 * @param o The log to compare with.
	 * @return true is equal, false otherwise.
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CpuEventLog that)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		return Objects.equals(getPercentUsage(), that.getPercentUsage());
	}

	/**
	 * Hash code using default.
	 *
	 * @return hasCode.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getPercentUsage());
	}

	/**
	 * Cpu log Builder.
	 */
	public static class CPUUtilizationBuilder extends EventLog.EventLogBuilder<CPUUtilizationBuilder> {
		private String usage;

		/**
		 * set usage.
		 *
		 * @param percentUsage usage number as a String.
		 * @return builder.
		 */
		public CPUUtilizationBuilder percentUsage(final String percentUsage) {
			this.usage = percentUsage;
			return this;
		}

		/**
		 * creates builder.
		 *
		 * @return builder.
		 */
		@Override
		public CpuEventLog build() {
			return new CpuEventLog(this);
		}
	}
}
