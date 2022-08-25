package com.example.user_service.controller.impl;

import com.example.user_service.controller.UserControllerI;
import com.example.user_service.dto.in.ChangePasswordUserInDTO;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.exceptions.UserNotFoundException;
import com.example.user_service.service.impl.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController implements UserControllerI {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserOutDTO createUser(UserInDTO userInDTO) {

        return userService.createUser(userInDTO);
    }

    @Override
    public UserOutDTO updateUser(UserInDTO userInDTO, Long id) throws UserNotFoundException {

        return userService.update(userInDTO, id);
    }

    @Override
    public Long deleteUser(Long id) throws UserNotFoundException {

        return userService.deleteUser(id);
    }

    @Override
    public UserOutDTO getUser(Long id) throws UserNotFoundException {

        return userService.getUser(id);
    }

    @Override
    public void changePassword(ChangePasswordUserInDTO changePasswordUserInDTO) {
        userService.changePassword(changePasswordUserInDTO);
    }
}
