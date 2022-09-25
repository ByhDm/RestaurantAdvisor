package com.example.restaurantadvisor.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantOutDTO {

    @Schema(description = "restaurant id")
    private Long id;

    @Schema(description = "name")
    private String name;

    @Schema(description = "phone number")
    private String phoneNumber;

    @Schema(description = "email address")
    private String email;

    @Schema(description = "description text")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;
}