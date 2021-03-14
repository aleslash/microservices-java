package com.aleslash.java.shippingservice.dto;

import java.math.BigDecimal;

public class Cotacao {
    private int dollars;
    private int cents;

    public Cotacao(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
    }

    public Cotacao(BigDecimal valor) {
        this.dollars = valor.intValue();
        this.cents = Integer.valueOf(valor.subtract(new BigDecimal(valor.intValue())).toPlainString().replace('.','0'));
    }

    @Override
    public String toString() {
        return "$" + dollars +
                "." + cents;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }
}
