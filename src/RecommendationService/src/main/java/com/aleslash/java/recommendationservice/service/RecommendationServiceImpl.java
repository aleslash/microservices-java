package com.aleslash.java.recommendationservice.service;

import com.aleslash.java.productcatalog.Empty;
import com.aleslash.java.productcatalog.ListProductsResponse;
import com.aleslash.java.productcatalog.Product;
import com.aleslash.java.productcatalog.ProductCatalogServiceGrpc;
import com.aleslash.java.recommendation.ListRecommendationsRequest;
import com.aleslash.java.recommendation.ListRecommendationsResponse;
import com.aleslash.java.recommendation.RecommendationServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
public class RecommendationServiceImpl extends RecommendationServiceGrpc.RecommendationServiceImplBase {

    @GrpcClient("product-catalog")
    private ProductCatalogServiceGrpc.ProductCatalogServiceBlockingStub productCatalogServiceStub;

    @Override
    public void listRecommendations(ListRecommendationsRequest request, StreamObserver<ListRecommendationsResponse> responseObserver) {
        ListProductsResponse listProductsResponse = productCatalogServiceStub.listProducts(Empty.getDefaultInstance());
        List<Product> filteredProducts = listProductsResponse.getProductsList().stream()
                .filter(product -> !request.getProductIdsList().contains(product.getId()))
                .collect(Collectors.toList());
        ListRecommendationsResponse response = ListRecommendationsResponse.newBuilder()
                .addAllProductIds(filteredProducts.stream()
                        .map(product -> product.getId())
                        .limit(5)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
