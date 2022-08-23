package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.service.RestaurantServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import javax.transaction.Transactional;
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

//    @Transactional
    @Test
    void getAllRestaurants() throws Exception {
        String expected = objectMapper.writeValueAsString(restaurantService.getAllRestaurants(Pageable.unpaged()));
        this.mockMvc.perform(get("/restaurant/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
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
        RestaurantOutDTO restaurant = RestaurantOutDTO.builder()
                .name("TEST")
                .phoneNumber("+79992222222")
                .email("test@test.com")
                .description("test")
                .date(LocalDate.of(2011, 1, 11))
                .build();
        String obj = objectMapper.writeValueAsString(restaurant);
        this.mockMvc.perform(post("/restaurant/add")
                        .contentType(MediaType.APPLICATION_JSON).content(obj))
                .andExpect(status().isOk());
    }

    @Test
    void updateDescription() throws Exception {
        this.mockMvc.perform(put("/restaurant/update/{name}/{description}", "Astoria", "description"))
                .andDo(print())
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
}