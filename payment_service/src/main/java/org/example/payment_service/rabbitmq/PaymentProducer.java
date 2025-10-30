package org.example.payment_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.example.common.rabbitmq.constant.PaymentConstant;
import org.example.common.rabbitmq.event.PaymentFailedEvent;
import org.example.common.rabbitmq.event.PaymentProcessedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendPaymentProcessed(PaymentProcessedEvent event) {
        rabbitTemplate.convertAndSend(PaymentConstant.PAYMENT_EXCHANGE,
                PaymentConstant.PAYMENT_PROCESSED_ROUTING_KEY,
                event);
    }

    public void sendPaymentFailed(PaymentFailedEvent event) {
        rabbitTemplate.convertAndSend(PaymentConstant.PAYMENT_EXCHANGE,
                PaymentConstant.PAYMENT_FAILED_ROUTING_KEY,
                event);
    }


}
