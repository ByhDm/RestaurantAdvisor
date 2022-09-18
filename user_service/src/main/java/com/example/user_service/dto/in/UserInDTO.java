package com.example.user_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "DTO to create user")
@Data
public class UserInDTO {

    @NotBlank(message = "name is empty")
    private String name;
    private String surname;
    private String lastname;
    @Email(message = "not valid email")
    private String email;
    private String password;
}
