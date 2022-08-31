package com.example.user_service.service.impl;

import com.example.user_service.dto.in.ChangePasswordUserInDTO;
import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.entity.User;
import com.example.user_service.exceptions.UserNotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserServiceI;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements UserServiceI {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserOutDTO createUser(UserInDTO userInDTO) {
        if (userRepository.existsByEmail(userInDTO.getEmail())) {
            throw new RuntimeException();
        }
        User user = userMapper.userInDTOToUser(userInDTO);
        User saveUser = userRepository.save(user);
        return userMapper.userToUserOutDTO(saveUser);
    }

    @Override
    @Transactional
    public UserOutDTO update(UserInDTO userInDTO, Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id) && userRepository.existsByEmail(userInDTO.getEmail())) {
            throw new UserNotFoundException();
        }
        User user = userMapper.userInDTOToUser(userInDTO);
        user.setId(id);
        User saveUser = userRepository.save(user);
        return userMapper.userToUserOutDTO(saveUser);
    }

    @Override
    public Long deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public UserOutDTO getUser(Long id) throws UserNotFoundException {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userMapper.userToUserOutDTO(byId.get());
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordUserInDTO changePasswordUserInDTO) {
        Optional<User> byEmail = userRepository.findByEmail(changePasswordUserInDTO.getEmail());
        if (!byEmail.get().getPassword().equals(changePasswordUserInDTO.getOldPassword())) {
            throw new RuntimeException();
        }
        byEmail.get().setPassword(changePasswordUserInDTO.getNewPassword());
    }
}
