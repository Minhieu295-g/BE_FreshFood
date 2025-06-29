package com.freshfood.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInforRequestDTO implements Serializable {
    private String fullName;
    private String numberPhone;
    private String email;

}
