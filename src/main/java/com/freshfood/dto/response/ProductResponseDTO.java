package com.freshfood.dto.response;

import com.freshfood.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private int id;

    private String name;

    private String description;

    private String thumbnailUrl;

    private CategoryResponseDTO category;

    private HashSet<ProductImageResponseDTO> productImages;

    private HashSet<ProductVariantResponseDTO> productVariants;
}
