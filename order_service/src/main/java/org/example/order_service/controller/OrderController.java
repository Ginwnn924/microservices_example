package org.example.order_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.order_service.ProductClient;
import org.example.order_service.entity.OrderEntity;
import org.example.order_service.request.OrderRequest;
import org.example.order_service.response.OrderResponse;
import org.example.order_service.response.PaymentResponse;
import org.example.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductClient productClient;

    @PostMapping
    public PaymentResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

}
