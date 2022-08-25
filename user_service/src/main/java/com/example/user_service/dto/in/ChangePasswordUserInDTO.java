package com.example.user_service.dto.in;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordUserInDTO {

    @NotBlank(message = "email is empty")
    private String email;

    @NotBlank(message = "password is empty")
    private String oldPassword;

    @NotBlank(message = "password is empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$", message = "password mismatch")
    @Size(min = 5)
    private String newPassword;
}
