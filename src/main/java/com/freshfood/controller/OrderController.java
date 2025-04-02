package com.freshfood.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freshfood.dto.request.OrderRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.model.Order;
import com.freshfood.service.OrderService;
import com.freshfood.service.PaymentService;
import com.freshfood.util.PaymentMethodsEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.freshfood.util.PaymentMethodsEnum.COD;

@RequestMapping("/order")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;
    @PostMapping("/")
    public ResponseData<?> addOrder(HttpServletRequest request,
                                    @RequestBody OrderRequestDTO order)
            throws IOException, JsonProcessingException {

        if (order.getPaymentMethod().equals(COD.toString())) {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Order created successfully", orderService.addOrder(order));
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("pendingOrder", order);
            String paymentUrl = paymentService.createVnPayPayment(request, order).getData().toString();
            return new ResponseData<>(HttpStatus.OK.value(), "Redirect to VNPay", paymentUrl);
        }
    }
    @GetMapping("/vnpay-callback")
    public RedirectView handleVnPayCallback(HttpServletRequest request, HttpSession session) {
        OrderRequestDTO pendingOrder = (OrderRequestDTO) session.getAttribute("pendingOrder");
        if (pendingOrder == null) {
            return new RedirectView("http://localhost:3000/order-failed");        }
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            String orderCode = orderService.addOrder(pendingOrder);
            session.removeAttribute("pendingOrder"); // Xóa đơn hàng khỏi session sau khi xử lý
            return new RedirectView("http://localhost:3000/order-success?orderCode=" + orderCode);
        } else {
            return new RedirectView("http://localhost:3000/order-failed");
        }
    }
    @GetMapping("/list/{userId}")
    public ResponseData<?> getOrdersByUserId(@PathVariable int userId, @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "15") int pageSize) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list order successfully", orderService.getOrdersByUserId(userId,pageNo,pageSize));
    }



}
