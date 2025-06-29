package com.freshfood.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImportRequestDTO implements Serializable {
    private int productVariantId;
    private int userId;
    private int quantity;
}
