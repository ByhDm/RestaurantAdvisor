package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.ReviewInDTO;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.service.RestaurantServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReviewControllerTest extends RestaurantServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addReview() throws Exception {
        ReviewInDTO review = ReviewInDTO.builder()
                .restaurantId(restaurantService.getRestaurantByName("Astoria").getId())
                .review("Test review 1")
                .rating(1)
                .build();
        String obj = objectMapper.writeValueAsString(review);
        this.mockMvc.perform(post("/review")
                        .contentType(MediaType.APPLICATION_JSON).content(obj))
                .andExpect(status().isOk());
    }
}