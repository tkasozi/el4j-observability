package org.el4j.observability.property;

import org.springframework.boot.context.properties.bind.DefaultValue;

public record PackageLevelLog(String name, @DefaultValue("ERROR") String level) {
}
