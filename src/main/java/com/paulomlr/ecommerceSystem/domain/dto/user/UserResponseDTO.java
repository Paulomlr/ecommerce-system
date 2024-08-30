package com.paulomlr.ecommerceSystem.domain.dto.user;

import com.paulomlr.ecommerceSystem.domain.User;

import java.util.UUID;

public record UserResponseDTO(UUID id) {
    public UserResponseDTO(User user) {
        this(user.getUserId());
    }
}
