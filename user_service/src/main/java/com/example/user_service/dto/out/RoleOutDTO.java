package com.example.user_service.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for display role")
@Data
public class RoleOutDTO {
    private Long id;
    private String roleName;
}
