package org.example.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.order_service.ProductClient;
import org.example.order_service.entity.OrderEntity;
import org.example.order_service.request.OrderRequest;
import org.example.order_service.response.OrderResponse;
import org.example.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductClient productClient;

    @PostMapping
    public OrderResponse createOrder(@RequestBody final OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable final int id) {
        return orderService.getOrderById(id);
    }

}
