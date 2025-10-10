package org.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "orders")
@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productId;
    private int quantity;
    private long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
