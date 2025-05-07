package com.freshfood.service;

import com.freshfood.dto.request.OrderRequestDTO;
import com.freshfood.dto.request.OrderStatusRequestDTO;
import com.freshfood.dto.response.PageResponse;

public interface OrderService {
    String addOrder(OrderRequestDTO orderRequestDTO);
    void updateStatus(int orderId, OrderStatusRequestDTO status);
    PageResponse getOrdersByUserId(int userId, int pageNo, int pageSize);
    PageResponse getOrders(int pageNo, int pageSize);

}
