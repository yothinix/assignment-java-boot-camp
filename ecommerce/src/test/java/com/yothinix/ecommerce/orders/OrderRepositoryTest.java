package com.yothinix.ecommerce.orders;

import com.yothinix.ecommerce.orders.entity.Order;
import com.yothinix.ecommerce.orders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void findByIdShouldReturnOrderTest() {
        Order order = new Order();
        order.setId(1);
        order.setUserId(2);
        order.setPaymentId(3);
        order.setShippingId(4);
        order.setTotalAmount(Double.valueOf("399.0"));
        order.setTransactionDate(new Date());
        order.setExpiredDate(new Date());
        order.setOrderStatus("pending");
        testEntityManager.persist(order);

        Optional<Order> orderOptional = orderRepository.findById(1);
        Order actual = orderOptional.get();

        assertEquals(1, actual.getId());
        assertEquals(2, actual.getUserId());
        assertEquals(3, actual.getPaymentId());
        assertEquals(4, actual.getShippingId());
        assertEquals(Double.valueOf("399.0"), actual.getTotalAmount());
        assertNotNull(actual.getTransactionDate());
        assertNotNull(actual.getExpiredDate());
        assertEquals("pending", actual.getOrderStatus());
    }
}