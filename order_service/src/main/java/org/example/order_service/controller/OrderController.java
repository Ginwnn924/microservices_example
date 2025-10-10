package org.example.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.order_service.ProductClient;
import org.example.order_service.entity.Order;
import org.example.order_service.response.OrderResponse;
import org.example.order_service.response.ProductResponse;
import org.example.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductClient productClient;

    @PostMapping
    public Order createOrder(@RequestBody final Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable final int id) {
        return orderService.getOrderById(id);
    }

}
