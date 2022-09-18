package com.example.restaurantadvisor.dto.in;

import lombok.Data;

@Data
public class ChangeOwnerInDTO {
    private Long oldIdBoss;
    private Long newIdBoss;
}
