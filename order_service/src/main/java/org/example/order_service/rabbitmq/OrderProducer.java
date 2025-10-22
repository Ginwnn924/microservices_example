package org.example.order_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.example.order_service.rabbitmq.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;


    public void sendOrderCreated(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_CREATED_ROUTING_KEY,
                event);

    }
}
