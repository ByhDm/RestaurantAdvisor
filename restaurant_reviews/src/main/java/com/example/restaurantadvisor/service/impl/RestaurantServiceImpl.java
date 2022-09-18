package com.example.restaurantadvisor.service.impl;

import com.example.restaurantadvisor.dto.in.AddOwnerInDTO;
import com.example.restaurantadvisor.dto.in.ChangeOwnerInDTO;
import com.example.restaurantadvisor.dto.in.DeleteOwnerInDTO;
import com.example.restaurantadvisor.dto.out.RestaurantSmallOutDTO;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.IncorrectEmailAddressException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.mapper.RestaurantMapper;
import com.example.restaurantadvisor.repository.RestaurantRepository;
import com.example.restaurantadvisor.repository.ReviewRepository;
import com.example.restaurantadvisor.repository.data.RestaurantSmall;
import com.example.restaurantadvisor.service.RestaurantService;
import com.example.restaurantadvisor.util.EmailUtil;
import com.example.restaurantadvisor.util.PhoneUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    private final RestaurantMapper restaurantMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
        this.restaurantMapper = restaurantMapper;
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
            throw new RestaurantNotFoundException(restaurantRepository.findFirstByName(name).getId());
        } else {
            return restaurant;
        }
    }

    @Override
    public void updateDescriptionRestaurantByName(String name, String description) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(restaurantRepository.findFirstByName(name).getId());
        } else {
            restaurant.setDescription(description);
            restaurantRepository.save(restaurant); //     @Transactional
        }
    }

    @Override
    public String getDescriptionRestaurantByName(String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(restaurantRepository.findFirstByName(name).getId());
        } else {
            return restaurant.getDescription();
        }
    }

    @Override
    public Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RestaurantNotFoundException(id);
        } else {
            return byId.get();
        }
    }

    @Override
    public void addPhoneByRestaurantName(String name, String phone) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findFirstByName(name);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(restaurantRepository.findFirstByName(name).getId());
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
            throw new RestaurantNotFoundException(restaurantRepository.findFirstByName(name).getId());
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
            throw new RestaurantNotFoundException(id);
        } else {
            Restaurant restaurantById = byId.get();
            return restaurantById.getDate();
        }
    }

    @Override
    @Transactional
    public List<String> getReviewsRestaurantByName(String name) {
        return reviewRepository.getReviewByName(name);
    }

    @Override
    public Double getRatingRestaurantByName(String name) {

        return reviewRepository.getRatingByName(name);
    }

    @Override
    public List<RestaurantSmallOutDTO> getSmallList() {
        List<RestaurantSmall> smallRestaurants = restaurantRepository.findSmallRestaurants();
        return restaurantMapper.restaurantsToRestaurantSmallOutDTO(smallRestaurants);
    }

    @Override
    @Transactional
    public void deleteOwner(DeleteOwnerInDTO deleteOwnerInDTO) throws RestaurantNotFoundException {
        Long idBoss = deleteOwnerInDTO.getIdBoss();
        List<Restaurant> restaurants = getRestaurantByOwnerId(idBoss);
        for (Restaurant restaurant : restaurants) {
            Long id = restaurant.getId();
            Optional<Restaurant> byId = restaurantRepository.findById(id);
            if (byId.isEmpty()) {
                throw new RestaurantNotFoundException(id);
            }
            byId.get().setIdBoss(null);
        }
    }

    @Override
    public List<Restaurant> getRestaurantByOwnerId(Long idBoss) throws RestaurantNotFoundException {
        Optional<List<Restaurant>> byIdBoss = restaurantRepository.findAllByIdBoss(idBoss);
        if (byIdBoss.isEmpty()) {
            throw new RestaurantNotFoundException(idBoss);
        }
        return byIdBoss.get();
    }

    @Override
    public void addOwner(AddOwnerInDTO addOwnerInDTO) throws RestaurantNotFoundException {
        Optional<Restaurant> byId = restaurantRepository.findById(addOwnerInDTO.getRestaurantId());
        if (byId.isEmpty()) {
            throw new RestaurantNotFoundException(addOwnerInDTO.getRestaurantId());
        }
        byId.get().setIdBoss(addOwnerInDTO.getIdBoss());
    }

    @Override
    public void changeOwner(ChangeOwnerInDTO changeOwnerInDTO) throws RestaurantNotFoundException {
        Long idBoss = changeOwnerInDTO.getOldIdBoss();
        List<Restaurant> restaurants = getRestaurantByOwnerId(idBoss);
        for (Restaurant restaurant : restaurants) {
            Long id = restaurant.getId();
            Optional<Restaurant> byId = restaurantRepository.findById(id);
            if (byId.isEmpty()) {
                throw new RestaurantNotFoundException(id);
            }
            byId.get().setIdBoss(changeOwnerInDTO.getNewIdBoss());
        }
    }
}