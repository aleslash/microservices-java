package com.aleslash.java.paymentservice.exception;

public class ExpiredCreditCardException extends CreditCardException{
    public ExpiredCreditCardException(String number, int month, int year) {
        super("Your credit card (ending "+ number.substring(number.length()-4)+") expired on " + month + "/" + year);
    }
}
