package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient(autoRegister = false)
@SpringBootApplication
public class GatewayApplication {

    @Bean
    RouteLocator buildRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("https://httpbin.org/get"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
