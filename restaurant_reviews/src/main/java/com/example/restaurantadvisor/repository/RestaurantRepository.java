package com.example.restaurantadvisor.repository;

import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.repository.data.RestaurantSmall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findFirstByName(String name);
    @Query(value = "select res.id, MAX(res.name) AS name, AVG(rev.rating) AS average " +
            "from restaurants AS res " +
            "join reviews AS rev on (res.id=rev.restaurant_id) " +
            "group by res.id", nativeQuery = true)
    List<RestaurantSmall> findSmallRestaurants();
}