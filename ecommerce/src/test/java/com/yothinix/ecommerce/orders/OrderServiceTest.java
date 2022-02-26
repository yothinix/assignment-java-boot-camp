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
}