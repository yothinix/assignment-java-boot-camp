package com.yothinix.ecommerce.payments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentGatewayServiceTest {

    @InjectMocks
    PaymentGatewayService paymentGatewayService;

    @Test
    void chargeSuccessTest() {
        Payment payment = new Payment();
        payment.setUserId(1);
        payment.setMethod("credit");
        payment.setNumber("1111222233334444");
        payment.setName("Human card");
        payment.setPaymentExpiryDate("0222");
        payment.setSecureCode("123");

        ChargeResponse actual = paymentGatewayService.charge(payment, "lazada");

        assertEquals("0000", actual.getStatusCode());
        assertEquals("success", actual.getDescription());
        assertNotNull(actual.getTransactionId());
    }

    @Test
    void chargeFailedTest() {
        Payment payment = new Payment();

        ChargeResponse actual = paymentGatewayService.charge(payment, "lazada");

        assertEquals("0001", actual.getStatusCode());
        assertEquals("error", actual.getDescription());
        assertNotNull(actual.getTransactionId());
    }
}