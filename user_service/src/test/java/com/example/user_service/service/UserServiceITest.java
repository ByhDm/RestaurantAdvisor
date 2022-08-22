package com.example.user_service.service;

import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.service.impl.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceITest {

    private UserService userService;

    @Test
    void createUser() {
        UserInDTO userInDTO = new UserInDTO();
        UserOutDTO user = userService.createUser(userInDTO);
        assertEquals(user, userService.getUser(user.getId()));
    }

    @Test
    void update() {
        UserInDTO userInDTO = new UserInDTO();
        UserOutDTO user = userService.createUser(userInDTO);
        UserOutDTO userOutDTO = userService.update(userInDTO, user.getId());
        assertEquals(user, userOutDTO);
    }

    @Test
    void deleteUser() {
        UserInDTO userInDTO = new UserInDTO();
        UserOutDTO user = userService.createUser(userInDTO);
        Long userId = userService.deleteUser(user.getId());
        assertEquals(userId, user.getId());
    }
}