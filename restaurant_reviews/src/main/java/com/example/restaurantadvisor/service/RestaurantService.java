package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.IncorrectEmailAddressException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {

    Page<Restaurant> getAllRestaurants(Pageable pageable);
    Restaurant addRestaurant(Restaurant restaurant) throws NumberParseException;
    Restaurant getRestaurantByName(String name) throws RestaurantNotFoundException;
    void updateDescriptionRestaurantByName(String name, String description) throws RestaurantNotFoundException;
    String getDescriptionRestaurantByName(String name) throws RestaurantNotFoundException;
    Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException;
    void addPhoneByRestaurantName(String name, String phone) throws RestaurantNotFoundException;
    void addEmailAddressByName(String name, String emailAddress) throws FoundationDateIsExpiredException, RestaurantNotFoundException, IncorrectEmailAddressException;
    Restaurant addRestaurantByNameAndCreationDate(String name, LocalDate creationDate) throws FoundationDateIsExpiredException, NumberParseException;
    LocalDate getCreationDateByRestaurantId(Long id) throws RestaurantNotFoundException;
}