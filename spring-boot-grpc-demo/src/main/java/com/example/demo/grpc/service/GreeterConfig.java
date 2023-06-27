package com.example.demo.grpc.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "greeter")
public class GreeterConfig {

    @Value("${prefix:Hello, }")
    private String prefix;
}
