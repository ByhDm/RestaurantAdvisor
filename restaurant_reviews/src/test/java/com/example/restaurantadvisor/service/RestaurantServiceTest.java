package com.example.restaurantadvisor.service;

import com.example.restaurantadvisor.RestaurantAdvisorApplicationTests;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.FoundationDateIsExpiredException;
import com.example.restaurantadvisor.exception.IncorrectEmailAddressException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    void addRestaurantsAndReviewsInDataBase() throws NumberParseException, RestaurantNotFoundException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Astoria");
        restaurant.setEmail("astoria@astoria.com");
        restaurant.setDescription("Test description 1");
        restaurant.setDate(LocalDate.of(2015, 1,13));
        restaurant.setIsDeleted(false);
        restaurantService.addRestaurant(restaurant);
        reviewService.addReview(restaurantService.getAllRestaurants(Pageable.unpaged()).toList().get(0).getId(), "Good restaurant", 1);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Asia");
        restaurant2.setEmail("asia@asia.com");
        restaurant2.setDescription("Test description 2");
        restaurant2.setDate(LocalDate.of(2015, 10,13));
        restaurant2.setIsDeleted(false);
        restaurantService.addRestaurant(restaurant2);
    }

    @BeforeEach
    void setDefaultParameters() throws RestaurantNotFoundException {
        restaurantService.addPhoneByRestaurantName("Astoria", "+79990000000");
        restaurantService.addPhoneByRestaurantName("Asia", "+79991111111");
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Order.by("name")));
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants(pageable).toList();
        assertNotNull(allRestaurants);
        assertEquals("Asia", allRestaurants.get(0).getName());
        assertEquals("+79991111111", allRestaurants.get(0).getPhoneNumber());
        assertEquals("asia@asia.com", allRestaurants.get(0).getEmail());
        assertEquals("Test description 2", allRestaurants.get(0).getDescription());
        assertEquals(LocalDate.of(2015, 10,13), allRestaurants.get(0).getDate());
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
        String name = "Asia";
        String description = "Test description 2";
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