package com.freshfood.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.freshfood.dto.validator.EnumPattern;
import com.freshfood.util.ProductStatus;
import com.freshfood.util.Unit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
public class ProductRequestDTO implements Serializable {
    @NotBlank(message = "name must be not blank")
    private String name;

    @NotNull(message = "description must be not null")
    private String description;

    private int categoryId;

}
