package com.example.restaurantadvisor.dto.out;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantSmallOutDTO {
    private Long id;
    private String name;
    private BigDecimal average;
}
