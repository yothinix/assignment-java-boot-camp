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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private UserAddressRepository userAddressRepository;

    @BeforeEach
    void setUp() {
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

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
        OrderResponse actual = testRestTemplate.postForObject("/orders", request, OrderResponse.class);

        assertEquals(1, actual.getUser().getId());
        assertEquals(2, actual.getOrderItems().get(0).getProductId());
    }

    @Test
    void createOrderShouldReturnBadRequestWhenProductNotFoundTest() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());

        OrderRequest request = new OrderRequest(1, 2);
        ResponseEntity<OrderResponse> actual = testRestTemplate.postForEntity("/orders", request, OrderResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void createOrderShouldReturnBadRequestWhenUserNotFoundTest() {
        Product product = new Product();
        product.setId(2);
        when(productRepository.findById(2)).thenReturn(Optional.of(product));

        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        OrderRequest request = new OrderRequest(1, 2);

        ResponseEntity<OrderResponse> actual = testRestTemplate.postForEntity("/orders", request, OrderResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void getOrderDetailSuccessTest() {
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

        OrderResponse actual = testRestTemplate.getForObject("/orders/1", OrderResponse.class);

        assertEquals(1, actual.getId());
        assertEquals(1, actual.getUser().getId());
        assertEquals(2, actual.getPayment().getId());
        assertEquals(3, actual.getShipping().getId());
        assertEquals(2, actual.getOrderItems().get(0).getProductId());
    }

    @Test
    void getOrderDetailShouldReturnNotFoundWhenOrderIdNotMatchTest() {
        ResponseEntity<OrderResponse> actual = testRestTemplate.getForEntity("/orders/1", OrderResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
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

        OrderResponse actual = testRestTemplate.patchForObject("/orders/1", request, OrderResponse.class);

        assertEquals(1, actual.getId());
        assertEquals(2, actual.getPayment().getId());
        assertEquals(3, actual.getShipping().getId());
    }

    @Test
    void orderUpdateShouldThrowOrderNotFoundWhenOrderIdNotMatchTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        OrderUpdateRequest order = new OrderUpdateRequest();
        order.setId(1);
        order.setPaymentId(2);
        order.setShippingId(3);
        HttpEntity<OrderUpdateRequest> request = new HttpEntity<>(order);

        ResponseEntity<OrderResponse> actual = testRestTemplate.exchange("/orders/1", HttpMethod.PATCH, request, OrderResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    void orderUpdateShouldThrowRequestInvalidWhenPaymentNotFoundTest() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        when(paymentRepository.findById(2)).thenReturn(Optional.empty());

        OrderUpdateRequest orderRequest = new OrderUpdateRequest();
        orderRequest.setId(1);
        orderRequest.setPaymentId(2);
        orderRequest.setShippingId(3);
        HttpEntity<OrderUpdateRequest> request = new HttpEntity<>(orderRequest);

        ResponseEntity<OrderResponse> actual = testRestTemplate.exchange("/orders/1", HttpMethod.PATCH, request, OrderResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
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

        OrderUpdateRequest orderRequest = new OrderUpdateRequest();
        orderRequest.setId(1);
        orderRequest.setPaymentId(2);
        orderRequest.setShippingId(3);
        HttpEntity<OrderUpdateRequest> request = new HttpEntity<>(orderRequest);

        ResponseEntity<OrderResponse> actual = testRestTemplate.exchange("/orders/1", HttpMethod.PATCH, request, OrderResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }
}