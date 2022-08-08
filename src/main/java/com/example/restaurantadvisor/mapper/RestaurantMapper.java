package com.example.restaurantadvisor.mapper;

import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.entity.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantOutDTO restaurantToRestaurantOutDTO(Restaurant restaurant);
    List<RestaurantOutDTO> restaurantsToRestaurantsOutDTO(List<Restaurant> restaurants);
}