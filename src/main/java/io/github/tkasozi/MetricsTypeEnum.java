package io.github.tkasozi;

import lombok.Getter;

/***
 * Types of events supported.
 * @author tkasozi  Talik. A. Kasozi talik.aziizi@gmail.com
 */
@Getter
public enum MetricsTypeEnum {
	/**
	 * This event is used to log a successful authorization event.
	 */
	SUCCESSFUL_AUTHORIZATION_EVENT("SUCCESSFUL_AUTHORIZATION_EVENT"),
	/**
	 * This event is used to log unauthorized user access event.
	 */
	UNSUCCESSFUL_AUTHORIZATION_EVENT("UNSUCCESSFUL_AUTHORIZATION_EVENT"),
	/**
	 * Use this event to signal a successful event happened.
	 */
	SUCCESSFUL_RESULT_EVENT("SUCCESSFUL_RESULT_EVENT"),
	/**
	 * This event is used to log unsuccessful request event.
	 */
	UNSUCCESSFUL_RESULT_EVENT("UNSUCCESSFUL_RESULT_EVENT"),
	/**
	 * This event is used to log performance event.
	 */
	PERFORMANCE_EVENT("PERFORMANCE_EVENT"),
	/**
	 * This event is used to log exception event.
	 */
	EXCEPTION_EVENT("EXCEPTION_EVENT"),
	/**
	 * This event is used for internal logging event.
	 */
	UTILITY_LOGGING_EVENT("UTILITY_LOGGING_EVENT"),
	/**
	 * This event is used to log cpu utilization event.
	 */
	CPU_UTILIZATION("CPU_UTILIZATION"),
	/**
	 * This event is used to log memory utilization event.
	 */
	MEMORY_UTILIZATION("MEMORY_UTILIZATION");

	private final String value;

	MetricsTypeEnum(final String value) {
		this.value = value;
	}
}
