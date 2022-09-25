package com.example.user_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Schema(description = "DTO to update password")
@Data
public class ChangePasswordUserInDTO {

    @Schema(description = "old password")
    @NotBlank(message = "password is empty")
    private String oldPassword;

    @Schema (description = "new password")
    @NotBlank(message = "password is empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[%#&*@]).{5,}$", message = "password mismatch")
    @Size(min = 5)
    private String newPassword;

    @Email(message = "not valid email")
    private String email;
}
