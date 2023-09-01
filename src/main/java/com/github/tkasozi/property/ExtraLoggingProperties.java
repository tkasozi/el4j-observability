package com.github.tkasozi.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Extra logging Properties.
 *
 * @param enabled  if true, extra logging is enabled.
 * @param ttl      Time To Live.
 * @param packages Packages that will be logged.
 */
@ConfigurationProperties(prefix = "elf4j.metrics.logging.extra")
@Validated
public record ExtraLoggingProperties(boolean enabled, Long ttl, List<PackageLevelLog> packages) {
}
