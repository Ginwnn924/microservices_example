package org.example.order_service.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private int bookingId;
    private long amount;
    private String status;
    private String url;
}
