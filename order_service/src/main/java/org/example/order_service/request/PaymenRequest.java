package org.example.order_service.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymenRequest {
    private int bookingId;
    private long amount;
}
