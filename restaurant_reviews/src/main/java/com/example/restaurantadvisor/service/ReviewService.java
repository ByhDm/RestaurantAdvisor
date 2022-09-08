package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.entity.Review;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    List<String> getReviewsRestaurantByName(String name);

    Double getRatingRestaurantByName(String name);

    void addReview(Long restaurantId, String text, Integer rating) throws RestaurantNotFoundException;

    void updateReviewById(Long restaurant_id, String review);

    void deleteReviewById(Long id);
}