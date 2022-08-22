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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {

        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) throws NumberParseException {
        String phone = restaurant.getPhoneNumber();
        if (phone == null || phone.equals("")) {
            restaurant.setPhoneNumber("");
        } else {
            restaurant.setPhoneNumber(PhoneUtil.reformatRuTelephone(phone));
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantByName(String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        } else {
            return restaurant;
        }
    }

    @Override
    public void updateDescriptionRestaurantByName(String name, String description) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        } else {
            restaurant.setDescription(description);
            restaurantRepository.save(restaurant); //     @Transactional
        }
    }

    @Override
    public String getDescriptionRestaurantByName(String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        } else {
            return restaurant.getDescription();
        }
    }

    @Override
    public Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RestaurantNotFoundException();
        } else {
            return byId.get();
        }
    }

    @Override
    public void addPhoneByRestaurantName(String name, String phone) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        } else {
            try {
                restaurant.setPhoneNumber(PhoneUtil.reformatRuTelephone(phone));
            } catch (NumberParseException e) {
                e.printStackTrace();
            }
            restaurantRepository.save(restaurant);
        }
    }

    @Override
    public void addEmailAddressByName(String name, String emailAddress) throws RestaurantNotFoundException, IncorrectEmailAddressException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        } else {
            if (EmailUtil.checkValid(emailAddress)) {
                restaurant.setEmail(emailAddress);
                restaurantRepository.save(restaurant);
            } else {
                throw new IncorrectEmailAddressException("write correct Email Address");
            }
        }
    }

    @Override
    public Restaurant addRestaurantByNameAndCreationDate(String name, LocalDate creationDate) throws FoundationDateIsExpiredException, NumberParseException {
        LocalDate dateNow = LocalDate.now();
        if (creationDate == null || dateNow.isBefore(creationDate)) {
            throw new FoundationDateIsExpiredException(name, creationDate);
        } else {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(name);
            restaurant.setDate(creationDate);
            return addRestaurant(restaurant);
        }
    }

    @Override
    public LocalDate getCreationDateByRestaurantId(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RestaurantNotFoundException();
        } else {
            Restaurant restaurantById = byId.get();
            return restaurantById.getDate();
        }
    }
}