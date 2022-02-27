package com.yothinix.ecommerce.orders;

import com.yothinix.ecommerce.orders.entity.OrderItem;
import com.yothinix.ecommerce.orders.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderItemRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Test
    void findOrderItemByOrderIdFoundOrderItemTest() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderId(1);
        orderItem1.setProductId(1);
        orderItem1.setAmount(Double.valueOf("399.0"));
        orderItem1.setPaymentPrice(Double.valueOf("399.0"));
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderId(2);
        orderItem2.setProductId(2);
        orderItem2.setAmount(Double.valueOf("500.0"));
        orderItem2.setPaymentPrice(Double.valueOf("500.0"));
        testEntityManager.persist(orderItem1);
        testEntityManager.persist(orderItem2);

        List<OrderItem> actual = orderItemRepository.findOrderItemByOrderId(1);

        assertEquals(1, actual.size());
        assertEquals(1, actual.get(0).getProductId());
    }

    @Test
    void findOrderItemByOrderIdNotFoundTest() {
        List<OrderItem> actual = orderItemRepository.findOrderItemByOrderId(1);

        assertEquals(0, actual.size());
    }
}