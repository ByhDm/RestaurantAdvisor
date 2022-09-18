package com.example.user_service.dto.in;

import lombok.Data;

@Data
public class ChangeOwnerInDTO {
    private Long oldOwnerId;
    private Long newOwnerId;
}