package com.freshfood.dto.request;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminRequestDTO implements Serializable {
    private String username;
    private String fullName;
    private String numberPhone;
    private String email;
    private String password;
    private String provider;
    private String providerId;
    private String role;
}
