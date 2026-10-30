package com.example.jenkins_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot application is running successfully!";
    }

    @GetMapping("/health")
    public String health() {
        return "Application Status: UP";
    }
}