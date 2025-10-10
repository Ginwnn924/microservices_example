package org.example.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.Order;
import org.example.order_service.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public Order createOrder(@RequestBody final Order order) {
        return orderService.createOrder(order);
    }
}
