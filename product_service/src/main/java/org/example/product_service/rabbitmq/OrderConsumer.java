package org.example.product_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @RabbitListener(queues = "order.created.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        boolean isSuccess = productService.decreaseQuantity(event.getListItems());
        if (isSuccess) {
            // Send event to payment service
            log.info("Order created successfully");
        }
        else {
            // Send event to order service to change status to FAILED
            log.info("Order created failed due to insufficient product quantity");
        }

    }
}
