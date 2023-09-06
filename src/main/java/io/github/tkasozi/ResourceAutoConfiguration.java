package io.github.tkasozi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@ConditionalOnProperty("elf4j.metrics.logging.enabled")
@Configuration
public class ResourceAutoConfiguration implements WebMvcConfigurer {
	private final String adminPage;


	public ResourceAutoConfiguration(
			final @Value("${elf4j.metrics.adminPageName}") String adminPageRoute
	) {
		this.adminPage = adminPageRoute;
	}

	@Override
	public void addViewControllers(final @NonNull ViewControllerRegistry registry) {
		registry.addViewController(String.format("/%s", adminPage))
				.setViewName(ResourceUtility.DEFAULT_VIEW);
	}

	@Bean
	public ViewResolver internalResourceViewResolver() {
		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(String.format("/%s/", ResourceUtility.STATIC_RESOURCE_LOCATION));
		resolver.setSuffix(".html");
		return resolver;
	}

	@Bean
	public SimpleUrlHandlerMapping el4jLoggerHandlerMapping() {
		final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);

		final Map<String, ResourceHttpRequestHandler> map = new HashMap<>();
		final var handler = el4jLoggerRequestHandler();
		map.put(String.format("/%s/**", ResourceUtility.STATIC_RESOURCE_LOCATION), handler);
		mapping.setUrlMap(map);

		return mapping;
	}

	@Bean
	public ResourceHttpRequestHandler el4jLoggerRequestHandler() {
		final ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
		final List<String> staticLocations = List.of(
				String.format("classpath:META-INF/%s/", ResourceUtility.STATIC_RESOURCE_LOCATION));

		requestHandler.setLocationValues(staticLocations);
		return requestHandler;
	}
}
