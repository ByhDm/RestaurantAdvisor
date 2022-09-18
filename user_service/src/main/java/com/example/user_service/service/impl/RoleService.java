package com.example.user_service.service.impl;

import com.example.user_service.dto.in.RoleInDTO;
import com.example.user_service.dto.in.UserRoleInDTO;
import com.example.user_service.dto.out.RoleOutDTO;
import com.example.user_service.dto.out.UserRoleOutDTO;
import com.example.user_service.entity.Role;
import com.example.user_service.entity.UserRole;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.mapper.UserRoleMapper;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRoleRepository;
import com.example.user_service.service.RoleServiceI;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoleService implements RoleServiceI {
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    public RoleService(RoleRepository roleRepository
            , UserRoleRepository userRoleRepository
            , RoleMapper roleMapper
            , UserRoleMapper userRoleMapper) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    @Transactional
    public RoleOutDTO createRole(RoleInDTO roleInDTO) throws RoleNotFoundException {
        if(roleRepository.existsRoleByName(roleInDTO.getRoleName())){
            throw new RoleNotFoundException();
        }
        Role role = roleMapper.roleInDTOToRole(roleInDTO);
        return roleMapper.roleToRoleOutDTO(roleRepository.save(role));
    }

    @Override
    @Transactional
    public Long deleteRole(Long id) throws RoleNotFoundException {
        if(!roleRepository.existsById(id)){
            throw  new RoleNotFoundException();
        }
        roleRepository.deleteById(id);
        return id;
    }

    @Override
    public RoleOutDTO getRole(Long id) throws RoleNotFoundException {
        Optional<Role> byId = roleRepository.findById(id);
        if(byId.isEmpty()){
            throw new RoleNotFoundException();
        }
        return roleMapper.roleToRoleOutDTO(byId.get());
    }

    @Override
    @Transactional
    public UserRoleOutDTO addRoleToUser(UserRoleInDTO userRoleInDTO) throws RoleNotFoundException {
        if(userRoleRepository.existsByIdUserAndIdRole(userRoleInDTO.getIdUser(), userRoleInDTO.getIdRole())){
            throw new RoleNotFoundException();
        }
        UserRole userRole = userRoleMapper.userRoleInDTOToUserRole(userRoleInDTO);
        return userRoleMapper.userRoleToUserRoleOutDTO(userRoleRepository.save(userRole));
    }

    @Override
    public Long deleteRoleToUser(Long id) throws RoleNotFoundException {
        if(!userRoleRepository.existsById(id)){
            throw new RoleNotFoundException();
        }
        userRoleRepository.deleteById(id);
        return id;
    }

    @Override
    public UserRoleOutDTO getUserRole(Long id) throws RoleNotFoundException {
        Optional<UserRole> byId = userRoleRepository.findById(id);
        if(byId.isEmpty()){
            throw new RoleNotFoundException();
        }
        return userRoleMapper.userRoleToUserRoleOutDTO(byId.get());
    }
}
