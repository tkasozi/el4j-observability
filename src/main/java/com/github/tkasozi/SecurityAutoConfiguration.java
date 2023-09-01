package com.github.tkasozi;

import com.github.tkasozi.property.MetricsProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@ConditionalOnProperty("elf4j.metrics.logging.enabled")
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
@Configuration
public class SecurityAutoConfiguration {

	private final MetricsProperties metricsProperties;

	public SecurityAutoConfiguration(final @NonNull MetricsProperties metricsProperties) {
		this.metricsProperties = metricsProperties;
	}

	@Order(0)
	@Bean
	public SecurityFilterChain endpointsSecurityFilterChain(final @NonNull HttpSecurity http) throws Exception {
		final String adminRole = metricsProperties.adminRole();
		http
				.securityMatcher(AntPathRequestMatcher.antMatcher(ResourceUtility.V1_API_PATTERN))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(new AntPathRequestMatcher(ResourceUtility.OBSERVER_V_1_UI_PERFORMANCE)).authenticated()
						.requestMatchers(new AntPathRequestMatcher(ResourceUtility.V1_API_PATTERN)).hasAuthority(adminRole)
				);

		return http.build();
	}

	@Order(1)
	@Bean
	public SecurityFilterChain resourceSecurityFilterChain(final @NonNull HttpSecurity http) throws Exception {
		final String adminRole = metricsProperties.adminRole();
		final String staticAssets = String.format("/%s/**", ResourceUtility.STATIC_RESOURCE_LOCATION);
		http
				.securityMatcher(AntPathRequestMatcher.antMatcher(HttpMethod.GET, staticAssets))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(staticAssets).hasAuthority(adminRole)
				);

		return http.build();
	}

	@Order(2)
	@Bean
	public SecurityFilterChain adminPageSecurityFilterChain(final @NonNull HttpSecurity http) throws Exception {
		final String adminRole = metricsProperties.adminRole();
		final String pageLocation = String.format("/%s", metricsProperties.adminPageName());
		http
				.securityMatcher(AntPathRequestMatcher.antMatcher(HttpMethod.GET, pageLocation))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(pageLocation).hasAuthority(adminRole)
				);

		return http.build();
	}
}
