package com.example.user_service.mapper;

import com.example.user_service.dto.in.UserRoleInDTO;
import com.example.user_service.dto.out.UserRoleOutDTO;
import com.example.user_service.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleOutDTO userRoleToUserRoleOutDTO(UserRole userRole);
    @Mapping(target = "id", ignore = true)
    UserRole userRoleInDTOToUserRole(UserRoleInDTO userRoleInDTO);
}