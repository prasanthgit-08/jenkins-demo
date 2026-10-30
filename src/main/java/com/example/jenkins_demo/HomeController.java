package com.example.jenkins_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello! Jenkins Poll SCM is working!!";
    }

    @GetMapping("/health")
    public String health() {
        return "Application Jenkins Status is good!";
    }
}
