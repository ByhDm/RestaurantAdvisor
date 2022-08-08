package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.service.RestaurantServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

    @Test
    void getAllRestaurants() throws Exception {
        ObjectMapper objectMapper = new JsonMapper();
        String expected = objectMapper.writeValueAsString(restaurantService.getAllRestaurants());
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
                .date(LocalDate.of(2011, 01, 11))
                .build();
        ObjectMapper objectMapper = new JsonMapper();
        String obj = objectMapper.writeValueAsString(restaurant);
        this.mockMvc.perform(post("/restaurant/add")
                        .contentType(MediaType.APPLICATION_JSON).content(obj))
                .andExpect(status().isOk());
    }

    @Test
    void updateDescription() throws Exception {
        this.mockMvc.perform(put("/restaurant/update/{name}/{description}", "mac", "description"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getRestaurantByName() throws Exception {
        RestaurantOutDTO restaurant = RestaurantOutDTO.builder()
                .id(restaurantService.getAllRestaurants().get(0).getId())
                .name("Astoria")
                .phoneNumber("+79998888888")
                .email("astoria@astoria.com")
                .description("Test description 1")
                .date(LocalDate.of(2015, 04, 17))
                .build();
        ObjectMapper objectMapper = new JsonMapper();
        String expected = objectMapper.writeValueAsString(restaurant);
        this.mockMvc.perform(get("/restaurant/name/{name}", "Astoria"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }
}