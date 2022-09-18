package com.example.user_service.service;

import com.example.user_service.dto.in.RoleInDTO;
import com.example.user_service.dto.in.UserRoleInDTO;
import com.example.user_service.dto.out.RoleOutDTO;
import com.example.user_service.dto.out.UserRoleOutDTO;

import javax.management.relation.RoleNotFoundException;

public interface RoleServiceI {
        RoleOutDTO createRole(RoleInDTO roleInDTO) throws RoleNotFoundException;
        Long deleteRole(Long id) throws RoleNotFoundException;
        RoleOutDTO getRole(Long id) throws RoleNotFoundException;
        UserRoleOutDTO addRoleToUser(UserRoleInDTO userRoleInDTO) throws RoleNotFoundException;
        Long deleteRoleToUser(Long id) throws RoleNotFoundException;
        UserRoleOutDTO getUserRole(Long id) throws RoleNotFoundException;
}
