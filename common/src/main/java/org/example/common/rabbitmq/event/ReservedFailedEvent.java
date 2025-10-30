package org.example.common.rabbitmq.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservedFailedEvent {
    private int orderId;
}
