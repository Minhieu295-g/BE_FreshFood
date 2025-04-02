package com.freshfood.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class OrderResponseDTO {
    private int id;
    private String orderNumber;
    private Date date;
    private double totalPrice;
    private double shippingFee;
    private Date expectedDate;
    private String paymentMethod;
    private String status;
    private String note;
    private List<OrderItemResponseDTO> items;
    private DeliveryAddressResponseDTO shippingAddress;
}
