package org.example.order_service.rabbitmq.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEvent implements Serializable {
    private int productId;
    private int quantity;
    private long price;
}