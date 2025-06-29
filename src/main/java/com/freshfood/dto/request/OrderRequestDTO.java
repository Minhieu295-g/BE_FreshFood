package com.freshfood.dto.request;

import com.freshfood.dto.response.CartItemReponseDTO;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO implements Serializable {
    private double totalPrice;
    private String note;
    private double deliveryFee;
    private String expectedDeliveryDate;
    private String paymentMethod;
    private int voucherId;
    private int deliveryAddressId;
    private int userId;
    private Set<CartItemReponseDTO> cartItems;
}
