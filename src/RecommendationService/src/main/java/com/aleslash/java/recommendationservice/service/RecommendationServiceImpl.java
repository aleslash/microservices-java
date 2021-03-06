package com.aleslash.java.recommendationservice.service;

import com.aleslash.java.recommendation.ListRecommendationsRequest;
import com.aleslash.java.recommendation.ListRecommendationsResponse;
import com.aleslash.java.recommendation.RecommendationServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class RecommendationServiceImpl extends RecommendationServiceGrpc.RecommendationServiceImplBase {
    @Override
    public void listRecommendations(ListRecommendationsRequest request, StreamObserver<ListRecommendationsResponse> responseObserver) {
//        super.listRecommendations(request, responseObserver);
        ListRecommendationsResponse response = ListRecommendationsResponse.newBuilder()
                .addProductIds(UUID.randomUUID().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
