package com.yothinix.ecommerce.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping("/orders/{id}")
    public OrderResponse orderDetail(@PathVariable("id") Integer orderId) {
        return orderService.get(orderId);
    }

    @PatchMapping("/orders/{id}")
    public OrderResponse updateOrder(@PathVariable("id") Integer id, @RequestBody OrderUpdateRequest request) {
        return orderService.update(id, request);
    }
}
