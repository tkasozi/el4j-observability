package io.github.tkasozi;

import io.github.tkasozi.property.PackageLevelLog;
import lombok.NonNull;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

@ConfigurationPropertiesBinding
public class PropertyConverter implements Converter<String, PackageLevelLog> {

	@Override
	public PackageLevelLog convert(final @NonNull String from) {
		Assert.notNull(from, "Please provide package name and Log level.");

		final String[] data = from.split(",");

		if (data.length == 2) {
			return new PackageLevelLog(data[0], data[1]);
		}
		return null;
	}
}
