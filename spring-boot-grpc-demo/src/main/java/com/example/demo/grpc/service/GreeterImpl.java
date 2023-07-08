package com.example.demo.grpc.service;

import com.example.demo.grpc.annotation.GrpcService;
import com.example.grpc.generated.GreeterGrpc;
import com.example.grpc.generated.SayHelloReq;
import com.example.grpc.generated.SayHelloResp;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Autowired
    private GreeterConfig config;

    @Override
    public void sayHello(SayHelloReq request, StreamObserver<SayHelloResp> responseObserver) {
        if (log.isDebugEnabled()) {
            log.info("sayHello: {}", request);
        }

        var reply = SayHelloResp.newBuilder()
                .setMessage(config.getPrefix() + " " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
