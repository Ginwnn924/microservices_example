package org.example.order_service.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.example.order_service.entity.OrderStatus;

@Data
@Builder
public class OrderResponse {
    private int id;
    private String totalPrice;
    private OrderStatus status;
    private ProductResponse product;
}
