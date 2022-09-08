package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.ReviewInDTO;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.mapper.ReviewMapper;
import com.example.restaurantadvisor.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping
    public Long addReview(@RequestBody @Valid ReviewInDTO reviewInDTO) throws RestaurantNotFoundException {
        reviewService.addReview(reviewInDTO.getRestaurant_id(), reviewInDTO.getReview(), reviewInDTO.getRating());
        return reviewInDTO.getRestaurant_id();
    }

    @PutMapping("/{id}")
    public void updateReviewById(@PathVariable Long id, @RequestParam String review) {
        reviewService.updateReviewById(id, review);
    }

    @DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }
}