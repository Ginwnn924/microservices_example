package org.example.product_service.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent implements Serializable {
    private int orderId;
    private long totalPrice;
    private List<OrderItem> listItems;
}
