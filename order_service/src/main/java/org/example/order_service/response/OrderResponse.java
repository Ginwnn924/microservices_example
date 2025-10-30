package org.example.order_service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.example.order_service.entity.OrderItemEntity;
import org.example.order_service.entity.OrderStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private int id;
    private long totalPrice;
    private OrderStatus status;
    private String urlPayment;
    private List<OrderItemEntity> listItems;
}
