package com.yothinix.ecommerce.payments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void findByIdShouldReturnPaymentTest() {
        Payment payment = new Payment();
        payment.setUserId(1);
        payment.setMethod("credit");
        payment.setNumber("1111222233334444");
        payment.setName("HuMan test");
        payment.setPaymentExpiryDate("0222");
        payment.setSecureCode("123");
        paymentRepository.save(payment);

        Optional<Payment> paymentOptional = paymentRepository.findById(1);
        Payment actual = paymentOptional.get();

        assertEquals(1, actual.getId());
        assertEquals("HuMan test", actual.getName());
    }

    @Test
    void findByIdShouldReturnEmptyWhenPaymentNotFoundTest() {
        Optional<Payment> actual = paymentRepository.findById(1);

        assertTrue(actual.isEmpty());
    }
}