package com.github.tkasozi;

import lombok.NonNull;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration(proxyBeanMethods = false)
@SpringBootConfiguration
@ContextConfiguration
@EnableAutoConfiguration
@Import(LettuceConnectionFactory.class)
@ActiveProfiles("test")
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class DemoApplication {
	@Bean
		/* default */SecurityFilterChain filterChain(final @NonNull HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
