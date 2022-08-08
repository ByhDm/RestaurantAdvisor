package com.example.restaurantadvisor.dto.out;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantOutDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String description;

    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate date;
}