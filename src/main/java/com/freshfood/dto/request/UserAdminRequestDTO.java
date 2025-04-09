package com.freshfood.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserAdminRequestDTO {
    private String username;
    private String fullName;
    private String numberPhone;
    private String email;
    private String password;
    private String provider;
    private String providerId;
    private String role;
}
