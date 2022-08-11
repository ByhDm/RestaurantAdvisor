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
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll(Sort.by("name"));
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) throws NumberParseException {
        String phone = restaurant.getPhoneNumber();
        // лучше оставлять поле пусьым, если не пришел телефон в ДТО
        if (phone == null || phone.equals("default")) {
            restaurant.setPhoneNumber("default");
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
        }
        return restaurant;
    }

    @Override
    public void updateDescriptionRestaurantByName(String name, String description) {
        // лучше апдейтить по id (или сделать имя уникальным)
        // апдейт сущности по не уникальному полю приведет к тому, что вы не знаете какой ресторан отредактировали
        //добавить обратку если нет ресторана по этому имени
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        restaurant.setDescription(description);
        restaurantRepository.save(restaurant); //     @Transactional
    }

    @Override
    public String getDescriptionRestaurantByName(String name) {
        //добавить обратку если нет ресторана по этому имени
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
        //добавить обратку если нет ресторана по этому имени
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        try {
            restaurant.setPhoneNumber(PhoneUtil.reformatRuTelephone(phone));
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        restaurantRepository.save(restaurant);
    }

    @Override
    public void addEmailAddressByName(String name, String emailAddress) throws IncorrectEmailAddressException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (EmailUtil.checkValid(emailAddress)) {
            restaurant.setEmail(emailAddress);
            restaurantRepository.save(restaurant);
        } else {
            throw new IncorrectEmailAddressException("write correct Email Address");
        }
    }

    @Override
    public Restaurant addRestaurantByNameAndCreationDate(String name, LocalDate creationDate) throws FoundationDateIsExpiredException, NumberParseException {
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
    public LocalDate getCreationDateByRestaurantId(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if(byId.isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurantById = byId.get();
        return restaurantById.getDate();
    }
}