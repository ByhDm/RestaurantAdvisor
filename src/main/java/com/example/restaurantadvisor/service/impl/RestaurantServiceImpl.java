package com.example.restaurantadvisor.service.impl;

import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.IncorrectEmailAddressException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.repository.RestaurantRepository;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.util.EmailUtil;
import com.example.restaurantadvisor.util.PhoneUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        String phone = restaurant.getPhoneNumber();
        if (phone == null || phone.equals("default")) {
            restaurant.setPhoneNumber("default");
        } else {
            try {
                restaurant.setPhoneNumber(PhoneUtil.reformatRuTelephone(phone));
            } catch (NumberParseException e) {
                throw new RuntimeException(e);
            }
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void updateDescriptionRestaurantByName(String name, String description) {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        restaurant.setDescription(description);
        restaurantRepository.save(restaurant); //     @Transactional
    }

    @Override
    public String getDescriptionRestaurantByName(String name) {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        return restaurant.getDescription();
    }

    @Override
    public Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).get();
        if (restaurant.getId() == null) throw new RestaurantNotFoundException();
        return restaurant;
    }

    @Override
    public void addPhoneByRestaurantName(String name, String phone) {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        try {
            restaurant.setPhoneNumber(PhoneUtil.reformatRuTelephone(phone));
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        restaurantRepository.save(restaurant);
    }

    @Override
    public void addEmailAddressByName(String name, String emailAddress) {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (EmailUtil.checkValid(emailAddress)) {
            restaurant.setEmail(emailAddress);
            restaurantRepository.save(restaurant);
        } else {
            try {
                throw new IncorrectEmailAddressException("write correct Email Address");
            } catch (IncorrectEmailAddressException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Restaurant addRestaurantByNameAndCreationDate(String name, LocalDate creationDate) throws FoundationDateIsExpiredException {
        LocalDate dateNow = LocalDate.now();
        if (creationDate == null || dateNow.isBefore(creationDate)) {
            throw new FoundationDateIsExpiredException(name, creationDate);
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setDate(creationDate);
        return addRestaurant(restaurant);

    }

    @Override
    public LocalDate getCreationDateByRestaurantId(Long id) {
        Restaurant restaurantById = restaurantRepository.findById(id).get();
        return restaurantById.getDate();
    }
}