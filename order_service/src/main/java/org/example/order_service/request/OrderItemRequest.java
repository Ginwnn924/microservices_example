package org.example.order_service.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private int productId;
    private long price;
    private int quantity;
}
