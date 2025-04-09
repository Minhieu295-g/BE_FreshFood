package com.freshfood.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Unit {
    @JsonProperty("kg")
    KG,
    @JsonProperty("g")
    G,
    @JsonProperty("l")
    L,
    @JsonProperty("ml")
    ML,
    @JsonProperty("box")
    BOX,
    @JsonProperty("can")
    CAN,
    @JsonProperty("bottle")
    BOTTLE,
    @JsonProperty("piece")
    PIECE,
    @JsonProperty("bag")
    BAG,
    @JsonProperty("bundle")
    BUNDLE,
    @JsonProperty("pack")
    PACK;

    @JsonCreator
    public static Unit fromString(String value) {
        if (value == null) {
            return null;
        }
        value = value.trim().toUpperCase();  // Chuyển giá trị về chữ hoa để so sánh
        return Unit.valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase(); // Chuyển giá trị của enum thành chữ thường khi serializing
    }
}
