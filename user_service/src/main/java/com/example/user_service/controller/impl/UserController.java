package com.example.user_service.controller.impl;

import com.example.user_service.controller.UserControllerI;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;

public class UserController implements UserControllerI {
    @Override
    public UserOutDTO createUser(UserInDTO userInDTO) {
        return null;
    }

    @Override
    public UserOutDTO updateUser(UserInDTO userInDTO, Long id) {
        return null;
    }

    @Override
    public Long deleteUser(Long id) {
        return null;
    }

    @Override
    public UserOutDTO getUser(Long id) {
        return null;
    }
}
