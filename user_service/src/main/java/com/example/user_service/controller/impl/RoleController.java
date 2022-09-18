package com.example.user_service.controller.impl;

import com.example.user_service.controller.RoleControllerI;
import com.example.user_service.dto.in.RoleInDTO;
import com.example.user_service.dto.out.RoleOutDTO;
import com.example.user_service.service.impl.RoleService;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

@RestController
public class RoleController implements RoleControllerI {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleOutDTO createRole(RoleInDTO roleInDTO) throws RoleNotFoundException {
        return roleService.createRole(roleInDTO);
    }

    @Override
    public Long deleteRole(Long id) throws RoleNotFoundException {
        return roleService.deleteRole(id);
    }
}
