package com.aleslash.java.shippingservice.util;

import com.aleslash.java.shippingservice.dto.Cotacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CotadorTest {

    @Test
    @DisplayName("Teste deve responder cotacao Zero")
    void deveResponderCotacaoZero() {
        Cotador cotador = new Cotador();
        Cotacao actual = cotador.getCotacaoFromCount(0);
        Cotacao expected = new Cotacao(BigDecimal.ZERO);
        Assertions.assertEquals(actual.getDollars(), expected.getDollars());
        Assertions.assertEquals(actual.getCents(), expected.getCents());
    }

    @Test
    @DisplayName("Teste deve responder cotacao para um")
    void deveResponderCotacaoParaUm() {
        Cotador cotador = new Cotador();
        Cotacao actual = cotador.getCotacaoFromCount(1);
        Cotacao expected = new Cotacao(BigDecimal.ZERO);
        expected.setDollars(1);
        expected.setCents(3);
        Assertions.assertEquals(actual.getDollars(), expected.getDollars());
        Assertions.assertEquals(actual.getCents(), expected.getCents());
    }
}