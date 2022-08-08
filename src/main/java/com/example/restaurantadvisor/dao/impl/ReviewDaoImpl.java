package com.example.restaurantadvisor.dao.impl;

import com.example.restaurantadvisor.dao.ReviewDao;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.restaurantadvisor.dao.ConnectionToDataBase.connection;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Override
    public Map<String, List<String>> getReviewsRestaurantById(Long id) {
        String getQuery = "SELECT restaurants.name, reviews.review FROM restaurants, reviews WHERE restaurants.id = ? AND reviews.restaurant_id =?";
        Restaurant restaurant = new Restaurant();
        Review review = new Review();
        Map<String, List<String>> getNameRestaurantWithReviews = new HashMap<>();
        List<String> reviews = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurant.setName(resultSet.getString(1));
                review.setReview(resultSet.getString(2));
                reviews.add(review.getReview());
            }
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or id '{}'"
                    , getQuery
                    , id
                    , e);
        }
        getNameRestaurantWithReviews.put(restaurant.getName(), reviews);
        return getNameRestaurantWithReviews;
    }

    @Override
    public Map<String, Integer> getRatingRestaurantById(Long id) {
        String getQuery = "SELECT restaurants.name, reviews.rating FROM restaurants, reviews WHERE restaurants.id = ? AND reviews.restaurant_id =?";
        Restaurant restaurant = new Restaurant();
        Review review = new Review();
        Map<String, Integer> getNameRestaurantWithRating = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurant.setName(resultSet.getString(1));
                review.setRating(resultSet.getInt(2));
                String nameRestaurant = restaurant.getName();
                Integer ratingRestaurant = review.getRating();
                getNameRestaurantWithRating.put(nameRestaurant, ratingRestaurant);
            }
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or id '{}'"
                    , getQuery
                    , id
                    , e);
        }
        return getNameRestaurantWithRating;
    }

    @Override
    public void addReview(Review review) {
        String getQuery = "INSERT INTO reviews (restaurant_id, review, rating) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setLong(1, review.getRestaurant_id());
            preparedStatement.setString(2, review.getReview());
            preparedStatement.setInt(3, review.getRating());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' " +
                            "or fields object review - restaurant_id '{}' or review '{}' or rating '{}'"
                    , getQuery
                    , review.getRestaurant_id()
                    , review.getReview()
                    , review.getRating()
                    , e);
        }
    }

    @Override
    public void updateReviewById(Long restaurant_id, String review) {
        String getQuery = "UPDATE reviews SET review = ? WHERE restaurant_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, review);
            preparedStatement.setLong(2, restaurant_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or restaurant_id '{}' or review '{}'"
                    , getQuery
                    , restaurant_id
                    , review
                    , e);
        }
    }
}