package com.yothinix.ecommerce.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }
}
