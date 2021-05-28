package com.aleslash.java.shippingservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CotacaoTest {

    @Test
    @DisplayName("deve retornar String no formato correto com Cotacao de inteiros")
    void deveRetornarStringNoFormatoCorretoCotacaoInteiros() {
        Cotacao cotacao = new Cotacao(2,30);
        String expected = "$2.30";
        Assertions.assertEquals(expected, cotacao.toString());
    }

    @Test
    @DisplayName("deve retornar String no formato correto com Cotacao de BigDecimal")
    void deveRetornarStringNoFormatoCorretoCotacaoBigDecimal() {
        Cotacao cotacao = new Cotacao(new BigDecimal("2.30"));
        String expected = "$2.30";
        Assertions.assertEquals(expected, cotacao.toString());
    }
}