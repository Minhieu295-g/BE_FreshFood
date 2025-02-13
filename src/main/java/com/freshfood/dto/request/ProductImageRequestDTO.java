package com.freshfood.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ProductImageRequestDTO implements Serializable {
    private String imageUrl;
    private String altText;
    private long productId;
}
