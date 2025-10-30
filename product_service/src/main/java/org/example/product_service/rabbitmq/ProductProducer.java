package org.example.product_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.constant.ProductConstant;
import org.example.common.rabbitmq.event.ReservedEvent;
import org.example.common.rabbitmq.event.ReservedFailedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendInventoryFailedEvent(ReservedFailedEvent message) {
        rabbitTemplate.convertAndSend(OrderConstant.ORDER_EXCHANGE, OrderConstant.ORDER_FAILED_ROUTING_KEY, message);
    }


    public void sendReservedEvent(ReservedEvent event) {
        rabbitTemplate.convertAndSend(ProductConstant.PRODUCT_EXCHANGE, ProductConstant.PRODUCT_RESERVED_ROUTING_KEY, event);
    }

}
