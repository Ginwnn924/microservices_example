package org.example.order_service.rabbitmq.event;

import lombok.Data;

@Data
public class InventoryFailedEvent {
    private int orderId;
}
