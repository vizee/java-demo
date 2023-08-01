package org.example;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import org.example.service.GreeterImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var server = Grpc.newServerBuilderForPort(50051, InsecureServerCredentials.create())
                .addService(new GreeterImpl())
                .build();
        server.start();
        System.out.println("Server started on " + server.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server");
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
        server.awaitTermination();
    }
}
