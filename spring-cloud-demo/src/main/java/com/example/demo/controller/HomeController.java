package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RefreshScope
public class HomeController {

    @Value("${app.home.helloPrefix:}")
    String helloPrefix;

    @GetMapping("/hello")
    public String getHello() {
        return helloPrefix + "World";
    }
}
