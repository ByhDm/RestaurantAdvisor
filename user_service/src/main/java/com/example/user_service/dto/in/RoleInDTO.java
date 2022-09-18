package com.example.user_service.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(description = "DTO to create role")
@Data
@Builder
@AllArgsConstructor
public class RoleInDTO {
    private String roleName;
}
