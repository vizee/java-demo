package com.example.demo.controller;

import com.example.demo.config.HomeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeConfig config;

    @GetMapping("/hello")
    public String hello() {
        return config.getGreeting() + "world";
    }
}
