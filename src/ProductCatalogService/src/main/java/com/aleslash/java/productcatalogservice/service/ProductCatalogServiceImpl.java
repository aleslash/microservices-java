package com.aleslash.java.productcatalogservice.service;

import com.aleslash.java.productcatalog.*;
import com.aleslash.java.productcatalogservice.dto.ProductInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ProductCatalogServiceImpl extends ProductCatalogServiceGrpc.ProductCatalogServiceImplBase {

    @Value("classpath:static/products.json")
    Resource productDataResource;

    @Override
    public void listProducts(Empty request, StreamObserver<ListProductsResponse> responseObserver) {

        List<ProductInfo> productInfos = getProductsCatalog();
        ListProductsResponse response = ListProductsResponse.newBuilder()
                .addAllProducts(productInfos.stream().map(
                        productInfo -> productInfo.toProduct()).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<Product> responseObserver) {

        List<ProductInfo> productInfos = getProductsCatalog();
        List<ProductInfo> productInfosFiltered = productInfos.stream()
                .filter(
                        productInfo -> productInfo.getId().equalsIgnoreCase(request.getId()))
                .collect(Collectors.toList());

        if(productInfosFiltered.size() < 1) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("no product with ID " + request.getId()).asException());
        }

        Product response = productInfosFiltered.get(0).toProduct();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void searchProducts(SearchProductsRequest request, StreamObserver<SearchProductsResponse> responseObserver) {

        List<ProductInfo> productInfos = getProductsCatalog();
        List<ProductInfo> productInfosFiltered = productInfos.stream()
                .filter(
                        productInfo -> productInfo.getName().toLowerCase().contains(request.getQuery().toLowerCase()) ||
                                productInfo.getDescription().toLowerCase().contains(request.getQuery().toLowerCase()))
                .collect(Collectors.toList());

        SearchProductsResponse response = SearchProductsResponse.newBuilder()
                .addAllResults(productInfosFiltered.stream().map(
                        productInfo -> productInfo.toProduct()).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<ProductInfo> getProductsCatalog(){
        List<ProductInfo> productInfos = Collections.emptyList();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            productInfos = objectMapper.readValue(
                    productDataResource.getInputStream(),
                    new TypeReference<List<ProductInfo>>(){});

        } catch (Exception ex) {
            System.out.println("Erro ao ler arquivo: " + ex.toString());
        }

        return productInfos;
    }
}
