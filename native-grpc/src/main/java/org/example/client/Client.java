package org.example.client;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import org.example.grpc.generated.GreeterGrpc;
import org.example.grpc.generated.SayHelloReq;

import java.util.concurrent.ExecutionException;

public class Client {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var channel = Grpc.newChannelBuilder("localhost:50051", InsecureChannelCredentials.create())
                .build();
        var greeter = GreeterGrpc.newFutureStub(channel);
        var resp = greeter.sayHello(SayHelloReq.newBuilder().setName("Alice").build()).get();
        System.out.println("response: " + resp.getMessage());
    }
}
