package io.github.tkasozi.aspect;

import java.util.UUID;

import io.github.tkasozi.data.LoggedUserDetails;
import io.github.tkasozi.domain.redis.EventLog;
import io.github.tkasozi.repository.redis.EventLogRepository;
import lombok.NonNull;
import io.github.tkasozi.MetricsTypeEnum;
import io.github.tkasozi.ResourceUtility;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * This class implements an aspect to log exceptions
 * that occur in classes annotated with Controller, Service,
 * and RestController.
 */
@Aspect
public class ExceptionLoggingAspect {

	private final EventLogRepository logRepository;
	private final Long ttl;

	/**
	 * Constructor.
	 * @param logRepository persistence layer for Event log.
	 * @param ttl Time to live.
	 */
	public ExceptionLoggingAspect(final @NonNull EventLogRepository logRepository, final Long ttl) {
		this.logRepository = logRepository;
		this.ttl = ttl;
	}

	/**
	 * Pointcut to exclude methods in this library.
	 */
	@Pointcut("!within(io.github.tkasozi.*)")
	public void excludeOwnMethods() {
		// empty
	}

	/**
	 * Handles exceptions that happen in Controller, Service,
	 * and RestController annotated classes.
	 *
	 * @param joinPoint The aspect that happened.
	 * @param exception The exception that was thrown.
	 */
	@AfterThrowing(value = "excludeOwnMethods() &&" +
			"@within(org.springframework.web.bind.annotation.RestController) ||" +
			"@within(org.springframework.stereotype.Controller) ||" +
			"@within(org.springframework.stereotype.Service)", throwing = "exception")
	public void logExceptions(final JoinPoint joinPoint, final Throwable exception) {
		final var formattedErrorMessage = String.format("[Method: '%s' failed with the following message: '%s']",
				joinPoint.getSignature().getName(),
				exception.getMessage());

		final LoggedUserDetails loggedUser = ResourceUtility.getLoggedUserDetails();
		final EventLog.EventLogBuilder<?> builder = new EventLog.EventLogBuilder<>();

		logRepository.save(builder
				.id(UUID.randomUUID().toString())
				.ttl(ttl)
				.username(loggedUser.getUsername())
				.userAccess(loggedUser.getUserAccess())
				.eventName(MetricsTypeEnum.EXCEPTION_EVENT.getValue())
				.description(formattedErrorMessage)
				.build());
	}
}
