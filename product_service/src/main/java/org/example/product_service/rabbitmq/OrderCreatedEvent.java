package org.example.product_service.rabbitmq;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private int orderId;
    private long totalPrice;
}
