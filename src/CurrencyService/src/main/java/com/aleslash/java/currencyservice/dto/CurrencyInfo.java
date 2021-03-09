package com.aleslash.java.currencyservice.dto;

public class CurrencyInfo {
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getConversionFromEUR() {
        return conversionFromEUR;
    }

    public void setConversionFromEUR(Long conversionFromEUR) {
        this.conversionFromEUR = conversionFromEUR;
    }

    private String currency;
    private Long conversionFromEUR;


}
