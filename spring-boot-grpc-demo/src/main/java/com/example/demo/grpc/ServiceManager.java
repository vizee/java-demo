package com.example.demo.grpc;

import io.grpc.BindableService;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ServiceManager {

    @Autowired
    private ServiceConfig config;

    private Server server;

    public void stop() throws InterruptedException {
        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    public void spin() throws InterruptedException {
        server.awaitTermination();
    }

    public void start(Map<String, Object> grpcServiceBeanMap) throws IOException {
        var serverBuilder = Grpc.newServerBuilderForPort(config.getPort(), InsecureServerCredentials.create());
        for (Object bean : grpcServiceBeanMap.values()) {
            serverBuilder.addService((BindableService) bean);
            log.info("Add GRPC service: {}", bean.getClass().getSimpleName());
        }
        server = serverBuilder.build();
        server.start();
        log.info("Server started on port {}", config.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down server");
            try {
                stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
    }
}
