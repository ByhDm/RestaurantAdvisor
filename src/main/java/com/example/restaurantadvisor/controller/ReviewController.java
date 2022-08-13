package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.entity.Review;
import com.example.restaurantadvisor.mapper.ReviewMapper;
import com.example.restaurantadvisor.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/{name}")
    public List<String> getReviewsRestaurantByName(@PathVariable String name){
        List<String> reviewsRestaurantById = reviewService.getReviewsRestaurantByName(name);
        return reviewMapper.reviewsToReviewsOutDTO(reviewsRestaurantById);
    }

    @GetMapping("/rating/{name}")
    Double getRatingRestaurantByName(@PathVariable String name) {
        return reviewService.getRatingRestaurantByName(name);
    }

    @PostMapping("/add")
    public void addReview(@RequestBody @Valid Review review) {
        reviewService.addReview(review);
    }

    @PutMapping("/update/{restaurant_id}/{review}")
    void updateReviewById(@PathVariable Long restaurant_id, @PathVariable String review) {
        reviewService.updateReviewById(restaurant_id, review);
    }
}