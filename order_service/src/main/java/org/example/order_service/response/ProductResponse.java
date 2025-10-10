package org.example.order_service.response;

import lombok.Data;

@Data
public class ProductResponse {
    private int id;
    private String name;
    private long price;
    private int quantity;
}
