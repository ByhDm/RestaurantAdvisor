package com.example.user_service.controller;

import com.example.user_service.dto.in.RoleInDTO;
import com.example.user_service.dto.out.RoleOutDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RequestMapping("/role")
public interface RoleControllerI {

    @Operation(summary = "Create role")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "role is create"),
            @ApiResponse(
                    responseCode = "404",
                    description = "role is not create"
            )
    })
    @PostMapping
    RoleOutDTO createRole(@RequestBody RoleInDTO roleInDTO) throws RoleNotFoundException;

    @Operation(summary = "Delete role by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "role is delete"),
            @ApiResponse(
                    responseCode = "404",
                    description = "role is not delete"
            )
    })
    @DeleteMapping( "/{id}")
    Long deleteRole (@PathVariable Long id) throws RoleNotFoundException;
}
