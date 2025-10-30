package org.example.common.rabbitmq.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestedEvent implements Serializable {
    private int orderId;
    private long totalPrice;
    private String ipAddress;
}
