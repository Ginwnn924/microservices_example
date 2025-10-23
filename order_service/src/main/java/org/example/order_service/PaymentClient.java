package org.example.order_service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.order_service.request.PaymenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/payments/create-url")
    String createPaymentUrl(@RequestBody PaymenRequest paymenRequest);
}
