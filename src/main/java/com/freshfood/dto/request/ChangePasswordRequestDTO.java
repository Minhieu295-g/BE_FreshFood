package com.freshfood.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO implements Serializable {

    private String currentPassword;
    private String newPassword;

}
