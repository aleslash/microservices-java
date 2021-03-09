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
import java.util.stream.Collectors;


@GrpcService
public class CurrencyServiceImpl extends CurrencyServiceGrpc.CurrencyServiceImplBase {

    @Value("classpath:static/currency_conversion.json")
    Resource currencyDataResource;

    @Override
    public void getSupportedCurrencies(Empty request, StreamObserver<GetSupportedCurrenciesResponse> responseObserver) {
//        super.getSupportedCurrencies(request, responseObserver);
        List<CurrencyInfo> currencyInfos = getCurrencyInfos();

        GetSupportedCurrenciesResponse response = GetSupportedCurrenciesResponse.newBuilder()
                .addAllCurrencyCodes(currencyInfos.stream().map(currencyInfo -> currencyInfo.getCurrency()).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<CurrencyInfo> getCurrencyInfos() {
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
        return currencyInfos;
    }

    @Override
    public void convert(CurrencyConversionRequest request, StreamObserver<Money> responseObserver) {
//        super.convert(request, responseObserver);
        List<CurrencyInfo> currencyInfos = getCurrencyInfos();
        CurrencyInfo currrencyFrom = currencyInfos.stream().filter(
                        currencyInfo ->
                                currencyInfo.getCurrency().equalsIgnoreCase(request.getFrom().getCurrencyCode())
                ).collect(Collectors.toList()).get(0);

        CurrencyInfo currencyTo = currencyInfos.stream().filter(
                currencyInfo ->
                        currencyInfo.getCurrency().equalsIgnoreCase(request.getToCode())
        ).collect(Collectors.toList()).get(0);

        //Converter primeiro para euros
        Money euros = Money.newBuilder()
                .setCurrencyCode("EUR")
                .setUnits(request.getFrom().getUnits() /currrencyFrom.getConversionFromEUR())
                .setNanos((int) (request.getFrom().getNanos() /currrencyFrom.getConversionFromEUR()))
                .build();

        //Converter de euros para a moeda solicitada

        Money moneyTo = Money.newBuilder()
                .setCurrencyCode(currencyTo.getCurrency())
                .setUnits(euros.getUnits() * currencyTo.getConversionFromEUR())
                .setNanos((int) (euros.getNanos() * currencyTo.getConversionFromEUR()))
                .build();

        responseObserver.onNext(moneyTo);
        responseObserver.onCompleted();
    }
}
