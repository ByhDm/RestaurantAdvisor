package com.example.restaurantadvisor.repository;

import com.example.restaurantadvisor.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT review FROM reviews " +
            "JOIN restaurants AS re WHERE re.name = :name", nativeQuery = true)
    List<String> getReviewByName(String name);

    @Query(value = "SELECT avg(rating) from reviews " +
            "join restaurants as re where re.name = :name", nativeQuery = true)
    Double getRatingByName(String name);
}
