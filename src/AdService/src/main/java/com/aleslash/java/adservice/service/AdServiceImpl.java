package com.aleslash.java.adservice.service;

import com.aleslash.java.ad.Ad;
import com.aleslash.java.ad.AdRequest;
import com.aleslash.java.ad.AdResponse;
import com.aleslash.java.ad.AdServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AdServiceImpl extends AdServiceGrpc.AdServiceImplBase {
    @Override
    public void getAds(AdRequest request, StreamObserver<AdResponse> responseObserver) {
//        super.getAds(request, responseObserver);
        AdResponse response = AdResponse.newBuilder()
                .addAds(Ad.newBuilder()
                        .setText("Google website")
                        .setRedirectUrl("https://www.google.com")
                )
                .addAds(Ad.newBuilder()
                        .setText("Microsoft website")
                        .setRedirectUrl("https://www.microsoft.com")
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
