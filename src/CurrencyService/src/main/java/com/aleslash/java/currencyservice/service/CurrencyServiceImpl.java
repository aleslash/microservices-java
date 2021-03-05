package com.aleslash.java.currencyservice.service;

import com.aleslash.java.currency.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CurrencyServiceImpl extends CurrencyServiceGrpc.CurrencyServiceImplBase {
    @Override
    public void getSupportedCurrencies(Empty request, StreamObserver<GetSupportedCurrenciesResponse> responseObserver) {
//        super.getSupportedCurrencies(request, responseObserver);
        GetSupportedCurrenciesResponse response = GetSupportedCurrenciesResponse.newBuilder()
                .addCurrencyCodes("BRL")
                .addCurrencyCodes("USD")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void convert(CurrencyConversionRequest request, StreamObserver<Money> responseObserver) {
//        super.convert(request, responseObserver);
        Money response = Money.newBuilder()
                .setCurrencyCode("BRL")
                .setUnits(4)
                .setNanos(750000000)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
