package com.aleslash.java.cartservice.service;

import com.aleslash.java.cart.*;
import com.aleslash.java.cartservice.repository.LocalCartStore;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;

@GrpcService
public class CartServiceImpl extends CartServiceGrpc.CartServiceImplBase {

    @Autowired
    private LocalCartStore localCartStore;

    @Override
    public void addItem(AddItemRequest request, StreamObserver<Empty> responseObserver) {
        localCartStore.addItem(request.getUserId(), request.getItem().getProductId(), request.getItem().getQuantity());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getCart(GetCartRequest request, StreamObserver<Cart> responseObserver) {
        Cart response = localCartStore.getCart(request.getUserId());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void emptyCart(EmptyCartRequest request, StreamObserver<Empty> responseObserver) {
        localCartStore.emptyCart(request.getUserId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
