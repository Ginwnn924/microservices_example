package org.example.order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.Order;
import org.example.order_service.entity.OrderStatus;
import org.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(final Order order) {
        order.setStatus(OrderStatus.CREATED);
        return orderRepository.save(order);
    }

}
