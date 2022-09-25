package com.example.user_service.controller;

import com.example.user_service.dto.in.ChangeOwnerInDTO;
import com.example.user_service.dto.in.ChangePasswordUserInDTO;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
public interface UserControllerI {
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User is create"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not create"
            )
    })
    @PostMapping
    UserOutDTO createUser(@RequestBody UserInDTO userInDTO);

    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User is update"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not update"
            )
    })
    @PutMapping("/{id}")
    UserOutDTO updateUser(@RequestBody UserInDTO userInDTO, @PathVariable Long id) throws UserNotFoundException;

    @Operation(summary = "Change owner to RestaurantDB")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @PutMapping("owner")
    void changeOwner(@RequestBody ChangeOwnerInDTO changeOwnerInDTO);

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User is delete"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("/{id}")
    Long deleteUser(@PathVariable Long id) throws UserNotFoundException;

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/{id}")
    UserOutDTO getUser(@PathVariable Long id) throws UserNotFoundException;

    @Operation(summary = "Change password by user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "password is update"),
            @ApiResponse(
                    responseCode = "404",
                    description = "password is not valid"
            )
    })
    @PutMapping("/password")
    void changePassword(@RequestBody @Valid ChangePasswordUserInDTO changePasswordUserInDTO) throws UserNotFoundException;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default Map<String, String> handleValidationExceptions(
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
