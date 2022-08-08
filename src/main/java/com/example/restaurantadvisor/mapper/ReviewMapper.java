package com.example.restaurantadvisor.mapper;

import com.example.restaurantadvisor.dto.out.ReviewOutDTO;
import com.example.restaurantadvisor.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewOutDTO reviewToReviewOutDTO(Review review);
    Map<String, List<String>> reviewsToReviewsOutDTO(Map<String, List<String>> reviews);

}