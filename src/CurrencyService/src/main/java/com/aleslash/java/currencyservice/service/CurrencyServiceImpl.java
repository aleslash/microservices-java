package com.aleslash.java.currencyservice.service;

import com.aleslash.java.currency.*;
import com.aleslash.java.currencyservice.dto.CurrencyInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import java.nio.file.Files;
import java.util.*;


@GrpcService
public class CurrencyServiceImpl extends CurrencyServiceGrpc.CurrencyServiceImplBase {

    @Value("classpath:static/currency_conversion.json")
    Resource currencyDataResource;

    @Override
    public void getSupportedCurrencies(Empty request, StreamObserver<GetSupportedCurrenciesResponse> responseObserver) {
//        super.getSupportedCurrencies(request, responseObserver);
        List<CurrencyInfo> currencyInfos = Collections.emptyList();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            currencyInfos = objectMapper.readValue(
                    Files.readString(currencyDataResource.getFile().toPath()),
                            new TypeReference<List<CurrencyInfo>>(){});
        }
        catch (Exception ex) {
            System.out.println("Erro ao ler arquivo: " + ex.toString());
        }

        for (CurrencyInfo currencyInfo: currencyInfos) {
            System.out.println(currencyInfo);
        }

        GetSupportedCurrenciesResponse response = GetSupportedCurrenciesResponse.newBuilder()
//                .addAllCurrencyCodes(currencyInfos.iterator().)
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
