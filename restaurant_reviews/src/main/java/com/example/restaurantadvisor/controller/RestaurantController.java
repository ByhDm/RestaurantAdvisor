package com.example.restaurantadvisor.controller;

import com.example.restaurantadvisor.dto.in.*;
import com.example.restaurantadvisor.dto.out.RestaurantOutDTO;
import com.example.restaurantadvisor.dto.out.RestaurantSmallOutDTO;
import com.example.restaurantadvisor.entity.Restaurant;
import com.example.restaurantadvisor.exception.OwnerNotFoundException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.mapper.RestaurantMapper;
import com.example.restaurantadvisor.service.RestaurantService;
import com.google.i18n.phonenumbers.NumberParseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/all")
    public Page<RestaurantOutDTO> getAllRestaurants(@PageableDefault(sort = "name") Pageable pageable) {
        Page<Restaurant> restaurants = restaurantService.getAllRestaurants(pageable);
        return restaurants.map(restaurantMapper::restaurantToRestaurantOutDTO);
    }

    @Operation(summary = "Create restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @PostMapping
    public RestaurantInDTO addRestaurant(@RequestBody @Valid RestaurantInDTO restaurantInDTO) throws NumberParseException {
        restaurantService.addRestaurant(restaurantMapper.restaurantInDTOToRestaurant(restaurantInDTO));
        return restaurantInDTO;
    }

    @Operation(summary = "Get restaurant by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/name/{name}")
    public RestaurantOutDTO getRestaurantByName(@PathVariable String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant);
    }

    @Operation(summary = "Update description restaurant by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @PutMapping
    public void updateDescriptionRestaurantByName(@RequestParam String name, @RequestParam String description) throws RestaurantNotFoundException {
        restaurantService.updateDescriptionRestaurantByName(name, description);
    }

    @Operation(summary = "Get description restaurant by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/description/{name}")
    public String getDescriptionRestaurantByName(@PathVariable String name) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        return restaurant.getDescription();
    }

    @Operation(summary = "Get restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/{id}")
    public RestaurantOutDTO getRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurantMapper.restaurantToRestaurantOutDTO(restaurant);
    }

    @Operation(summary = "Get reviews for restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/{name}/reviews")
    public Page<String> getReviewsRestaurantByName(@PathVariable String name, Pageable pageable){
        List<String> reviewsRestaurantById = restaurantService.getReviewsRestaurantByName(name);
        return new PageImpl<>(reviewsRestaurantById, pageable, reviewsRestaurantById.size());
    }

    @Operation(summary = "Get rating for restaurant by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/rating/{name}")
    public Double getRatingRestaurantByName(@PathVariable String name) {
        return restaurantService.getRatingRestaurantByName(name);
    }

    @Operation(summary = "Get small List restaurants")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ok"),
            @ApiResponse(
                    responseCode = "404",
                    description = "false"
            )
    })
    @GetMapping("/smallList")
    public List<RestaurantSmallOutDTO> getSmallListRestaurants() {
        return restaurantService.getSmallList();
    }

    @Operation(summary = "Delete restaurant by id")
    @DeleteMapping("/{id}")
    public Long deleteRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException{
        return restaurantService.deleteRestaurantById(id);
    }

    @Operation(summary = "Update restaurant by id")
    @PutMapping("/{id}")
    void updateRestaurantById(@PathVariable Long id, @RequestBody @Valid UpdateRestaurantInDTO updateRestaurantInDTO) throws RestaurantNotFoundException, OwnerNotFoundException {
        restaurantService.updateRestaurantById(id, updateRestaurantInDTO);
    }

    @Operation(summary = "Delete owner")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("/owner")
    public void deleteOwner(@RequestBody DeleteOwnerInDTO deleteOwnerInDTO) throws RestaurantNotFoundException {
        restaurantService.deleteOwner(deleteOwnerInDTO);
    }

    @Operation(summary = "Add owner")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PostMapping("/owner")
    public void addOwner(@RequestBody AddOwnerInDTO addOwnerInDTO) throws RestaurantNotFoundException {
        restaurantService.addOwner(addOwnerInDTO);
    }

    @Operation(summary = "Change owner")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PostMapping("/owner/change")
    public void changeOwner(@RequestBody ChangeOwnerInDTO changeOwnerInDTO) throws RestaurantNotFoundException, OwnerNotFoundException {
        restaurantService.changeOwner(changeOwnerInDTO);
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