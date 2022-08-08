package com.example.restaurantadvisor.dao.impl;

import com.example.restaurantadvisor.dao.RestaurantDao;
import com.example.restaurantadvisor.entity.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.restaurantadvisor.dao.ConnectionToDataBase.connection;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantDaoImpl.class);

    @Override
    public List<Restaurant> getAllRestaurants() {
        String getQuery = "SELECT * FROM restaurants";
        List<Restaurant> restaurants = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getLong("id"));
                restaurant.setName(resultSet.getString(2));
                restaurant.setPhoneNumber(resultSet.getString(3));
                restaurant.setEmail(resultSet.getString(4));
                restaurant.setDescription(resultSet.getString(5));
                restaurant.setDate(resultSet.getDate(6).toLocalDate());
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}'"
                    , getQuery
                    , e);
        }
        return restaurants;
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        String getQuery = "INSERT INTO restaurants (name, phoneNumber, email, description, creationDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, restaurant.getName());
            preparedStatement.setString(2, restaurant.getPhoneNumber());
            preparedStatement.setString(3, restaurant.getEmail());
            preparedStatement.setString(4, restaurant.getDescription());
            preparedStatement.setString(5, String.valueOf(restaurant.getDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}'" +
                            " or fields object restaurant - name '{}' or phoneNumber '{}' or email '{}' or description '{}' or date '{}'"
                    , getQuery
                    , restaurant.getName()
                    , restaurant.getPhoneNumber()
                    , restaurant.getEmail()
                    , restaurant.getDescription()
                    , restaurant.getDate()
                    , e);
        }
        return restaurant;
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        String getQuery = "SELECT * FROM restaurants WHERE name = ?";
        Restaurant restaurant = new Restaurant();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurant.setId(resultSet.getLong(1));
                restaurant.setName(resultSet.getString(2));
                restaurant.setPhoneNumber(resultSet.getString(3));
                restaurant.setEmail(resultSet.getString(4));
                restaurant.setDescription(resultSet.getString(5));
                restaurant.setDate(resultSet.getDate(6).toLocalDate());
            }
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or name '{}'"
                    , getQuery
                    , name
                    , e);
        }
        return restaurant;
    }

    @Override
    public void updateDescriptionRestaurantByName(String name, String description) {
        String getQuery = "UPDATE restaurants SET description = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or name '{}' or description '{}'"
                    , getQuery
                    , name
                    , description
                    , e);
        }
    }

    @Override
    public String getDescriptionRestaurantByName(String name) {
        String getQuery = "SELECT description FROM restaurants WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("description");
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or name '{}'"
                    , getQuery
                    , name
                    , e);
        }
        return null;
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        String getQuery = "SELECT * FROM restaurants WHERE id = ?";
        Restaurant restaurant = new Restaurant();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurant.setId(resultSet.getLong(1));
                restaurant.setName(resultSet.getString(2));
                restaurant.setPhoneNumber(resultSet.getString(3));
                restaurant.setEmail(resultSet.getString(4));
                restaurant.setDescription(resultSet.getString(5));
                restaurant.setDate(resultSet.getDate(6).toLocalDate());
            }
        } catch (SQLException e) {
            logger.debug("Incorrect getQuery '{}' or id '{}'"
                    , getQuery
                    , id
                    , e);
        }
        return restaurant;
    }
}