package com.freshfood.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    @JsonProperty("available")
    AVAILABLE,
    @JsonProperty("out_of_stock")
    OUT_OF_STOCK,
    @JsonProperty("discontinued")
    DISCONTINUED,
    @JsonProperty("pre_order")
    PRE_ORDER,
    @JsonProperty("archived")
    ARCHIVED;

    @JsonCreator
    public static ProductStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        value = value.trim().toUpperCase();  // Chuyển giá trị về chữ hoa để so sánh
        return ProductStatus.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase(); // Chuyển giá trị của enum thành chữ thường khi serializing
    }
}
