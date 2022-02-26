package com.yothinix.ecommerce.payments;

import com.yothinix.ecommerce.users.User;
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
        Payment payment = new Payment(1, 1, "credit", "1111222233334444", "HuMan test", "0222", "123");
        testEntityManager.persist(payment);

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