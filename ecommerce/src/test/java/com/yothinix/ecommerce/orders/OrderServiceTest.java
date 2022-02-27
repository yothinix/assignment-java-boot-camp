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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    UserAddressRepository userAddressRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void createOrderSuccessTest() {
        Product product = new Product();
        product.setId(2);
        when(productRepository.findById(2)).thenReturn(Optional.of(product));

        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Order order = new Order();
        order.setId(1);
        order.setUserId(user.getId());
        when(orderRepository.save(Mockito.any())).thenReturn(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(2);
        when(orderItemRepository.findOrderItemByOrderId(1)).thenReturn(List.of(orderItem));

        OrderRequest request = new OrderRequest(1, 2);

        OrderResponse actual = orderService.create(request);

        assertEquals(1, actual.getUserId());
        assertEquals(2, actual.getOrderItems().get(0).getProductId());
    }

    @Test
    void createOrderShouldThrowOrderRequestInvalidExceptionWhenProductNotFoundTest() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());

        OrderRequest request = new OrderRequest(1, 2);

        assertThrows(OrderRequestInvalidException.class, () -> orderService.create(request));
    }

    @Test
    void createOrderShouldThrowOrderRequestInvalidExceptionWhenUserNotFoundTest() {
        Product product = new Product();
        product.setId(2);
        when(productRepository.findById(2)).thenReturn(Optional.of(product));

        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        OrderRequest request = new OrderRequest(1, 2);

        assertThrows(OrderRequestInvalidException.class, () -> orderService.create(request));
    }

    @Test
    void getOrderSuccessTest() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Payment payment = new Payment();
        payment.setId(2);
        when(paymentRepository.findById(2)).thenReturn(Optional.of(payment));

        UserAddress userAddress = new UserAddress();
        userAddress.setId(3);
        when(userAddressRepository.findById(3)).thenReturn(Optional.of(userAddress));

        Order order = new Order();
        order.setId(1);
        order.setUserId(user.getId());
        order.setPaymentId(payment.getId());
        order.setShippingId(userAddress.getId());
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(2);
        when(orderItemRepository.findOrderItemByOrderId(1)).thenReturn(List.of(orderItem));

        OrderResponse actual = orderService.get(1);

        assertEquals(1, actual.getId());
        assertEquals(1, actual.getUser().getId());
        assertEquals(2, actual.getPayment().getId());
        assertEquals(3, actual.getShipping().getId());
        assertEquals(2, actual.getOrderItems().get(0).getProductId());
    }

    @Test
    void getOrderShouldThrowOrderNotFoundExceptionWhenIdNotMatchTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.get(1));
    }

    @Test
    void orderUpdateSuccessTest() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Payment payment = new Payment();
        payment.setId(2);
        when(paymentRepository.findById(2)).thenReturn(Optional.of(payment));

        UserAddress userAddress = new UserAddress();
        userAddress.setId(3);
        when(userAddressRepository.findById(3)).thenReturn(Optional.of(userAddress));

        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setId(1);
        request.setPaymentId(2);
        request.setShippingId(3);

        OrderResponse actual = orderService.update(1, request);

        assertEquals(1, actual.getId());
        assertEquals(2, actual.getPayment().getId());
        assertEquals(3, actual.getShipping().getId());
    }

    @Test
    void orderUpdateShouldThrowOrderNotFoundWhenOrderIdNotMatchTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());


        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setId(1);
        request.setPaymentId(2);
        request.setShippingId(3);

        assertThrows(OrderNotFoundException.class, () -> orderService.update(1, request));
    }

    @Test
    void orderUpdateShouldThrowRequestInvalidWhenPaymentNotFoundTest() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        when(paymentRepository.findById(2)).thenReturn(Optional.empty());

        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setId(1);
        request.setPaymentId(2);
        request.setShippingId(3);

        assertThrows(OrderRequestInvalidException.class, () -> orderService.update(1, request));
    }

    @Test
    void orderUpdateShouldThrowRequestInvalidWhenUserAddressNotFoundTest() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Payment payment = new Payment();
        payment.setId(2);
        when(paymentRepository.findById(2)).thenReturn(Optional.of(payment));

        UserAddress userAddress = new UserAddress();
        userAddress.setId(3);
        when(userAddressRepository.findById(3)).thenReturn(Optional.empty());

        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setId(1);
        request.setPaymentId(2);
        request.setShippingId(3);

        assertThrows(OrderRequestInvalidException.class, () -> orderService.update(1, request));
    }
}