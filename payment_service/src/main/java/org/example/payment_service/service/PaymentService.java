package org.example.payment_service.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.payment_service.config.VNPAYConfig;
import org.example.payment_service.request.PaymenRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final VNPAYConfig vnPayConfig;


    public String createPayment(PaymenRequest paymenRequest) {
        long amount = (paymenRequest.getAmount()) * 100;

        String vnpayRef = vnPayConfig.getRandomNumber(8);
//        String bankCode = "";
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();

        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", paymenRequest.getIpAddress());
        //
        vnpParamsMap.put("vnp_OrderInfo", paymenRequest.getBookingId() + "");
        vnpParamsMap.put("vnp_TxnRef", vnpayRef);
        //
        String queryUrl = vnPayConfig.getPaymentURL(vnpParamsMap, true);
        String hashData = vnPayConfig.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = vnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return paymentUrl;

    }


    public boolean confirmPayment(HttpServletRequest request) {
        if(!isValidParams(request)) {
            log.error("Invalid parameters in VnPay callback");
            return false;
        }

        // Check response code
        String status = request.getParameter("vnp_ResponseCode");
        if ("00".equals(status)) {
            // Success
            return true;
        }


    }

    private boolean isValidParams(HttpServletRequest request) {
        // Veriy hash
        Enumeration<String> listParam = request.getParameterNames();
        Map<String, String> params = new HashMap<>();
        while (listParam.hasMoreElements()) {
            String paramName = listParam.nextElement();
            String value = request.getParameter(paramName);
            if (!StringUtils.isEmpty(paramName) && !StringUtils.isEmpty(value)) {
                if ("vnp_SecureHash".equals(paramName) || "vnp_SecureHashType".equals(paramName)) {
                    continue;
                }
                params.put(paramName, value);
            }
        }
        String hashData = vnPayConfig.getPaymentURL(params, false);
        String vnpSecureHash = vnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String vnpSecureHashFromParams = request.getParameter("vnp_SecureHash");

        if (!vnpSecureHash.equals(vnpSecureHashFromParams)) {
            return false;
        }
        return true;


    }
}
