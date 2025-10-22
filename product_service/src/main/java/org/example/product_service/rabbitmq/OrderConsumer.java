package org.example.product_service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.product_service.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final ProductProducer productProducer;
    private final ProductRepository productRepository;
    @RabbitListener(queues = "order.created.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        if (event.getTotalPrice() >= 50000) {
            InventoryFailed inventoryFailed = new InventoryFailed();
            inventoryFailed.setOrderId(event.getOrderId());
            productProducer.sendInventoryFailedEvent(inventoryFailed);
        }

    }
}
