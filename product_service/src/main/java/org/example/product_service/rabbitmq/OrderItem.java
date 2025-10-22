package org.example.product_service.rabbitmq;

import lombok.Data;

@Data
public class OrderItem {
    private int productId;
    private long price;
    private int quantity;
}