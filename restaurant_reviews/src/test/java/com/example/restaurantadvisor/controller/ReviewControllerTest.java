package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.ReviewInDTO;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.service.RestaurantServiceTest;
import com.example.restaurantadvisor.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReviewControllerTest extends RestaurantServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;


//    @Test
//    void getReviewsRestaurantByName() throws Exception {
//        this.mockMvc.perform(get("/review/{name}", "Astoria"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

//    @Test
//    void getRatingRestaurantByName() throws Exception {
//       Double ratingRestaurantById = reviewService.getRatingRestaurantByName("Astoria");
//        this.mockMvc.perform(get("/review/rating/{name}", "Astoria"))
//                .andDo(print())
//                .andExpect(content().string(Double.toString(ratingRestaurantById)));
//    }

    @Test
    void addReview() throws Exception {
        ReviewInDTO review = ReviewInDTO.builder()
                .restaurant_id(restaurantService.getRestaurantByName("Astoria").getId())
                .review("Test review 1")
                .rating(1)
                .build();
        String obj = objectMapper.writeValueAsString(review);
        this.mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON).content(obj))
                .andExpect(status().isOk());
    }
}