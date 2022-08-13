package com.example.restaurantadvisor.service.impl;

import com.example.restaurantadvisor.entity.Review;
import com.example.restaurantadvisor.repository.ReviewRepository;
import com.example.restaurantadvisor.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public List<String> getReviewsRestaurantByName(String name) {
        return reviewRepository.getReviewByName(name);
    }

    @Override
    public Double getRatingRestaurantByName(String name) {
        return reviewRepository.getRatingByName(name);
    }

    @Override
    public Review addReview(Review review) {

        return reviewRepository.save(review);
    }

    @Override
    public void updateReviewById(Long restaurant_id, String review) {
        Optional<Review> reviewById = reviewRepository.findById(restaurant_id);
        if (reviewById.isPresent()) {
            reviewById.get().setReview(review);
            reviewRepository.save(reviewById.get());
        }
    }
}