package com.aleslash.java.paymentservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardValidatorTest {

    @Test
    @DisplayName("Deve validar cartao com sucesso")
    void deveValidarCartao() {
        CardValidator cardValidator = new CardValidator("6771-7980-2500-0004");
        Assertions.assertTrue(cardValidator.isValid());
    }

    @Test
    @DisplayName("Deve falhar para reconhecer cartao")
    void deveFalharParaReconhecerCartao() {
        CardValidator cardValidator = new CardValidator("6771-7980-2500-0004");
        String expected = "UNKNOWN";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao visa com sucesso")
    void deveReconhecerCartaoVisa() {
        CardValidator cardValidator = new CardValidator("4000-0600-0000-0006");
        String expected = "VISA";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao mastercard com sucesso")
    void deveReconhecerCartaoMastercard() {
        CardValidator cardValidator = new CardValidator("5204-2477-5000-1471");
        String expected = "MASTERCARD";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao JCB com sucesso")
    void deveReconhecerCartaoJCB() {
        CardValidator cardValidator = new CardValidator("3569-9900-1009-5841");
        String expected = "JCB";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao Discover com sucesso")
    void deveReconhecerCartaoDiscover() {
        CardValidator cardValidator = new CardValidator("6011-1111-1111-1117");
        String expected = "DISCOVER";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao Diners com sucesso")
    void deveReconhecerCartaoDiners() {
        CardValidator cardValidator = new CardValidator("3600-6666-3333-44");
        String expected = "DINERS_CLUB";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao American Express com sucesso")
    void deveReconhecerCartaoAmericanExpress() {
        CardValidator cardValidator = new CardValidator("3714-4963-5398-431");
        String expected = "AMERICAN_EXPRESS";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }

    @Test
    @DisplayName("Deve reconhecer cartao China UnionPay com sucesso")
    void deveReconhecerCartaoChinaUnionPay() {
        CardValidator cardValidator = new CardValidator("6243-0300-0000-0001");
        String expected = "CHINA_UNION_PAY";
        Assertions.assertEquals(expected, cardValidator.getCartType());
    }
}