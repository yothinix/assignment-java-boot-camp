package com.yothinix.ecommerce.orders;

import com.yothinix.ecommerce.orders.entity.Order;
import com.yothinix.ecommerce.orders.entity.OrderItem;
import com.yothinix.ecommerce.orders.repository.OrderItemRepository;
import com.yothinix.ecommerce.orders.repository.OrderRepository;
import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        assertEquals(1, actual.getUserId());
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
}