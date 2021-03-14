package com.aleslash.java.shippingservice.util;

import com.aleslash.java.shippingservice.dto.Cotacao;

import java.math.BigDecimal;

public class Cotador {
    public Cotacao getCotacaoFromCount(int count) {
        return getCotacaoFromBigDecimal(getBigDecimalFromCount(count));
    }

    private Cotacao getCotacaoFromBigDecimal(BigDecimal valor) {
        return new Cotacao(valor);
    }

    private BigDecimal getBigDecimalFromCount(int count) {
        if(count == 0) return BigDecimal.ZERO;
        BigDecimal p = BigDecimal.valueOf(1 + (count * 0.2));
        return BigDecimal.valueOf(count + Math.floor(Math.pow(3,p.doubleValue()))/100);
    }


}
