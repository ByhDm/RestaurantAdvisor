package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.RestaurantAdvisorApplicationTests;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;


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
        assertNotNull(restaurantService.getAllRestaurants());
        assertEquals("Astoria", restaurantService.getAllRestaurants().get(0).getName());
        assertEquals("+79998888888", restaurantService.getAllRestaurants().get(0).getPhoneNumber());
        assertEquals("astoria@astoria.com", restaurantService.getAllRestaurants().get(0).getEmail());
        assertEquals("Test description 1", restaurantService.getAllRestaurants().get(0).getDescription());
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
        MockedStatic<LocalDate> localDateMockedStatic = mockStatic(LocalDate.class, CALLS_REAL_METHODS);
        LocalDate defaultDateNow = LocalDate.of(2015, 4, 17);
        localDateMockedStatic.when(LocalDate::now).thenReturn(defaultDateNow);

        Assertions.assertThrowsExactly(FoundationDateIsExpiredException.class,
                () -> restaurantService.addRestaurantByNameAndCreationDate("Astoria", LocalDate.of(2016, 4, 17)));

        restaurantService.addRestaurantByNameAndCreationDate("Astoria", LocalDate.of(2015, 4, 17));
        assertEquals(LocalDate.of(2015, 4, 17), restaurantService.getCreationDateByRestaurantId(1L));
    }
}