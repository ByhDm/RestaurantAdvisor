package com.example.restaurantadvisor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Restaurant not found")
public class RestaurantNotFoundException extends Exception {
    private final Long restaurantId;

    public RestaurantNotFoundException(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    public Long getRestaurantId() {
        return restaurantId;
    }
}