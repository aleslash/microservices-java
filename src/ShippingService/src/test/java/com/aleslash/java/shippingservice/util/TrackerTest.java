package com.aleslash.java.shippingservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackerTest {

    @Test
    @DisplayName("Deve retornar TrackingId no formato correto")
    void deveRetornarTrackingIdNoFormatoCorreto() {
        Tracker tracker = new Tracker();
        String actual = tracker.createTrackingId("base address");
        Assertions.assertTrue(actual.matches("[A-Z]+-[0-9]+-[0-9]+"));
    }

    @Test
    @DisplayName("Não pode devolver TrackingId iguais")
    void naoPodeDevolverTrackingIdIguais() {
        Tracker tracker = new Tracker();
        String actual = tracker.createTrackingId("base address");
        String expected = tracker.createTrackingId("base address");
        Assertions.assertNotEquals(actual, expected);
    }

}