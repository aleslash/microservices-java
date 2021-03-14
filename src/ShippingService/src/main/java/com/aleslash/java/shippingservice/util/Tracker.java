package com.aleslash.java.shippingservice.util;

import java.time.LocalDateTime;
import java.util.Random;

public class Tracker {

    private boolean seeded = false;

    private Random random = new Random();

    public String createTrackingId(String salt) {
        if(!seeded) {
            random.setSeed(LocalDateTime.now().getNano());
            seeded = true;
        }
        return String.format("%c%c-%d%s-%d%s",
                getRandomLetterCode(),
                getRandomLetterCode(),
                salt.length(),
                getRandomNumber(3),
                salt.length()/2,
                getRandomNumber(7)
                );
    }

    private int getRandomLetterCode() {
        return 65 + Integer.valueOf(random.nextInt(25));
    }

    private String getRandomNumber(int digits) {
        String retorno = "";
        for (int i = 0; i < digits; i++) {
            retorno = String.format("%s%d", retorno, random.nextInt(10));
        }
        return retorno;
    }
}
