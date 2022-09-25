package com.example.restaurantadvisor.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(description = "DTO for display user")
@Data
public class UserOutDTO {

    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private String email;

    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate registrationDate;

    private String password;
}
