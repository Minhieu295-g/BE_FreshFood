package com.freshfood.controller;

import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api/vnpay")
@RequiredArgsConstructor
public class VnPayController {

    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseData<?> pay(HttpServletRequest request, @RequestParam("amount") String amount) throws UnsupportedEncodingException {
        return null;
    }

}
