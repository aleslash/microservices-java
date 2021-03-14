package com.aleslash.java.shippingservice.service;

import com.aleslash.java.shipping.*;
import com.aleslash.java.shippingservice.dto.Cotacao;
import com.aleslash.java.shippingservice.util.Cotador;
import com.aleslash.java.shippingservice.util.Tracker;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class ShippingServiceImpl extends ShippingServiceGrpc.ShippingServiceImplBase {
    @Override
    public void getQuote(GetQuoteRequest request, StreamObserver<GetQuoteResponse> responseObserver) {
        // 1. Our quote system requires the total number of items to be shipped.
        int count = 0;
        for (CartItem cartItem:request.getItemsList()) {
            count += cartItem.getQuantity();
        }

        // 2. Generate a quote based on the total number of items to be shipped.
        Cotacao cotacao = (new Cotador()).getCotacaoFromCount(count);

        // 3. Generate a response.
        GetQuoteResponse response = GetQuoteResponse.newBuilder()
                .setCostUsd( Money.newBuilder()
                        .setCurrencyCode("USD")
                        .setUnits(cotacao.getDollars())
                        .setNanos(cotacao.getCents() * 10000000)
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void shipOrder(ShipOrderRequest request, StreamObserver<ShipOrderResponse> responseObserver) {
        // 1. Create a Tracking ID
        String baseAddress = request.getAddress().getStreetAddress() + ", "
                + request.getAddress().getCity() + ", "
                + request.getAddress().getState();

        Tracker tracker = new Tracker();

        // 2. Generate a response.
        ShipOrderResponse response = ShipOrderResponse.newBuilder()
                .setTrackingId(tracker.createTrackingId(baseAddress))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
