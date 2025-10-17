package org.example.order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.order_service.ProductClient;
import org.example.order_service.entity.Order;
import org.example.order_service.entity.OrderStatus;
import org.example.order_service.rabbitmq.OrderPublisher;
import org.example.order_service.repository.OrderRepository;
import org.example.order_service.response.OrderResponse;
import org.example.order_service.response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderPublisher  orderPublisher;

    public Order createOrder(final Order order) {
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);
//        orderPublisher.sendOrderCreated(order);
        return order;
    }

    public OrderResponse getOrderById(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        ProductResponse product = productClient.getProductById(order.getProductId());
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(String.valueOf(order.getTotalPrice()))
                .status(order.getStatus())
                .product(product)
                .build();
    }



}
