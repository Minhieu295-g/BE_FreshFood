package com.freshfood.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String fullName;
    private String numberPhone;
    private String email;
    private String provider;
    private String providerId;
    private String role;
}
