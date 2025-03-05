package com.freshfood.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CartItemRequestDTO implements Serializable {
    private int cartId;

    private int productVariantId;

    private int quantity;
}
