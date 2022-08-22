package com.example.restaurantadvisor.dto.in;

import com.example.restaurantadvisor.entity.Restaurant;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInDTO {

    @NotBlank(message = "You can't create review with empty reference id field")
    private Restaurant restaurant_id;

    @NotBlank(message = "Empty review")
    private String review;

    @Min(0)
    @Max(5)
    private Integer rating;
}
