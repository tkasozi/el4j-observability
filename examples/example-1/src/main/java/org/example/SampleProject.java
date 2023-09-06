package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

@SpringBootApplication
public class SampleProject {
    final Logger log = LoggerFactory.getLogger(SampleProject.class);

    public static void main(String[] args) {
        SpringApplication.run(SampleProject.class, args);
    }

    @EventListener
        /* default */ void successfullyAuthenticatedEvent(final AuthenticationSuccessEvent successEvent) {
        log.info("[{}]", successEvent);
    }
}
