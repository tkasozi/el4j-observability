package org.el4j.observability.aspect;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.UUID;

import org.el4j.observability.ResourceUtility;
import org.el4j.observability.annotation.TimeProfile;
import org.el4j.observability.data.LoggedUserDetails;
import org.el4j.observability.domain.redis.EventLog;
import org.el4j.observability.repository.redis.EventLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.lang.NonNull;

/**
 * Implements AOP and persists logs via Repository.
 */
@Aspect
public class InformationLoggingAspect {

	private final EventLogRepository logRepository;
	private final Long ttl;

	/**
	 * Required args Constructor.
	 *
	 * @param logRepository persistence layer for Event log.
	 * @param ttl           Time to live.
	 */
	public InformationLoggingAspect(final EventLogRepository logRepository, final Long ttl) {
		this.logRepository = logRepository;
		this.ttl = ttl;
	}

	/**
	 * Supports using {@link org.el4j.observability.annotation.TimeProfile} on classes.
	 *
	 * @param proceedingJoinPoint The proceeding request.
	 * @return The object that was timed.
	 * @throws Throwable Expected exception.
	 */
	@Around("@within(org.el4j.observability.annotation.TimeProfile)")
	public Object logExecutionTimeType(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		final long start = System.currentTimeMillis();

		final Object proceed = proceedingJoinPoint.proceed();

		final long executionTime = System.currentTimeMillis() - start;

		final MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		final Method method = signature.getMethod();
		final Class<?> declaringClass = method.getDeclaringClass();
		final TimeProfile timeProfile = declaringClass.getAnnotation(TimeProfile.class);

		persistEvent(proceedingJoinPoint, timeProfile, executionTime);

		return proceed;
	}

	/**
	 * Supports using {@link org.el4j.observability.annotation.TimeProfile} on methods.
	 *
	 * @param proceedingJoinPoint The proceeding request.
	 * @return The object that was timed.
	 * @throws Throwable Expected exception.
	 */
	@Around("@annotation(org.el4j.observability.annotation.TimeProfile)")
	public Object logExecutionTimeMethod(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		final long start = System.currentTimeMillis();

		final Object proceed = proceedingJoinPoint.proceed();

		final long executionTime = System.currentTimeMillis() - start;

		final MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		final Method method = signature.getMethod();
		final TimeProfile timeProfile = method.getAnnotation(TimeProfile.class);

		persistEvent(proceedingJoinPoint, timeProfile, executionTime);

		return proceed;
	}


	/**
	 * Creates a log with the custom event name.
	 *
	 * @param proceedingJoinPoint The proceedingJoinPoint from which we get the method signature.
	 * @param timeProfile         Annotation which may also contain custom event name.
	 * @param executionTime       Time it took to execute the method.
	 */
	private void persistEvent(
			final ProceedingJoinPoint proceedingJoinPoint,
			final @NonNull TimeProfile timeProfile,
			final long executionTime
	) {
		final String methodName = proceedingJoinPoint.getSignature().getName();
		final String descriptionVal =
				String.format("[Method \"%s\" in class \"%s\" executed in \"%s\" ms]", methodName,
						proceedingJoinPoint.getTarget().getClass().getSimpleName(), executionTime);

		logRepository.save(getEventLogBuilder()
				.eventName(timeProfile.value().toUpperCase(Locale.US))
				.description(descriptionVal)
				.build());
	}

	/**
	 * Generates an event to be persisted.
	 *
	 * @return A log object to persist.
	 */
	@SuppressWarnings("java:S3740")
	private EventLog.EventLogBuilder<?> getEventLogBuilder() {
		final LoggedUserDetails loggedUser = ResourceUtility.getLoggedUserDetails();
		final EventLog.EventLogBuilder<?> builder = new EventLog.EventLogBuilder<>();
		return builder
				.id(UUID.randomUUID().toString())
				.ttl(ttl)
				.username(loggedUser.getUsername())
				.userAccess(loggedUser.getUserAccess());
	}
}
