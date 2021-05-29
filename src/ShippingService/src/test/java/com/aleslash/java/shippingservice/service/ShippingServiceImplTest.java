package com.aleslash.java.shippingservice.service;

import com.aleslash.java.shipping.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShippingServiceImplTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();


    @Test
    @DisplayName("Deve obter cotacao com sucesso de um item")
    void deveObterCotacaoComSucessoDeUmItem() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new ShippingServiceImpl()).build().start());

        ShippingServiceGrpc.ShippingServiceBlockingStub blockingStub = ShippingServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        GetQuoteResponse getQuoteResponse =
                blockingStub.getQuote(GetQuoteRequest
                        .newBuilder()
                        .addItems(CartItem.newBuilder()
                                .setQuantity(1)
                        .build()).build());

        Assertions.assertNotNull(getQuoteResponse);

    }

    @Test
    @DisplayName("Deve obter TrackingID com sucesso")
    void deveObterTrackingIdComSucesso() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new ShippingServiceImpl()).build().start());

        ShippingServiceGrpc.ShippingServiceBlockingStub blockingStub = ShippingServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );
        ShipOrderResponse shipOrderResponse =
                blockingStub.shipOrder(ShipOrderRequest
                        .newBuilder().setAddress(Address.newBuilder()
                                .setCity("City")
                                .setCountry("Country")
                                .setState("State")
                                .setStreetAddress("Street Address")
                                .build())
                        .build());

        Assertions.assertNotNull(shipOrderResponse);
    }
}