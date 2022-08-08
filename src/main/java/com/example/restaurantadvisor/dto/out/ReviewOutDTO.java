package com.example.restaurantadvisor.dto.out;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewOutDTO {
    private Long id;
    private Long restaurant_id;
    private String review;
    private double rating;

}