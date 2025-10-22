package org.example.product_service.rabbitmq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryFailed {
    private int orderId;

}
