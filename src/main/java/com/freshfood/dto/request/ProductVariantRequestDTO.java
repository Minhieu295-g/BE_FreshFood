package com.freshfood.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freshfood.dto.validator.EnumPattern;
import com.freshfood.util.ProductStatus;
import com.freshfood.util.Unit;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequestDTO implements Serializable {
    private int productId;

    private String name;

    private double price;

    private int discountPercentage;

    @EnumPattern(name = "unit", regexp = "(?i)KG|G|L|ML|BOX|CAN|BOTTLE|PIECE|BAG|BUNDLE|PACK")
    private Unit unit;


    @NotNull(message = "Expiry date must be not null")
    @JsonFormat(pattern = "MM/dd/yyyy", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date expiryDate;


    @EnumPattern(name = "status", regexp = "(?)AVAILABLE|OUT_OF_STOCK|DISCONTINUED|PRE_ORDER|ARCHIVED")
    private ProductStatus status;
}
