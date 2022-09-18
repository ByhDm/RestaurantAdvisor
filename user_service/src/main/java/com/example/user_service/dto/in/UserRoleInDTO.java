package com.example.user_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "DTO to add role for user")
@Data
@Builder
public class UserRoleInDTO {
    private Long idUser;
    private Long idRole;
}
