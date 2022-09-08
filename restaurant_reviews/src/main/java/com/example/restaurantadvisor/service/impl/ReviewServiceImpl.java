package com.example.restaurantadvisor.service.impl;

import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.entity.Review;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.repository.RestaurantRepository;
import com.example.restaurantadvisor.repository.ReviewRepository;
import com.example.restaurantadvisor.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final RestaurantRepository restaurantRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void addReview(Long restaurantId, String text, Integer rating) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(restaurantId);
        if(byId.isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurant = byId.get();
        Review review = new Review(restaurant, text, rating);
        reviewRepository.save(review);
    }

    @Override
    public void updateReviewById(Long id, String review) {
        Optional<Review> reviewById = reviewRepository.findById(id);
        if (reviewById.isPresent()) {
            reviewById.get().setReview(review);
            reviewRepository.save(reviewById.get());
        }
    }

    @Override
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }
}