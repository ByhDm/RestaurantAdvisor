package com.example.user_service.service;

import com.example.user_service.dto.in.ChangePasswordUserInDTO;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface UserServiceI {

    UserOutDTO createUser(UserInDTO userInDTO);
    UserOutDTO update(UserInDTO userInDTO, Long id) throws UserNotFoundException;
    Long deleteUser(Long id) throws UserNotFoundException;
    UserOutDTO getUser(Long id) throws UserNotFoundException;

    void changePassword(ChangePasswordUserInDTO changePasswordUserInDTO);
}
