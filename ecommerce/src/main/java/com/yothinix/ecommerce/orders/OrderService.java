package com.yothinix.ecommerce.orders;

import com.yothinix.ecommerce.exceptions.OrderRequestInvalidException;
import com.yothinix.ecommerce.orders.entity.Order;
import com.yothinix.ecommerce.orders.entity.OrderItem;
import com.yothinix.ecommerce.orders.repository.OrderItemRepository;
import com.yothinix.ecommerce.orders.repository.OrderRepository;
import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse create(OrderRequest orderRequest) {
        Optional<Product> optionalProduct = productRepository.findById(orderRequest.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new OrderRequestInvalidException("Product id: " + orderRequest.getProductId() + " is not found");
        }
        Product product = optionalProduct.get();

        Optional<User> optionalUser = userRepository.findById(orderRequest.getUserId());
        if (optionalUser.isEmpty()) {
            throw new OrderRequestInvalidException("User id: " + orderRequest.getUserId() + " is not found");
        }
        User user = optionalUser.get();

        Order order = new Order();
        order.setUserId(user.getId());
        order.setTotalAmount(product.getPrice());
        order.setTransactionDate(new Date());
        order.setExpiredDate(new Date());
        order.setOrderStatus("pending");
        Order createdOrder = orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(createdOrder.getId());
        orderItem.setProductId(product.getId());
        orderItem.setAmount(product.getPrice());
        orderItem.setPaymentPrice(product.getPrice());
        orderItemRepository.save(orderItem);

        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(createdOrder.getId());

        OrderResponse orderResponse = new OrderResponse(order);
        orderResponse.setUser(user);
        orderResponse.setOrderItems(orderItems);

        return orderResponse;
    }
}
