package org.example.order_service.rabbitmq;

import lombok.Data;

@Data
public class InventoryFailed {
    private int orderId;
}
