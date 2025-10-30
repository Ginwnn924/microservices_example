package org.example.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "orders")
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String paymentUrl;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> listItems;
}
