package org.example;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Aspect
public class SampleProject {
    public static void main(String[] args) {
        SpringApplication.run(SampleProject.class, args);
    }
}