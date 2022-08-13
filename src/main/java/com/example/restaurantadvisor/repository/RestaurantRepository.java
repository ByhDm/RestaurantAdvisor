package com.example.restaurantadvisor.repository;

import com.example.restaurantadvisor.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findFirstByName(String name);
}