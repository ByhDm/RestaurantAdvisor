package com.example.restaurantadvisor.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Review {

    private Long id;
    private Long restaurant_id;
    private String review;
    private Integer rating;

}