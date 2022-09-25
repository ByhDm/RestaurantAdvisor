package com.example.user_service.mapper;

import com.example.user_service.dto.in.RoleInDTO;
import com.example.user_service.dto.out.RoleOutDTO;
import com.example.user_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "roleName", ignore = true)
    RoleOutDTO roleToRoleOutDTO(Role role);
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
    Role roleInDTOToRole(RoleInDTO roleInDTO);
}