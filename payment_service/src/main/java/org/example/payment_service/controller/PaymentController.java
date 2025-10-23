package org.example.payment_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.payment_service.request.PaymenRequest;
import org.example.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-url")
    public String createUrlPayment(HttpServletRequest request,
                                   @RequestBody PaymenRequest paymenRequest) {

        return paymentService.createPayment(paymenRequest, request);
    }

    @GetMapping("/callback")
    public boolean vnpayCallback(HttpServletRequest request) {
        boolean isSuccess = paymentService.confirmPayment(request);
        return isSuccess;
    }
}
