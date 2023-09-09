package io.github.tkasozi.property;

import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Per package level log customization.
 *
 * @param name The name of the package
 *
 * @param level The level of log you want to capture.
 */
public record PackageLevelLog(String name, @DefaultValue("ERROR") String level) {
}
