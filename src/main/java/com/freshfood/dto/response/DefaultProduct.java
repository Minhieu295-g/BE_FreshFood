package com.freshfood.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultProduct {
    private int id;
    private int productVariantId;
    private String name;
    private String thumbnailUrl;
    private double price;
    private Integer discountPercentage;
}
