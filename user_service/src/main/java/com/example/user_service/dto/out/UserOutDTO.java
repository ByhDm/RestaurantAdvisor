package com.example.user_service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class UserOutDTO {

    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private String email;

    @EqualsAndHashCode.Exclude
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDateTime registrationDate;

}
