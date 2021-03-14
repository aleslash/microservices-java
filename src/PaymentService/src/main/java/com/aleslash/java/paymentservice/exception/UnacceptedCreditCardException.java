package com.aleslash.java.paymentservice.exception;

public class UnacceptedCreditCardException extends CreditCardException{
    public UnacceptedCreditCardException(String cardType) {
        super("Sorry, we cannot process " + cardType + " credit cards. Only VISA or MasterCard is accepted.");
    }
}
