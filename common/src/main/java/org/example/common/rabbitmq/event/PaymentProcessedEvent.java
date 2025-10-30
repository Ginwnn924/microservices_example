package org.example.common.rabbitmq.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessedEvent implements Serializable {
    private int orderId;
    private String paymentUrl;
}
