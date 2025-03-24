package com.freshfood.service;

import com.freshfood.dto.request.OrderRequestDTO;

public interface OrderService {
    int addOrder(OrderRequestDTO orderRequestDTO);
}
