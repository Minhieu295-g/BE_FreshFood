package com.freshfood.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemReponseDTO implements Serializable {
    private int id;
    private int quantity;
    private ProductVariantResponseDTO productVariant;
}
