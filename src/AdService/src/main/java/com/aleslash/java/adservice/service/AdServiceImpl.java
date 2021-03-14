package com.aleslash.java.adservice.service;

import com.aleslash.java.ad.Ad;
import com.aleslash.java.ad.AdRequest;
import com.aleslash.java.ad.AdResponse;
import com.aleslash.java.ad.AdServiceGrpc;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@GrpcService
public class AdServiceImpl extends AdServiceGrpc.AdServiceImplBase {

    private static final ImmutableListMultimap<String, Ad> adsMap = createAdsMap();
    private static int MAX_ADS_TO_SERVE = 2;
    private static final Random random = new Random();

    @Override
    public void getAds(AdRequest request, StreamObserver<AdResponse> responseObserver) {
        List<Ad> allAds = new ArrayList<>();
        if(request.getContextKeysCount() > 0) {
            for (int i = 0; i < request.getContextKeysCount(); i++) {
                Collection<Ad> ads = this.getAdsByCategory(request.getContextKeys(i));
                allAds.addAll(ads);
            }
        } else {
            allAds = this.getRandomAds();
        }

        AdResponse response = AdResponse.newBuilder()
                .addAllAds(allAds)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Collection<Ad> getAdsByCategory(String category) {
        return adsMap.get(category);
    }

    private static ImmutableListMultimap<String, Ad> createAdsMap() {
        Ad camera =
                Ad.newBuilder()
                        .setRedirectUrl("/product/2ZYFJ3GM2N")
                        .setText("Film camera for sale. 50% off.")
                        .build();
        Ad lens =
                Ad.newBuilder()
                        .setRedirectUrl("/product/66VCHSJNUP")
                        .setText("Vintage camera lens for sale. 20% off.")
                        .build();
        Ad recordPlayer =
                Ad.newBuilder()
                        .setRedirectUrl("/product/0PUK6V6EV0")
                        .setText("Vintage record player for sale. 30% off.")
                        .build();
        Ad bike =
                Ad.newBuilder()
                        .setRedirectUrl("/product/9SIQT8TOJO")
                        .setText("City Bike for sale. 10% off.")
                        .build();
        Ad baristaKit =
                Ad.newBuilder()
                        .setRedirectUrl("/product/1YMWWN1N4O")
                        .setText("Home Barista kitchen kit for sale. Buy one, get second kit for free")
                        .build();
        Ad airPlant =
                Ad.newBuilder()
                        .setRedirectUrl("/product/6E92ZMYYFZ")
                        .setText("Air plants for sale. Buy two, get third one for free")
                        .build();
        Ad terrarium =
                Ad.newBuilder()
                        .setRedirectUrl("/product/L9ECAV7KIM")
                        .setText("Terrarium for sale. Buy one, get second one for free")
                        .build();
        return ImmutableListMultimap.<String, Ad>builder()
                .putAll("photography", camera, lens)
                .putAll("vintage", camera, lens, recordPlayer)
                .put("cycling", bike)
                .put("cookware", baristaKit)
                .putAll("gardening", airPlant, terrarium)
                .build();
    }

    private List<Ad> getRandomAds() {
        List<Ad> ads = new ArrayList<>(MAX_ADS_TO_SERVE);
        Collection<Ad> allAds = adsMap.values();
        for (int i = 0; i < MAX_ADS_TO_SERVE; i++) {
            ads.add(Iterables.get(allAds, random.nextInt(allAds.size())));
        }
        return ads;
    }
}
