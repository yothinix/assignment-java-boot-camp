package com.yothinix.ecommerce.payments;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private PaymentRepository paymentRepository;

    @Test
    void createPaymentSuccessTest() {
        Payment payment = new Payment();
        payment.setUserId(1);
        payment.setMethod("credit");
        payment.setNumber("1111222233334444");
        payment.setName("Human card");
        payment.setPaymentExpiryDate("0222");
        payment.setSecureCode("123");
        when(paymentRepository.save(Mockito.any())).thenReturn(payment);

        Payment actual = testRestTemplate.postForObject("/payments", payment, Payment.class);

        assertEquals(1, actual.getUserId());
        assertEquals("credit", actual.getMethod());
        assertEquals("1111222233334444", actual.getNumber());
        assertEquals("Human card", actual.getName());
        assertEquals("0222", actual.getPaymentExpiryDate());
        assertEquals("123", actual.getSecureCode());
    }
}