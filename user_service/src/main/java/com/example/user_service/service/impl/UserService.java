package com.example.user_service.service.impl;

import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.service.UserServiceI;

public class UserService implements UserServiceI {
    @Override
    public UserOutDTO createUser(UserInDTO userInDTO) {
        return null;
    }

    @Override
    public UserOutDTO update(UserInDTO userInDTO, Long id) {
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
