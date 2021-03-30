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

import java.util.UUID;

@GrpcService
public class RecommendationServiceImpl extends RecommendationServiceGrpc.RecommendationServiceImplBase {

    @GrpcClient("product-catalog")
    private ProductCatalogServiceGrpc.ProductCatalogServiceBlockingStub productCatalogServiceStub;

    @Override
    public void listRecommendations(ListRecommendationsRequest request, StreamObserver<ListRecommendationsResponse> responseObserver) {
        //fetch list of products from product catalog stub
        ListProductsResponse listProductsResponse = productCatalogServiceStub.listProducts(Empty.getDefaultInstance());
        for (Product product: listProductsResponse.getProductsList()) {
            System.out.println("Product: " + product.getName());
        }

        //sample list of indices to return

        //fetch product ids from indices

        //build and return response

        ListRecommendationsResponse response = ListRecommendationsResponse.newBuilder()
                .addProductIds(UUID.randomUUID().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
