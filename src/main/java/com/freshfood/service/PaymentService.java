package com.freshfood.service;

import com.freshfood.configuration.VnPayConfig;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.util.VnPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VnPayConfig vnPayConfig;
    public ResponseData<?> createVnPayPayment(HttpServletRequest request, String amount) throws UnsupportedEncodingException {
        long amountt = Integer.parseInt(amount) * 100;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amountt));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", "127.0.0.1");
        //build query url
        String queryUrl = VnPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VnPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return new ResponseData<>(HttpStatus.OK.value(), "success", paymentUrl);
    }
}
