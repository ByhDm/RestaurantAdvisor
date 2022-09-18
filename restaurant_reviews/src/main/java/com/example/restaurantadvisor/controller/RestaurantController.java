package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.RestaurantInDTO;
import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.dto.out.RestaurantSmallOutDTO;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.mapper.RestaurantMapper;
import com.example.restaurantadvisor.service.RestaurantService;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private final RestaurantService restaurantService;

    private final RestaurantMapper restaurantMapper;

    public RestaurantController(RestaurantService restaurantService, RestaurantMapper restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    @GetMapping("/all")
    public Page<RestaurantOutDTO> getAllRestaurants(@PageableDefault(sort = "name") Pageable pageable) {
        Page<Restaurant> restaurants = restaurantService.getAllRestaurants(pageable);
        return restaurants.map(restaurantMapper::restaurantToRestaurantOutDTO);
    }

    @PostMapping
    public RestaurantInDTO addRestaurant(@RequestBody @Valid RestaurantInDTO restaurantInDTO) throws NumberParseException {
        restaurantService.addRestaurant(restaurantMapper.restaurantInDTOToRestaurant(restaurantInDTO));
        return restaurantInDTO;
    }

    @GetMapping("/name/{name}")
    public RestaurantOutDTO getRestaurantByName(@PathVariable String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant);
    }

    @PutMapping
    public void updateDescriptionRestaurantByName(@RequestParam String name, @RequestParam String description) throws RestaurantNotFoundException {
        restaurantService.updateDescriptionRestaurantByName(name, description);
    }

    @GetMapping("/description/{name}")
    public String getDescriptionRestaurantByName(@PathVariable String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        return restaurant.getDescription();
    }

    @GetMapping("/{id}")
    public RestaurantOutDTO getRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant);
    }

    @GetMapping("/{name}/reviews")
    public Page<String> getReviewsRestaurantByName(@PathVariable String name, Pageable pageable){
        List<String> reviewsRestaurantById = restaurantService.getReviewsRestaurantByName(name);
        return new PageImpl<>(reviewsRestaurantById, pageable, reviewsRestaurantById.size());
    }

    @GetMapping("/rating/{name}")
    public Double getRatingRestaurantByName(@PathVariable String name) {
        return restaurantService.getRatingRestaurantByName(name);
    }

    @GetMapping("/smallList")
    public List<RestaurantSmallOutDTO> getSmallListRestaurants() {
        return restaurantService.getSmallList();
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