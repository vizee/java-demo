package com.example.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    static final Logger log = LoggerFactory.getLogger(AuthGatewayFilterFactory.class);

    public AuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            var path = request.getPath().value();
            if (Arrays.stream(config.ignorePaths).anyMatch(path::startsWith)) {
                return chain.filter(exchange);
            }
            var authorization = request.getHeaders().getFirst("Authorization");
            log.info("auth {} with {}", path, authorization);
            if (authorization == null || authorization.isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange.mutate().request(builder -> builder.header(config.authHeader, authorization)).build());
        };
    }

    public static class Config {
        String authHeader;
        String[] ignorePaths;

        public String getAuthHeader() {
            return this.authHeader;
        }

        public void setAuthHeader(String authHeader) {
            this.authHeader = authHeader;
        }

        public String[] getIgnorePaths() {
            return this.ignorePaths;
        }

        public void setIgnorePaths(String[] ignorePaths) {
            this.ignorePaths = ignorePaths;
        }
    }
}
