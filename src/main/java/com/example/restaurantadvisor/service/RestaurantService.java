package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAllRestaurants();
    Restaurant addRestaurant(Restaurant restaurant);
    Restaurant getRestaurantByName(String name);
    void updateDescriptionRestaurantByName(String name, String description);
    String getDescriptionRestaurantByName(String name);
    Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException;
    void addPhoneByRestaurantName(String name, String phone);
    void addEmailAddressByName(String name, String emailAddress) throws FoundationDateIsExpiredException;
    Restaurant addRestaurantByNameAndCreationDate(String name, LocalDate creationDate) throws FoundationDateIsExpiredException;
    LocalDate getCreationDateByRestaurantId(Long id);
}