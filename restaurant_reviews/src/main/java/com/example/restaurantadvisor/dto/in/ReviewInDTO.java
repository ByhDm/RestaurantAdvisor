package com.example.restaurantadvisor.dto.in;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInDTO {

    @NotNull(message = "You can't create review with null reference id field")
    private Long restaurantId;

    @NotBlank(message = "Empty review")
    private String review;

    @Min(0)
    @Max(5)
    private Integer rating;
}
