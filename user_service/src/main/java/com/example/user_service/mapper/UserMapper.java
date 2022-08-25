package com.example.user_service.mapper;

import com.example.user_service.dto.in.UserInDTO;
import com.example.user_service.dto.out.UserOutDTO;
import com.example.user_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserOutDTO userToUserOutDTO(User user);
    User userInDTOToUser(UserInDTO userInDTO);
}
