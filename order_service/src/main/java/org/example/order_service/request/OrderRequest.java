package org.example.order_service.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.order_service.response.OrderItemResponse;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private long totalPrice;
    private List<OrderItemRequest> listItems;
}
