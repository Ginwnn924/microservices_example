package org.example.product_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.rabbitmq.constant.OrderConstant;
import org.example.common.rabbitmq.event.OrderCreatedEvent;
import org.example.common.rabbitmq.event.ReservedEvent;
import org.example.common.rabbitmq.event.ReservedFailedEvent;
import org.example.product_service.entity.Product;
import org.example.product_service.repository.ProductRepository;
import org.example.product_service.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final ProductProducer productProducer;
    private final ProductService productService;


    @RabbitListener(queues = OrderConstant.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        boolean isSuccess = productService.decreaseQuantity(event.getListItems());
        if (isSuccess) {
            log.info("Order created successfully");
            ReservedEvent reservedEvent = ReservedEvent.builder()
                    .orderId(event.getOrderId())
                    .totalPrice(event.getTotalPrice())
                    .ipAddress(event.getIpAddress())
                    .build();

            productProducer.sendReservedEvent(reservedEvent);
        }
        else {
            // Send event to order service to change status to FAILED
            productProducer.sendInventoryFailedEvent(ReservedFailedEvent.builder()
                    .orderId(event.getOrderId())
                    .build());
        }

    }
}
