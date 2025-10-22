package org.example.order_service.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemResponse {
    private int id;
    private String name;
    private long price;
    private int quantity;
}
