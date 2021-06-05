package com.aleslash.java.adservice.service;

import com.aleslash.java.ad.AdRequest;
import com.aleslash.java.ad.AdResponse;
import com.aleslash.java.ad.AdServiceGrpc;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdServiceImplTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    @DisplayName("Deve devolver Ads randomicos diferentes com sucesso")
    void deveDevolverAdsRandomicosDiferentes() throws Exception {

        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new AdServiceImpl()).build().start());

        AdServiceGrpc.AdServiceBlockingStub blockingStub = AdServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        AdResponse adResponse1 = blockingStub.getAds(
                AdRequest.newBuilder()
                        .build());
        AdResponse adResponse2 = blockingStub.getAds(
                AdRequest.newBuilder()
                        .build());

        String resposta1 = adResponse1.getAds(0).getRedirectUrl() + adResponse1.getAds(1).getRedirectUrl();
        Assertions.assertFalse(
                resposta1.contains(adResponse2.getAds(0).getRedirectUrl()) &&
                        resposta1.contains(adResponse2.getAds(1).getRedirectUrl()));

    }

    @Test
    @DisplayName("Deve devolver Ads por categoria com sucesso")
    void deveDevolverAdsPorCategoria() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new AdServiceImpl()).build().start());

        AdServiceGrpc.AdServiceBlockingStub blockingStub = AdServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        AdResponse adResponse = blockingStub.getAds(
                AdRequest.newBuilder()
                        .addContextKeys("photography")
                        .build());
        int expected = 2;
        Assertions.assertEquals(expected, adResponse.getAdsCount());
    }
}