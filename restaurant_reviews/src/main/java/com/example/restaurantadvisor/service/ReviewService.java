package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.exception.RestaurantNotFoundException;

public interface ReviewService {
    void addReview(Long restaurantId, String text, Integer rating) throws RestaurantNotFoundException;

    void updateReviewById(Long restaurant_id, String review);

    void deleteReviewById(Long id);
}