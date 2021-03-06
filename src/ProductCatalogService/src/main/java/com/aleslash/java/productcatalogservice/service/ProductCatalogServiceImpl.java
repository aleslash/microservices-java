package com.aleslash.java.productcatalogservice.service;

import com.aleslash.java.productcatalog.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class ProductCatalogServiceImpl extends ProductCatalogServiceGrpc.ProductCatalogServiceImplBase {
    @Override
    public void listProducts(Empty request, StreamObserver<ListProductsResponse> responseObserver) {
//        super.listProducts(request, responseObserver);
        ListProductsResponse response = ListProductsResponse.newBuilder()
                .addProducts(Product.newBuilder()
                        .addCategories("CategoryA")
                        .setDescription("DescriptionA")
                        .setId(UUID.randomUUID().toString())
                        .setName("ProductA")
                        .setPicture("productA.jpg")
                        .setPriceUsd(Money.newBuilder()
                                .setCurrencyCode("USD")
                                .setUnits(2)
                                .setNanos(750000000)
                                .build())
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<Product> responseObserver) {
//        super.getProduct(request, responseObserver);
        Product response = Product.newBuilder()
                .addCategories("CategoryA")
                .setDescription("DescriptionA")
                .setId(request.getId())
                .setName("ProductA")
                .setPicture("productA.jpg")
                .setPriceUsd(Money.newBuilder()
                        .setCurrencyCode("USD")
                        .setUnits(2)
                        .setNanos(750000000)
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void searchProducts(SearchProductsRequest request, StreamObserver<SearchProductsResponse> responseObserver) {
//        super.searchProducts(request, responseObserver);
        SearchProductsResponse response = SearchProductsResponse.newBuilder()
                .addResults(Product.newBuilder()
                        .addCategories("CategoryA")
                        .setDescription("DescriptionA")
                        .setId(UUID.randomUUID().toString())
                        .setName("ProductA")
                        .setPicture("productA.jpg")
                        .setPriceUsd(Money.newBuilder()
                                .setCurrencyCode("USD")
                                .setUnits(2)
                                .setNanos(750000000)
                                .build())
                        .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
