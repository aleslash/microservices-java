package com.aleslash.java.emailservice.service;

import com.aleslash.java.email.EmailServiceGrpc;
import com.aleslash.java.email.Empty;
import com.aleslash.java.email.SendOrderConfirmationRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class EmailServiceImpl extends EmailServiceGrpc.EmailServiceImplBase {
    @Override
    public void sendOrderConfirmation(SendOrderConfirmationRequest request, StreamObserver<Empty> responseObserver) {
//        super.sendOrderConfirmation(request, responseObserver);
        responseObserver.onCompleted();
    }
}
