package org.example.product_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendInventoryFailedEvent(InventoryFailed message) {
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE, RabbitConfig.ORDER_FAILED_ROUTING_KEY, message);
    }
}
