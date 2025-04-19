package com.freshfood.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReviewReplyRequestDTO implements Serializable {
    private int reviewId;
    private int userId;
    private String replyText;

}
