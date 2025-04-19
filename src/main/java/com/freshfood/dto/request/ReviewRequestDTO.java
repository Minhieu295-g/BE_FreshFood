package com.freshfood.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReviewRequestDTO implements Serializable {
    private int userId;
    private int productId;
    private int rating;
    private String comment;
}
