package org.example.order_service.rabbitmq.event;

import lombok.*;
import org.example.order_service.entity.OrderItemEntity;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreatedEvent implements Serializable {
    private int orderId;
    private long totalPrice;
    private List<OrderItemEvent> listItems;
}
