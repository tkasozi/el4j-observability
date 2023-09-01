package io.github.tkasozi.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "elf4j.metrics")
@Validated
public record MetricsProperties(String adminRole, String adminPageName) {
}
