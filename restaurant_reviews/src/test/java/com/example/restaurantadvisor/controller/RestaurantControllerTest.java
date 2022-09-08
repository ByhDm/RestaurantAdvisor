package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.RestaurantInDTO;
import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.service.RestaurantServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class RestaurantControllerTest extends RestaurantServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRestaurants() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        this.mockMvc.perform(get("/restaurant/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void descriptionByName() throws Exception {
        String expected = restaurantService.getDescriptionRestaurantByName("Astoria");
        this.mockMvc.perform(get("/restaurant/description/{name}", "Astoria"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    void addRestaurants() throws Exception {
        RestaurantInDTO restaurant = RestaurantInDTO.builder()
                .name("Astoria")
                .phoneNumber("+79990000000")
                .email("astoria@astoria.com")
                .description("Test description 1")
                .date(LocalDate.of(2015, 1, 13))
                .build();
        String obj = objectMapper.writeValueAsString(restaurant);
        this.mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON).content(obj))
                .andExpect(status().isOk());
    }

    @Test
    void updateDescription() throws Exception {
        RestaurantInDTO restaurant = RestaurantInDTO.builder()
                .name("Astoria")
                .phoneNumber("+79990000000")
                .email("astoria@astoria.com")
                .description("description")
                .date(LocalDate.of(2015, 1, 13))
                .build();
        String obj = objectMapper.writeValueAsString(restaurant);
        this.mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON).content(obj))
                .andExpect(status().isOk());
    }

    @Test
    void getRestaurantByName() throws Exception {
        RestaurantOutDTO restaurant = RestaurantOutDTO.builder()
                .id(restaurantService.getAllRestaurants(Pageable.unpaged()).toList().get(0).getId())
                .name("Astoria")
                .phoneNumber("+79990000000")
                .email("astoria@astoria.com")
                .description("Test description 1")
                .date(LocalDate.of(2015, 1, 13))
                .build();
        String expected = objectMapper.writeValueAsString(restaurant);
        this.mockMvc.perform(get("/restaurant/name/{name}", "Astoria"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }

    @Test
    void getReviewsRestaurantByName() throws Exception {
        this.mockMvc.perform(get("/restaurant/{name}/reviews", "Astoria"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getRatingRestaurantByName() throws Exception {
        Double ratingRestaurantById = restaurantService.getRatingRestaurantByName("Astoria");
        this.mockMvc.perform(get("/restaurant/rating/{name}", "Astoria"))
                .andDo(print())
                .andExpect(content().string(Double.toString(ratingRestaurantById)));
    }
}