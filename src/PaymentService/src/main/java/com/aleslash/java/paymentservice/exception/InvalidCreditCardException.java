package com.aleslash.java.paymentservice.exception;

public class InvalidCreditCardException extends CreditCardException {
    public InvalidCreditCardException() {
        super("Credit card info is invalid");
    }
}
