package org.example.common.rabbitmq.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent implements Serializable {
    private int orderId;
    private long totalPrice;
    private List<OrderItemEvent> listItems;
    private String ipAddress;
}
