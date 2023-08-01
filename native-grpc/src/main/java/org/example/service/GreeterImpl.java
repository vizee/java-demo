package org.example.service;

import org.example.grpc.generated.GreeterGrpc;
import org.example.grpc.generated.SayHelloReq;
import org.example.grpc.generated.SayHelloResp;
import io.grpc.stub.StreamObserver;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(SayHelloReq request, StreamObserver<SayHelloResp> responseObserver) {
        var reply = SayHelloResp.newBuilder()
                .setMessage("Hello " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
