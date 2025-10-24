package org.example.product_service.rabbitmq;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryFailed {
    private int orderId;

}
