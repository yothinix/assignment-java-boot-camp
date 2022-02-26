package com.yothinix.ecommerce.orders.repository;

import com.yothinix.ecommerce.orders.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findOrderItemByOrderId(Integer orderId);
}
