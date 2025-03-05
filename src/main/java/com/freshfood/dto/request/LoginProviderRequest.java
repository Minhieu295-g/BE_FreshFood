package com.freshfood.dto.request;

import lombok.Getter;

@Getter
public class LoginProviderRequest {
    private String providerId;
    private String providerName;
    private String email;
    private String fullName;
}
