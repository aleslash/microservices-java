package com.aleslash.java.checkoutservice.service;

import com.aleslash.java.checkout.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class CheckoutServiceImpl extends CheckoutServiceGrpc.CheckoutServiceImplBase {
    @Override
    public void placeOrder(PlaceOrderRequest request, StreamObserver<PlaceOrderResponse> responseObserver) {
//        super.placeOrder(request, responseObserver);
        PlaceOrderResponse response = PlaceOrderResponse.newBuilder()
                .setOrder(OrderResult.newBuilder()
                        .addItems(OrderItem.newBuilder()
                                .setCost(Money.newBuilder()
                                        .setCurrencyCode("USD")
                                        .setUnits(7)
                                        .setNanos(0)
                                        .build())
                                .setItem(CartItem.newBuilder()
                                        .setProductId(UUID.randomUUID().toString())
                                        .setQuantity(3)
                                        .build())
                                .build())
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
