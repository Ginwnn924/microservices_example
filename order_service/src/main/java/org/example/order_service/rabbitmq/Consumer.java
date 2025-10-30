package org.example.order_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.order_service.entity.OrderStatus;
import org.example.order_service.rabbitmq.event.InventoryFailedEvent;
import org.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    private final OrderRepository orderRepository;
    @RabbitListener(queues = "order.failed.queue")
    public void handleOrderFailed(InventoryFailedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresentOrElse(order -> {
            order.setStatus(OrderStatus.OUT_OF_STOCK);
            orderRepository.save(order);
            log.info("Out of stock: {}", order);
        }, () -> {
            log.warn("Order not found: {}", event.getOrderId());
        });
    }

    @RabbitListener(queues = "order.reserved.queue")
    public void handleOrderReserved() {
            log.info("Received order reserved event");
    }
}
