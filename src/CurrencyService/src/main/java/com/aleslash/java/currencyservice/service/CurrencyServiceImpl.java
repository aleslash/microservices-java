package com.aleslash.java.currencyservice.service;

import com.aleslash.java.currency.*;
import io.grpc.stub.StreamObserver;

public class CurrencyServiceImpl extends CurrencyServiceGrpc.CurrencyServiceImplBase {
    @Override
    public void getSupportedCurrencies(Empty request, StreamObserver<GetSupportedCurrenciesResponse> responseObserver) {
        super.getSupportedCurrencies(request, responseObserver);
    }

    @Override
    public void convert(CurrencyConversionRequest request, StreamObserver<Money> responseObserver) {
        super.convert(request, responseObserver);
    }
}
