package com.example.user_service.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO to display user rights")
@Data
public class UserRoleOutDTO {
    private Long id;
    private Long idUser;
    private Long idRole;
}