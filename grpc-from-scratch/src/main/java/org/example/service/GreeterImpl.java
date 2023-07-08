package org.example.service;

import com.example.grpc.generated.GreeterGrpc;
import com.example.grpc.generated.SayHelloReq;
import com.example.grpc.generated.SayHelloResp;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(SayHelloReq request, StreamObserver<SayHelloResp> responseObserver) {
        if (log.isDebugEnabled()) {
            log.info("sayHello: {}", request);
        }

        var reply = SayHelloResp.newBuilder()
                .setMessage("Hello " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
