package com.freshfood.dto.request;

import com.freshfood.dto.validator.Email;
import com.freshfood.dto.validator.Password;
import com.freshfood.dto.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;

@Getter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "username must be not blank")
    @Length(min = 8, message = "username must be at least 8 character long")
    private String username;

    @NotBlank(message = "password must be not blank")
    @Password
    private String password;

    @NotBlank(message = "full-name must be not blank")
    @Password
    private String fullName;

    @Email
    private String email;

    @PhoneNumber
    private String phone;

}
