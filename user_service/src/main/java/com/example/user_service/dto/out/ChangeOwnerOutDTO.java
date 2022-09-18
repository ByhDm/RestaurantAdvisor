package com.example.user_service.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChangeOwnerOutDTO {
    @Schema(description = "Old owner id")
    private Long oldId;
    @Schema(description = "New owner id")
    private Long newId;
}
