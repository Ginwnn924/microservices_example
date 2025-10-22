package org.example.order_service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {
    @RabbitListener(queues = "order.failed.queue")
    public void handleOrderFailed(InventoryFailed event) {
        log.info("Received order failed event: {}", event);
    }
}
