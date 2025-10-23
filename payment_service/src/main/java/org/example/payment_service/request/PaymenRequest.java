package org.example.payment_service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymenRequest {
    private int bookingId;
    private long amount;
}

