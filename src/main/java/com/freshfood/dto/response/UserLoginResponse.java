package com.freshfood.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
@Builder
@Getter
public class UserLoginResponse implements Serializable {
    private Integer userId;
    private String username;
    private String fullName;
    private Integer cartId;
}
