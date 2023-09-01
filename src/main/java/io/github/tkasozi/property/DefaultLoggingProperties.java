package io.github.tkasozi.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


/**
 * Properties auto configured as default.
 * @param enabled if true, overall logging is active.
 * @param exceptions if true, exception logging is active.
 * @param ttl Time To live.
 */
@ConfigurationProperties(prefix = "elf4j.metrics.logging")
@Validated
public record DefaultLoggingProperties(boolean enabled, boolean exceptions, Long ttl) {
}
