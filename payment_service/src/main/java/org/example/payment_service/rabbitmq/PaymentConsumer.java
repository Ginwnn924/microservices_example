package org.example.payment_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.rabbitmq.constant.PaymentConstant;
import org.example.common.rabbitmq.event.PaymentProcessedEvent;
import org.example.common.rabbitmq.event.PaymentRequestedEvent;
import org.example.payment_service.request.PaymenRequest;
import org.example.payment_service.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentProducer paymentProducer;
    private final PaymentService paymentService;

    @RabbitListener(queues = PaymentConstant.PAYMENT_REQUESTED_QUEUE)
    public void handlePaymentProcessed(PaymentRequestedEvent event) {
        log.info("Received payment processed event");

        PaymenRequest paymenRequest = new PaymenRequest();
        paymenRequest.setBookingId(event.getOrderId());
        paymenRequest.setAmount(event.getTotalPrice());
        paymenRequest.setIpAddress(event.getIpAddress());

        String paymentUrl = paymentService.createPayment(paymenRequest);

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();
        paymentProcessedEvent.setOrderId(event.getOrderId());
        paymentProcessedEvent.setPaymentUrl(paymentUrl);

        paymentProducer.sendPaymentProcessed(paymentProcessedEvent);
    }
}
