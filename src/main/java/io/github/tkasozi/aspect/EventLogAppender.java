package io.github.tkasozi.aspect;


import java.util.UUID;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.github.tkasozi.domain.redis.EventLog;
import io.github.tkasozi.repository.redis.EventLogRepository;
import lombok.NonNull;
import io.github.tkasozi.MetricsTypeEnum;
import io.github.tkasozi.ResourceUtility;
import io.github.tkasozi.data.LoggedUserDetails;

/**
 * AppenderBase For saving package level logs.
 */
public class EventLogAppender extends AppenderBase<ILoggingEvent> {

	private final EventLogRepository logRepository;
	private final Long ttl;

	/**
	 * Required args Constructor.
	 *
	 * @param logRepository persistence layer for Event log.
	 * @param ttl           Time to live.
	 */
	public EventLogAppender(final @NonNull EventLogRepository logRepository, final @NonNull Long ttl) {
		super();
		this.logRepository = logRepository;
		this.ttl = ttl;
	}

	/**
	 * Saves log created with Slf4j.
	 *
	 * @param eventObject log.
	 */
	@Override
	protected void append(final @NonNull ILoggingEvent eventObject) {
		final LoggedUserDetails loggedUser = ResourceUtility.getLoggedUserDetails();
		final String eventType = MetricsTypeEnum.UTILITY_LOGGING_EVENT.getValue();
		final EventLog.EventLogBuilder<?> builder = new EventLog.EventLogBuilder<>();
		logRepository.save(builder
				.id(UUID.randomUUID().toString())
				.ttl(ttl)
				.username(loggedUser.getUsername())
				.userAccess(loggedUser.getUserAccess())
				.eventName(eventType)
				.description("[" + eventObject.getFormattedMessage() + " completed successfully.]")
				.build());
	}
}
