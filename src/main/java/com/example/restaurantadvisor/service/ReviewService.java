package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.entity.Review;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;

import java.util.List;
import java.util.Map;

public interface ReviewService {
//    List<String> getReviewsRestaurantById(Long id) throws RestaurantNotFoundException;
//    Map<String, Integer> getRatingRestaurantById(Long id);
//    void addReview(Review review);
//    void updateReviewById(Long restaurant_id, String review);

    List<String> getReviewsRestaurantByName(String name);

    Double getRatingRestaurantByName(String name);

    Review addReview(Review review);

    void updateReviewById(Long restaurant_id, String review);
}