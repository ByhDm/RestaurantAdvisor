package com.example.user_service.controller;

import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

public interface UserControllerI {

    @PostMapping("/create")
    UserOutDTO createUser(@RequestBody UserInDTO userInDTO);

    @PutMapping("/update/{id}")
    UserOutDTO updateUser(UserInDTO userInDTO, @PathVariable Long id) throws UserNotFoundException;

    @DeleteMapping("/delete/{id}")
    Long deleteUser(@PathVariable Long id) throws UserNotFoundException;

    @GetMapping("/get/{id}")
    UserOutDTO getUser(@PathVariable Long id) throws UserNotFoundException;
}
