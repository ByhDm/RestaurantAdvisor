package com.example.user_service.controller.impl;

import com.example.user_service.controller.UserControllerI;
import com.example.user_service.dto.in.ChangeOwnerInDTO;
import com.example.user_service.dto.in.ChangePasswordUserInDTO;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.ChangeOwnerOutDTO;
import com.example.user_service.dto.out.DeletedOwnerOutDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.exceptions.UserNotFoundException;
import com.example.user_service.service.impl.UserService;
import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserControllerI {

    private final UserService userService;
    private final RabbitMessageOperations rabbitTemplate;

    public UserController(UserService userService, RabbitMessageOperations rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
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
    public void changeOwner(ChangeOwnerInDTO changeOwnerInDTO) {
        rabbitTemplate.convertSendAndReceive("myQueueChangeOwner",
                new ChangeOwnerOutDTO(changeOwnerInDTO.getOldOwnerId(),
                        changeOwnerInDTO.getNewOwnerId()), ChangeOwnerOutDTO.class);
    }

    @Override
    public Long deleteUser(Long id) throws UserNotFoundException {
        Long deleteUserId = userService.deleteUser(id);
        rabbitTemplate.convertSendAndReceive("myQueueDeleteOwner", new DeletedOwnerOutDTO(id), DeletedOwnerOutDTO.class);
        return deleteUserId;
    }

    @Override
    public UserOutDTO getUser(Long id) throws UserNotFoundException {
        return userService.getUser(id);
    }

    @Override
    public void changePassword(ChangePasswordUserInDTO changePasswordUserInDTO) throws UserNotFoundException {
        userService.changePassword(changePasswordUserInDTO);
    }
}
