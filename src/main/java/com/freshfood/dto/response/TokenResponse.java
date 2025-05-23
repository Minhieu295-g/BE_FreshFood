package com.freshfood.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class TokenResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private String username;
    private String fullName;
    private Integer cartId;
    private String role;

}
