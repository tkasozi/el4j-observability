package io.github.tkasozi;

import io.github.tkasozi.property.DefaultLoggingProperties;
import io.github.tkasozi.property.ExtraLoggingProperties;
import io.github.tkasozi.property.MetricsProperties;
import io.github.tkasozi.property.PackageLevelLog;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configures properties and create a property converter.
 */
@PropertySource({"classpath:el4j-observability-default-logging.properties", "classpath:el4j-observability-extra-logging.properties"})
@EnableConfigurationProperties({
		DefaultLoggingProperties.class,
		ExtraLoggingProperties.class,
		MetricsProperties.class,

})
@Configuration
public class DefaultAutoConfiguration {
	/**
	 * Creates bean of type {@link PropertyConverter}
	 * which converts Strings to {@link PackageLevelLog}.
	 *
	 * @return {@link PropertyConverter}.
	 */
	@Bean
	public PropertyConverter propertyConverter() {
		return new PropertyConverter();
	}
}
