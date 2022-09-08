package com.example.restaurantadvisor.mapper;

import com.example.restaurantadvisor.dto.in.RestaurantInDTO;
import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantOutDTO restaurantToRestaurantOutDTO(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idBoss", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Restaurant restaurantInDTOToRestaurant(RestaurantInDTO restaurantInDTO);

    @Mapping(target = "id", ignore = true)
    List<RestaurantOutDTO> restaurantsToRestaurantsOutDTO(List<Restaurant> restaurants);
}