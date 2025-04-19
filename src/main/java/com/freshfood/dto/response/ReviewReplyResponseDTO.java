package com.freshfood.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ReviewReplyResponseDTO {
    private int id;
    private int userId;
    private String username;
    private String fullName;
    private String reply;
    private Date date;
}
