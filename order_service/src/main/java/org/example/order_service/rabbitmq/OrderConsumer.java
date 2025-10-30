package org.example.order_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.event.ReservedFailedEvent;
import org.example.order_service.entity.OrderStatus;
import org.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    private final OrderRepository orderRepository;
    @RabbitListener(queues = OrderConstant.ORDER_FAILED_QUEUE)
    public void handleOrderFailed(ReservedFailedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresentOrElse(order -> {
            order.setStatus(OrderStatus.OUT_OF_STOCK);
            orderRepository.save(order);
            log.info("Out of stock: {}", order);
        }, () -> {
            log.warn("Order not found: {}", event.getOrderId());
        });
    }

    @RabbitListener(queues = OrderConstant.ORDER_RESERVED_QUEUE)
    public void handleOrderReserved() {
            log.info("Received order reserved event");
    }
}
