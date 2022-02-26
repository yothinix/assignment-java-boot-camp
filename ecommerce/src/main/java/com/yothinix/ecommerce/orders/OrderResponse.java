package com.yothinix.ecommerce.orders;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yothinix.ecommerce.orders.entity.Order;
import com.yothinix.ecommerce.orders.entity.OrderItem;
import com.yothinix.ecommerce.payments.Payment;
import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderResponse extends Order {
    private User user;
    private Payment payment;
    private UserAddress shipping;
    private List<OrderItem> orderItems;

    public OrderResponse(Order order) {
        this.setId(order.getId());
        this.setUserId(order.getUserId());
        this.setPaymentId(order.getPaymentId());
        this.setShippingId(order.getShippingId());
        this.setTotalAmount(order.getTotalAmount());
        this.setTransactionDate(order.getTransactionDate());
        this.setExpiredDate(order.getExpiredDate());
        this.setOrderStatus(order.getOrderStatus());
    }
}
