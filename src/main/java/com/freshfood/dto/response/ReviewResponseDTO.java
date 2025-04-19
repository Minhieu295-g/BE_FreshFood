package com.freshfood.dto.response;

import com.freshfood.model.ReviewImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;

@Getter
@Builder
@Setter
public class ReviewResponseDTO {
    private int id;
    private int userId;
    private int productId;
    private String username;
    private String fullName;
    private int rating;
    private String comment;
    private Date date;
    private ReviewReplyResponseDTO reply;
    private HashSet<ReviewImageResponseDTO> images;
}
