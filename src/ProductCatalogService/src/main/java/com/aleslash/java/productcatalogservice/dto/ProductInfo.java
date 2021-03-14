package com.aleslash.java.productcatalogservice.dto;

import com.aleslash.java.productcatalog.Money;
import com.aleslash.java.productcatalog.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInfo {
    private String id;
    private String name;
    private String description;
    private String picture;
    private List<String> categories;
    private PriceUsd priceUsd;

    public String getId() {
        return id;
    }

    public Product toProduct() {
        return Product.newBuilder()
                .setId(this.getId())
                .setName(this.getName())
                .setDescription(this.getDescription())
                .setPicture(this.getPicture())
                .setPriceUsd(Money.newBuilder()
                        .setCurrencyCode(this.getPriceUsd().getCurrencyCode())
                        .setNanos(this.getPriceUsd().getNanos())
                        .setUnits(this.getPriceUsd().getUnits())
                        .build())
                .addAllCategories(this.getCategories().stream().collect(Collectors.toList()))
                .build();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public PriceUsd getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(PriceUsd priceUsd) {
        this.priceUsd = priceUsd;
    }
}
