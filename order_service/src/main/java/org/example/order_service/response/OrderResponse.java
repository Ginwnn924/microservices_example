package org.example.order_service.response;

import lombok.*;
import org.example.order_service.entity.OrderItemEntity;
import org.example.order_service.entity.OrderStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private int id;
    private long totalPrice;
    private OrderStatus status;
    private List<OrderItemEntity> listItems;
}
