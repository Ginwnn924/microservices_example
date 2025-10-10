package org.example.product_service.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private int id;
    private String name;
    private long price;
    private int quantity;
}
