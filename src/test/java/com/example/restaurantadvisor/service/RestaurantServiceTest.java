package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.RestaurantAdvisorApplicationTests;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantServiceTest extends RestaurantAdvisorApplicationTests {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReviewService reviewService;

/*    @BeforeAll
    void addRestaurantsAndReviewsInDataBase() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("TestRestaurant");
        restaurant.setDescription("Test description new");
        restaurantService.addRestaurant(restaurant);
        Review review = new Review();
        review.setReview("Good restaurant");
        review.setRestaurant_id(restaurantService.getAllRestaurants().get(0).getId());
        review.setRating(4);
        reviewService.addReview(review);
    }*/

/*    @BeforeEach
    void setDefaultParameters() {
        restaurantService.addPhoneByRestaurantName("TestRestaurant", "+79990000000");
    }*/

    @Test
    void getAll() {
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
        assertNotNull(allRestaurants);
        assertEquals("Astoria", allRestaurants.get(0).getName());
        assertEquals("+79998888888", allRestaurants.get(0).getPhoneNumber());
        assertEquals("astoria@astoria.com", allRestaurants.get(0).getEmail());
        assertEquals("Test description 1", allRestaurants.get(0).getDescription());
    }

    @Test
    void getRestaurantByName() {
        String name = "Astoria";
        Restaurant restaurantByName = restaurantService.getRestaurantByName(name);
        assertEquals(name, restaurantByName.getName());
    }

    @Test
    void updateDescriptionRestaurantByName() {
        String newDescription = "Test description 3";
        restaurantService.updateDescriptionRestaurantByName("KFC", newDescription);
        assertEquals(newDescription, restaurantService.getDescriptionRestaurantByName("KFC"));
    }

    @Test
    void getDescriptionRestaurantByName() {
        String name = "Praga";
        String description = "Test description 2";
        String descriptionRestaurantByName = restaurantService.getDescriptionRestaurantByName(name);
        assertEquals(description, descriptionRestaurantByName);
    }

    @Test
    void getRestaurantById() throws RestaurantNotFoundException {
        Long id = 3L;
        Restaurant restaurantById = restaurantService.getRestaurantById(id);
        assertEquals(id, restaurantById.getId());
    }

    @Test
    void addPhoneByRestaurantName() {
        String phone = "+79998888888";
        restaurantService.addPhoneByRestaurantName("Astoria", phone);
        assertEquals(phone, restaurantService.getRestaurantByName("Astoria").getPhoneNumber());
    }

    @Test
    void addEmailAddressByName() throws FoundationDateIsExpiredException {
        String email = "astoria@astoria.com";
        restaurantService.addEmailAddressByName("Astoria", email);
        assertEquals(email, restaurantService.getRestaurantByName("Astoria").getEmail());
    }

    @Test
    void addRestaurantByNameAndCreationDate() throws FoundationDateIsExpiredException {
        Assertions.assertThrowsExactly(FoundationDateIsExpiredException.class,
                () -> restaurantService.addRestaurantByNameAndCreationDate("Astoria", LocalDate.now().plusDays(5)));

        LocalDate creationDate = LocalDate.now().minusDays(5);
        Restaurant astoria = restaurantService.addRestaurantByNameAndCreationDate("Astoria", creationDate);
        assertTrue(creationDate.isEqual(restaurantService.getCreationDateByRestaurantId(astoria.getId())));
    }
}