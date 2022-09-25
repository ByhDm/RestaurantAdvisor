package com.example.restaurantadvisor.dto.in;

import com.example.restaurantadvisor.validation.constraint.ValidPhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRestaurantInDTO {

    @Schema(description = "Name")
    @NotBlank(message = "Empty name")
    private String name;

    @Schema(description = "Owner id")
    private Long idBoss;

    @Schema(description = "Email address")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Phone number")
    @ValidPhoneNumber(message = "Invalid format phone number")
    private String phoneNumber;

    @Schema(description = "Description text")
    @NotBlank(message = "Empty description")
    private String description;
}
