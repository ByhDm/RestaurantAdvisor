package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.ReviewInDTO;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.mapper.ReviewMapper;
import com.example.restaurantadvisor.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<String> getReviewsRestaurantByName(@PathVariable String name, Pageable pageable){
        List<String> reviewsRestaurantById = reviewService.getReviewsRestaurantByName(name);
        return new PageImpl<>(reviewsRestaurantById, pageable, reviewsRestaurantById.size());
    }

    @GetMapping("/rating/{name}")
    public Double getRatingRestaurantByName(@PathVariable String name) {
        return reviewService.getRatingRestaurantByName(name);
    }

    @PostMapping("/add")
    public Long addReview(@RequestBody @Valid ReviewInDTO reviewInDTO) throws RestaurantNotFoundException {
        reviewService.addReview(reviewInDTO.getRestaurant_id(), reviewInDTO.getReview(), reviewInDTO.getRating());
        return reviewInDTO.getRestaurant_id();
    }

    @PutMapping("/update/{restaurant_id}/{review}")
    public void updateReviewById(@PathVariable Long restaurant_id, @PathVariable String review) {
        reviewService.updateReviewById(restaurant_id, review);
    }

    @DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }
}