package com.freshfood.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageResponseDTO {
    private int id;
    private String imageUrl;
    private String altText;
}
