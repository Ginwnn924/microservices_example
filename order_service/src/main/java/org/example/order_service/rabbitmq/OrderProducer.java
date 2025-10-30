package org.example.order_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.constant.PaymentConstant;
import org.example.common.rabbitmq.event.OrderCreatedEvent;
import org.example.common.rabbitmq.event.PaymentRequestedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;


    public void sendOrderCreated(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(OrderConstant.ORDER_EXCHANGE,
                OrderConstant.ORDER_CREATED_ROUTING_KEY,
                event);
    }

    public void sendPaymentRequest(PaymentRequestedEvent event) {
        rabbitTemplate.convertAndSend(PaymentConstant.PAYMENT_EXCHANGE,
                PaymentConstant.PAYMENT_REQUESTED_ROUTING_KEY,
                event);
    }
}
