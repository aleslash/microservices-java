package com.aleslash.java.paymentservice.exception;

public class CreditCardException extends Exception{

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code = 400;

    public CreditCardException(String message) {
        super(message);
    }
}
