package org.example.order_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.constant.PaymentConstant;
import org.example.common.rabbitmq.constant.ProductConstant;
import org.example.common.rabbitmq.event.PaymentProcessedEvent;
import org.example.common.rabbitmq.event.PaymentRequestedEvent;
import org.example.common.rabbitmq.event.ReservedEvent;
import org.example.common.rabbitmq.event.ReservedFailedEvent;
import org.example.order_service.entity.OrderStatus;
import org.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;


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

    @RabbitListener(queues = ProductConstant.PRODUCT_RESERVED_QUEUE)
    public void handleOrderReserved(ReservedEvent event) {
        log.info("Order reserved: {} - {} - {}", event.getOrderId(), event.getTotalPrice(), event.getIpAddress());

        PaymentRequestedEvent paymentRequestedEvent = PaymentRequestedEvent.builder()
                .orderId(event.getOrderId())
                .totalPrice(event.getTotalPrice())
                .ipAddress(event.getIpAddress())
                .build();

        orderProducer.sendPaymentRequest(paymentRequestedEvent);
    }

    @RabbitListener(queues = PaymentConstant.PAYMENT_PROCESSED_QUEUE)
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        log.info("Received payment processed event: {} {}", event.getOrderId(), event.getPaymentUrl());
        orderRepository.findById(event.getOrderId()).ifPresentOrElse(order -> {
            order.setPaymentUrl(event.getPaymentUrl());
            orderRepository.save(order);
            log.info("Payment is ready: {}", order);
        }, () -> {
            log.warn("Order not found: {}", event.getOrderId());
        });
    }
}
