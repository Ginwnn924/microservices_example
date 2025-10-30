package org.example.common.rabbitmq.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentFailedEvent {
    private int orderId;
}
