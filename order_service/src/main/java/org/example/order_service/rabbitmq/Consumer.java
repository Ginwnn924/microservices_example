package org.example.order_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("📩 Received Order Created Event: " + event);
        // Here you can add logic to process the event, e.g., update order status, notify other services, etc.
    }
}
