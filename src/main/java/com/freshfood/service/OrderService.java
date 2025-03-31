package com.freshfood.service;

import com.freshfood.dto.request.OrderRequestDTO;
import com.freshfood.dto.response.PageResponse;

public interface OrderService {
    String addOrder(OrderRequestDTO orderRequestDTO);
    PageResponse getOrdersByUserId(int userId, int pageNo, int pageSize);
}
