package com.aleslash.java.paymentservice.service;

import com.aleslash.java.payment.ChargeRequest;
import com.aleslash.java.payment.ChargeResponse;
import com.aleslash.java.payment.CreditCardInfo;
import com.aleslash.java.payment.PaymentServiceGrpc;
import com.aleslash.java.paymentservice.exception.ExpiredCreditCardException;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    @DisplayName("Deve efetuar cobranca com sucesso")
    void deveEfetuarCobrancaComSucesso() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new PaymentServiceImpl()).build().start());

        PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub = PaymentServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        ChargeResponse chargeResponse = blockingStub.charge(
                ChargeRequest.newBuilder()
                        .setCreditCard(CreditCardInfo.newBuilder()
                                .setCreditCardNumber("4000-0600-0000-0006")
                                .setCreditCardCvv(123)
                                .setCreditCardExpirationMonth(10)
                                .setCreditCardExpirationYear(LocalDate.now().getYear()+1)
                                .build())
                        .build());
        Assertions.assertNotNull(chargeResponse.getTransactionId());
    }

    @Test
    @DisplayName("Deve gerar exception de cartao expirado")
    void deveGerarExceptionCartaoExpirado() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new PaymentServiceImpl()).build().start());

        PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub = PaymentServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        Assertions.assertThrows(StatusRuntimeException.class, () -> {
            ChargeResponse chargeResponse = blockingStub.charge(
                ChargeRequest.newBuilder()
                        .setCreditCard(CreditCardInfo.newBuilder()
                                .setCreditCardNumber("4000-0600-0000-0006")
                                .setCreditCardCvv(123)
                                .setCreditCardExpirationMonth(10)
                                .setCreditCardExpirationYear(LocalDate.now().getYear()-1)
                                .build())
                        .build());
        });
    }

    @Test
    @DisplayName("Deve gerar exception de cartao invalido")
    void deveGerarExceptionCartaoInvalido() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new PaymentServiceImpl()).build().start());

        PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub = PaymentServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        Assertions.assertThrows(StatusRuntimeException.class, () -> {
            ChargeResponse chargeResponse = blockingStub.charge(
                    ChargeRequest.newBuilder()
                            .setCreditCard(CreditCardInfo.newBuilder()
                                    .setCreditCardNumber("1234")
                                    .setCreditCardCvv(123)
                                    .setCreditCardExpirationMonth(10)
                                    .setCreditCardExpirationYear(LocalDate.now().getYear()-1)
                                    .build())
                            .build());
        });
    }

    @Test
    @DisplayName("Deve gerar exception de cartao AMEX nao autorizado")
    void deveGerarExceptionCartaoAmexNaoAutorizado() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(new PaymentServiceImpl()).build().start());

        PaymentServiceGrpc.PaymentServiceBlockingStub blockingStub = PaymentServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );

        Assertions.assertThrows(StatusRuntimeException.class, () -> {
            ChargeResponse chargeResponse = blockingStub.charge(
                    ChargeRequest.newBuilder()
                            .setCreditCard(CreditCardInfo.newBuilder()
                                    .setCreditCardNumber("3714-4963-5398-431")
                                    .setCreditCardCvv(123)
                                    .setCreditCardExpirationMonth(10)
                                    .setCreditCardExpirationYear(LocalDate.now().getYear()+1)
                                    .build())
                            .build());
        });
    }
}