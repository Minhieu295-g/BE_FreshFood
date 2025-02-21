package com.freshfood.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freshfood.util.ProductStatus;
import com.freshfood.util.Unit;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantResponseDTO {
    private int id;

    private String name;

    private double price;

    private int discountPercentage;

    private String thumbnailUrl;

    private String unit;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date expiryDate;

    private String status;

}
