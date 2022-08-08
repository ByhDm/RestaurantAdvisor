package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.mapper.RestaurantMapper;
import com.example.restaurantadvisor.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @GetMapping("/all")
    public List<RestaurantOutDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return restaurantMapper.restaurantsToRestaurantsOutDTO(restaurants);
    }

    @PostMapping("/add")
    public void addRestaurant(@RequestBody @Valid Restaurant restaurant) {
        restaurantService.addRestaurant(restaurant);
    }

    @GetMapping("/name/{name}")
    public RestaurantOutDTO getRestaurantByName(@PathVariable String name) {
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant);
    }

    @PutMapping("/update/{name}/{description}")
    public void updateDescriptionRestaurantByName(@PathVariable String name, @PathVariable String description) {
        restaurantService.updateDescriptionRestaurantByName(name, description);
    }

    @GetMapping("/description/{name}")
    public String getDescriptionRestaurantByName(@PathVariable String name) {
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant).getDescription();
    }

    @GetMapping("/id/{id}")
    public RestaurantOutDTO getRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        System.out.println("Stop");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}