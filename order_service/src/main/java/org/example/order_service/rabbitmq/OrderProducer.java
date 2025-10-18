package org.example.order_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;


    public void sendOrderCreated(Order order) {
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, orderCreatedEvent);

    }
}
