package com.yothinix.ecommerce.payments;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentGatewayService {
    public ChargeResponse charge(Payment payment, String payee) {
        if (payment.getNumber() != null
                && payment.getPaymentExpiryDate() != null
                && payment.getSecureCode() != null
                && payee != null) {
            return new ChargeResponse("0000", "success", UUID.randomUUID().toString());
        } else {
            return new ChargeResponse("0001", "error", UUID.randomUUID().toString());
        }
    }
}
