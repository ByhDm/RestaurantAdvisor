package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.RestaurantAdvisorApplicationTests;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.entity.Review;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.IncorrectEmailAddressException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantServiceTest extends RestaurantAdvisorApplicationTests {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReviewService reviewService;

    @BeforeAll
    void addRestaurantsAndReviewsInDataBase() throws NumberParseException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Astoria");
        restaurant.setEmail("astoria@astoria.com");
        restaurant.setDescription("Test description 1");
        restaurant.setDate(LocalDate.of(2015, 1,13));
        restaurantService.addRestaurant(restaurant);
        Review review = new Review();
        review.setReview("Good restaurant");
        review.setRestaurant_id(restaurantService.getAllRestaurants(Pageable.unpaged()).toList().get(0));
        review.setRating(4);
        reviewService.addReview(review);
    }

    @BeforeEach
    void setDefaultParameters() throws RestaurantNotFoundException {
        restaurantService.addPhoneByRestaurantName("Astoria", "+79990000000");
    }

    @Test
    void getAll() {
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants(Pageable.unpaged()).toList();
        assertNotNull(allRestaurants);
        assertEquals("Astoria", allRestaurants.get(0).getName());
        assertEquals("+79990000000", allRestaurants.get(0).getPhoneNumber());
        assertEquals("astoria@astoria.com", allRestaurants.get(0).getEmail());
        assertEquals("Test description 1", allRestaurants.get(0).getDescription());
    }

    @Test
    void getRestaurantByName() throws RestaurantNotFoundException {
        String name = "Astoria";
        Restaurant restaurantByName = restaurantService.getRestaurantByName(name);
        assertEquals(name, restaurantByName.getName());
    }

    @Test
    void updateDescriptionRestaurantByName() throws RestaurantNotFoundException {
        String newDescription = "Test description 1";
        restaurantService.updateDescriptionRestaurantByName("Astoria", newDescription);
        assertEquals(newDescription, restaurantService.getDescriptionRestaurantByName("Astoria"));
    }

    @Test
    void getDescriptionRestaurantByName() throws RestaurantNotFoundException {
        String name = "Astoria";
        String description = "Test description 1";
        String descriptionRestaurantByName = restaurantService.getDescriptionRestaurantByName(name);
        assertEquals(description, descriptionRestaurantByName);
    }

    @Test
    void getRestaurantById() throws RestaurantNotFoundException {
        Long id = 1L;
        Restaurant restaurantById = restaurantService.getRestaurantById(id);
        assertEquals(id, restaurantById.getId());
    }

    @Test
    void addPhoneByRestaurantName() throws RestaurantNotFoundException {
        String phone = "+79990000000";
        restaurantService.addPhoneByRestaurantName("Astoria", phone);
        assertEquals(phone, restaurantService.getRestaurantByName("Astoria").getPhoneNumber());
    }

    @Test
    void addEmailAddressByName() throws FoundationDateIsExpiredException, RestaurantNotFoundException, IncorrectEmailAddressException {
        String email = "astoria@astoria.com";
        restaurantService.addEmailAddressByName("Astoria", email);
        assertEquals(email, restaurantService.getRestaurantByName("Astoria").getEmail());
    }

    @Test
    void addRestaurantByNameAndCreationDate() throws FoundationDateIsExpiredException, NumberParseException, RestaurantNotFoundException {
        Assertions.assertThrowsExactly(FoundationDateIsExpiredException.class,
                () -> restaurantService.addRestaurantByNameAndCreationDate("Astoria", LocalDate.now().plusDays(5)));

        LocalDate creationDate = LocalDate.now().minusDays(5);
        Restaurant astoria = restaurantService.addRestaurantByNameAndCreationDate("Astoria", creationDate);
        assertTrue(creationDate.isEqual(restaurantService.getCreationDateByRestaurantId(astoria.getId())));
    }
}