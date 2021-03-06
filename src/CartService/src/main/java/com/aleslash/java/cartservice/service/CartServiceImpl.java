package com.aleslash.java.cartservice.service;

import com.aleslash.java.cart.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Random;
import java.util.UUID;

@GrpcService
public class CartServiceImpl extends CartServiceGrpc.CartServiceImplBase {
    @Override
    public void addItem(AddItemRequest request, StreamObserver<Empty> responseObserver) {
//        super.addItem(request, responseObserver);
        responseObserver.onCompleted();
    }

    @Override
    public void getCart(GetCartRequest request, StreamObserver<Cart> responseObserver) {
//        super.getCart(request, responseObserver);
        Cart response = Cart.newBuilder()
                .addItems(CartItem.newBuilder()
                        .setProductId(UUID.randomUUID().toString())
                        .setQuantity(3)
                        .build()
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void emptyCart(EmptyCartRequest request, StreamObserver<Empty> responseObserver) {
//        super.emptyCart(request, responseObserver);
        responseObserver.onCompleted();
    }
}
