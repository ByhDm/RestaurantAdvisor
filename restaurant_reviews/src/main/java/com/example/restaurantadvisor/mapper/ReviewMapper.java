package com.example.restaurantadvisor.mapper;

import com.example.restaurantadvisor.dto.out.ReviewOutDTO;
import com.example.restaurantadvisor.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewOutDTO reviewToReviewOutDTO(Review review);

    List<String> reviewsToReviewsOutDTO(List<String> reviews);
}