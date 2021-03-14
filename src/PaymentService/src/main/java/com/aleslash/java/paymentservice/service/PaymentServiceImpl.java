package com.aleslash.java.paymentservice.service;

import com.aleslash.java.payment.ChargeRequest;
import com.aleslash.java.payment.ChargeResponse;
import com.aleslash.java.payment.PaymentServiceGrpc;
import com.aleslash.java.paymentservice.exception.ExpiredCreditCardException;
import com.aleslash.java.paymentservice.exception.InvalidCreditCardException;
import com.aleslash.java.paymentservice.exception.UnacceptedCreditCardException;
import com.aleslash.java.paymentservice.util.CardValidator;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.UUID;

@GrpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    @Override
    public void charge(ChargeRequest request, StreamObserver<ChargeResponse> responseObserver) {
//        super.charge(request, responseObserver);
        //Incluir validacoes
        CardValidator cardValidator = new CardValidator(request.getCreditCard().getCreditCardNumber());
        if (!cardValidator.isValid()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withCause(new InvalidCreditCardException()).asException());
        }

        // Only VISA and mastercard is accepted, other card types (AMEX, dinersclub) will
        // throw UnacceptedCreditCard error.
        String cardType = cardValidator.getCartType();
        if (!(cardType.equalsIgnoreCase("VISA") ||
                cardType.equalsIgnoreCase("MASTERCARD"))){
            responseObserver.onError(Status.INVALID_ARGUMENT.withCause( new UnacceptedCreditCardException(cardType)).asException());
        }

        // Also validate expiration is > today.
        int mesAtual = LocalDate.now().getMonth().ordinal();
        int anoAtual = LocalDate.now().getYear();

        int mesExpiracaoCartao = request.getCreditCard().getCreditCardExpirationMonth();
        int anoExpiracaoCartao = request.getCreditCard().getCreditCardExpirationYear();

        if((anoAtual*12 + mesAtual) > (anoExpiracaoCartao*12 + mesExpiracaoCartao)) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withCause( new ExpiredCreditCardException(
                    request.getCreditCard().getCreditCardNumber().replaceAll("-",""),
                    mesExpiracaoCartao, anoExpiracaoCartao
            )).asException());
        }

        // registrar evento de log

        ChargeResponse response = ChargeResponse.newBuilder()
                .setTransactionId(UUID.randomUUID().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
