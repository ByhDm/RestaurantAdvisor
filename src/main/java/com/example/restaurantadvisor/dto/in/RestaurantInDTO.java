package com.example.restaurantadvisor.dto.in;

import com.example.restaurantadvisor.validation.constraint.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantInDTO {

    @NotBlank(message = "Empty name")
    private String name;

    @ValidPhoneNumber(message = "Invalid format phone number")
    private String phoneNumber;

    @Email(message = "Is not email format")
    private String email;

    @NotBlank(message = "Empty description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;
}
