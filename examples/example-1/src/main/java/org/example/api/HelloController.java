package org.example.api;

import io.github.tkasozi.annotation.TimeProfile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/metrics")
@RestController
public class HelloController {

	@TimeProfile("performance_test")
	@GetMapping
	public String metricsPerformanceTest() {
		return "Greetings from Spring Boot!";
	}

}