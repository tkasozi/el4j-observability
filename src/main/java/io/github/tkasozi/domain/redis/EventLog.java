package io.github.tkasozi.domain.redis;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import io.github.tkasozi.domain.BaseEntity;
import lombok.Getter;
import lombok.NonNull;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

/**
 * Event Log.
 */
@RedisHash(value = "Logs", timeToLive = 60)
@Getter
public class EventLog extends BaseEntity implements Serializable, Comparable<EventLog> {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Time to live.
	 *
	 * @return Seconds as {@link java.lang.Long}.
	 */
	@TimeToLive
	@Indexed
	protected Long ttl;

	/**
	 * Event description.
	 *
	 * @return String description.
	 */
	protected String description;
	/**
	 * Event name.
	 *
	 * @return String name.
	 */
	protected String eventName;
	/**
	 * Username for user who caused the event.
	 *
	 * @return String username.
	 */
	protected String username;
	/**
	 * User access for user who caused the event.
	 *
	 * @return String access.
	 */
	protected String userAccess;
	/**
	 * Timestamp when the event happened.
	 *
	 * @return {@link Date}.
	 */
	protected Date timestamp;
	/**
	 * Formatted timestamp when the event happened.
	 *
	 * @return formatted as MM/dd/yyyy HH:mm:ss.
	 */
	protected String formattedTimeStamp;

	/**
	 * Constructor.
	 */
	public EventLog() {
		super();
	}

	/**
	 * Builder to construct from.
	 *
	 * @param builder .
	 */
	public EventLog(final @NonNull EventLogBuilder<?> builder) {
		super(builder);
		this.ttl = builder.timeToLive;
		this.description = builder.descr;
		this.eventName = builder.name;
		this.username = builder.userIdentifier;
		this.userAccess = builder.access;
		this.timestamp = builder.timestamp;
		this.formattedTimeStamp = builder.displayDate;
	}

	/**
	 * Compares in favor of the newest.
	 *
	 * @param o the object to be compared.
	 * @return 0, 1, -1.
	 */
	@Override
	public int compareTo(final @NonNull EventLog o) {
		return o.getTimestamp().compareTo(timestamp);
	}

	/**
	 * Equal method.
	 *
	 * @param o Should be of this type.
	 * @return true if equal, false otherwise.
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof EventLog eventLog)) {
			return false;
		}
		return Objects.equals(getTtl(), eventLog.getTtl()) &&
				Objects.equals(getDescription(), eventLog.getDescription()) &&
				Objects.equals(getEventName(), eventLog.getEventName()) &&
				Objects.equals(getUsername(), eventLog.getUsername()) &&
				Objects.equals(getUserAccess(), eventLog.getUserAccess()) &&
				Objects.equals(getTimestamp(), eventLog.getTimestamp()) &&
				Objects.equals(getFormattedTimeStamp(), eventLog.getFormattedTimeStamp());
	}

	/**
	 * Hash code.
	 *
	 * @return Hash integer.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getTtl(),
				getDescription(),
				getEventName(),
				getUsername(),
				getUserAccess(),
				getTimestamp(),
				getFormattedTimeStamp());
	}

	/**
	 * Event log builder.
	 *
	 * @param <T> type.
	 */
	@SuppressWarnings("unchecked")
	public static class EventLogBuilder<T extends EventLogBuilder<T>> extends BaseEntity.BaseEntityBuilder<EventLogBuilder<T>> {
		private Long timeToLive;
		private String descr;
		private String name;
		private String userIdentifier;
		private String access;
		private final Date timestamp;
		private final String displayDate;

		/**
		 * Constructor only sets timestamp.
		 */
		public EventLogBuilder() {
			super();
			this.timestamp = Date.from(Instant.now());
			this.displayDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US)
					.format(this.timestamp);
		}

		/**
		 * set Time to live.
		 *
		 * @param ttl time to live.
		 * @return builder.
		 */
		public T ttl(final Long ttl) {
			this.timeToLive = ttl;
			return (T) this;
		}

		/**
		 * set description.
		 *
		 * @param description String description.
		 * @return builder.
		 */
		public T description(final String description) {
			this.descr = description;
			return (T) this;
		}

		/**
		 * set eventName.
		 *
		 * @param eventName .
		 * @return builder.
		 */
		public T eventName(final String eventName) {
			this.name = eventName;
			return (T) this;
		}

		/**
		 * set username.
		 *
		 * @param username .
		 * @return builder.
		 */
		public T username(final String username) {
			this.userIdentifier = username;
			return (T) this;
		}

		/**
		 * set userAccess.
		 *
		 * @param userAccess .
		 * @return builder.
		 */
		public T userAccess(final String userAccess) {
			this.access = userAccess;
			return (T) this;
		}

		/**
		 * build method.
		 *
		 * @return event log.
		 */
		public EventLog build() {
			return new EventLog(this);
		}
	}
}
