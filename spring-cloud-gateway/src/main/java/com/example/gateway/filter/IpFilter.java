package com.example.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class IpFilter implements GlobalFilter, Ordered {

    static final Logger log = LoggerFactory.getLogger(IpFilter.class);

    private String getClientIp(ServerHttpRequest request) {
        var forwardFor = request.getHeaders().getFirst("X-Forwarded-For");
        if (forwardFor != null && !forwardFor.isEmpty()) {
            var slash = forwardFor.indexOf('/');
            if (slash >= 0) {
                forwardFor = forwardFor.substring(0, slash);
            }
            return forwardFor;
        }
        var readIp = request.getHeaders().getFirst("X-Real-IP");
        if (readIp != null && !readIp.isEmpty()) {
            return readIp;
        }
        var address = request.getRemoteAddress();
        if (address != null) {
            return address.getAddress().getHostAddress();
        }
        return "";
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var clientIp = getClientIp(exchange.getRequest());
        log.info("access {} {} from {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath(), clientIp);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
