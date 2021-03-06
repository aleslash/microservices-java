package com.aleslash.java.paymentservice.service;

import com.aleslash.java.payment.ChargeRequest;
import com.aleslash.java.payment.ChargeResponse;
import com.aleslash.java.payment.PaymentServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    @Override
    public void charge(ChargeRequest request, StreamObserver<ChargeResponse> responseObserver) {
//        super.charge(request, responseObserver);
        ChargeResponse response = ChargeResponse.newBuilder()
                .setTransactionId(UUID.randomUUID().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
