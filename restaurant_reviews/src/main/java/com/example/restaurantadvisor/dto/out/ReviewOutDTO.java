package com.example.restaurantadvisor.dto.out;

import com.example.restaurantadvisor.entity.Restaurant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewOutDTO {
    private Long id;
    private Restaurant restaurant_id;
    private String review;
    private Double rating;
}