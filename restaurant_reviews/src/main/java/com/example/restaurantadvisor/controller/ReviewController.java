package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.ReviewInDTO;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Add review")
    @PostMapping
    public Long addReview(@RequestBody @Valid ReviewInDTO reviewInDTO) throws RestaurantNotFoundException {
        reviewService.addReview(reviewInDTO.getRestaurantId(), reviewInDTO.getReview(), reviewInDTO.getRating());
        return reviewInDTO.getRestaurantId();
    }

    @Operation(summary = "Update review by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "review is update"),
            @ApiResponse(
                    responseCode = "404",
                    description = "review is not found"
            )
    })
    @PutMapping("/{id}")
    public void updateReviewById(@PathVariable Long id, @RequestParam String review) {
        reviewService.updateReviewById(id, review);
    }

    @Operation(summary = "Delete review by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "review is delete"),
            @ApiResponse(
                    responseCode = "404",
                    description = "review is not found"
            )
    })
    @DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
    }
}