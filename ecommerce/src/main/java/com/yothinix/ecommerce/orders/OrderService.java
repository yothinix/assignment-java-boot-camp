package com.yothinix.ecommerce.orders;

import com.yothinix.ecommerce.exceptions.OrderNotFoundException;
import com.yothinix.ecommerce.exceptions.OrderRequestInvalidException;
import com.yothinix.ecommerce.orders.entity.Order;
import com.yothinix.ecommerce.orders.entity.OrderItem;
import com.yothinix.ecommerce.orders.repository.OrderItemRepository;
import com.yothinix.ecommerce.orders.repository.OrderRepository;
import com.yothinix.ecommerce.payments.Payment;
import com.yothinix.ecommerce.payments.PaymentRepository;
import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.entity.UserAddress;
import com.yothinix.ecommerce.users.repository.UserAddressRepository;
import com.yothinix.ecommerce.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private String notFoundTemplate = "%s id: %s is not found";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Transactional
    public OrderResponse create(OrderRequest orderRequest) {
        Optional<Product> optionalProduct = productRepository.findById(orderRequest.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new OrderRequestInvalidException(String.format(notFoundTemplate, "Product", orderRequest.getProductId()));
        }
        Product product = optionalProduct.get();

        Optional<User> optionalUser = userRepository.findById(orderRequest.getUserId());
        if (optionalUser.isEmpty()) {
            throw new OrderRequestInvalidException(String.format(notFoundTemplate, "User", orderRequest.getUserId()));
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

    public OrderResponse get(Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(String.format(notFoundTemplate, "Order", orderId));
        }

        return getOrderResponse(optionalOrder.get());
    }

    public OrderResponse update(Integer id, OrderUpdateRequest request) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(String.format(notFoundTemplate, "Order", id));
        }
        Order order = optionalOrder.get();

        if (request.getPaymentId() != null) {
            Optional<Payment> optionalPayment = paymentRepository.findById(request.getPaymentId());
            if (optionalPayment.isEmpty()) {
                throw new OrderRequestInvalidException(String.format(notFoundTemplate, "Payment", request.getPaymentId()));
            }
            Payment payment = optionalPayment.get();
            order.setPaymentId(payment.getId());
        }

        if (request.getShippingId() != null) {
            Optional<UserAddress> optionalShipping = userAddressRepository.findById(request.getShippingId());
            if (optionalShipping.isEmpty()) {
                throw new OrderRequestInvalidException(String.format(notFoundTemplate, "UserAddress", request.getShippingId()));
            }
            UserAddress shipping = optionalShipping.get();
            order.setShippingId(shipping.getId());
        }

        return getOrderResponse(order);
    }

    private OrderResponse getOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse(order);

        if (order.getUserId() != null) {
            Optional<User> optionalUser = userRepository.findById(order.getUserId());
            optionalUser.ifPresent(orderResponse::setUser);
        }

        if (order.getPaymentId() != null) {
            Optional<Payment> optionalPayment = paymentRepository.findById(order.getPaymentId());
            optionalPayment.ifPresent(orderResponse::setPayment);
        }

        if (order.getShippingId() != null) {
            Optional<UserAddress> optionalShipping = userAddressRepository.findById(order.getShippingId());
            optionalShipping.ifPresent(orderResponse::setShipping);
        }

        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(order.getId());
        orderResponse.setOrderItems(orderItems);
        return orderResponse;
    }
}
