package com.example.user_service.controller;

import com.example.user_service.dto.in.ChangePasswordUserInDTO;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
public interface UserControllerI {

    @PostMapping
    UserOutDTO createUser(@RequestBody UserInDTO userInDTO);

    @PutMapping("/{id}")
    UserOutDTO updateUser(UserInDTO userInDTO, @PathVariable Long id) throws UserNotFoundException;

    @DeleteMapping("/{id}")
    Long deleteUser(@PathVariable Long id) throws UserNotFoundException;

    @GetMapping("/{id}")
    UserOutDTO getUser(@PathVariable Long id) throws UserNotFoundException;

    @PutMapping("/password")
    void changePassword(@RequestBody @Valid ChangePasswordUserInDTO changePasswordUserInDTO);

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
