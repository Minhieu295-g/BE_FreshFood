package com.freshfood.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderItemResponseDTO {
    private int id;
    private int quantity;
    private ProductVariantResponseDTO product;
}
