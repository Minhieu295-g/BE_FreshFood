package com.freshfood.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
public class OrderRequestDTO implements Serializable {
    private double totalPrice;
    private String note;
    private double deliveryFee;
    private String expectedDeliveryDate;
    private String paymentMethod;
    private int voucherId;
    private int deliveryAddressId;
    private int userId;
    private Set<CartItemRequestDTO> cartItems;
}
