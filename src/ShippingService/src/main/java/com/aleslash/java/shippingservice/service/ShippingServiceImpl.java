package com.aleslash.java.shippingservice.service;

import com.aleslash.java.shipping.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class ShippingServiceImpl extends ShippingServiceGrpc.ShippingServiceImplBase {
    @Override
    public void getQuote(GetQuoteRequest request, StreamObserver<GetQuoteResponse> responseObserver) {
//        super.getQuote(request, responseObserver);
        GetQuoteResponse response = GetQuoteResponse.newBuilder()
                .setCostUsd( Money.newBuilder()
                        .setCurrencyCode("USD")
                        .setUnits(5)
                        .setNanos(250000000)
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void shipOrder(ShipOrderRequest request, StreamObserver<ShipOrderResponse> responseObserver) {
//        super.shipOrder(request, responseObserver);
        ShipOrderResponse response = ShipOrderResponse.newBuilder()
                .setTrackingId(UUID.randomUUID().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
