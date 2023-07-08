package org.example;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import lombok.extern.slf4j.Slf4j;
import org.example.service.GreeterImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var server = Grpc.newServerBuilderForPort(50051, InsecureServerCredentials.create())
                .addService(new GreeterImpl())
                .build();
        server.start();
        log.info("Server started on {}", server.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down server");
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
        server.awaitTermination();
    }
}
