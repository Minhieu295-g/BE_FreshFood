package com.freshfood.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponse {
    private ProductVariantResponseDTO productVariantResponseDTO;
    private int totalImport;
    private int totalSold;
    private int stock;
}
