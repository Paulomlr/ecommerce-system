package com.paulomlr.ecommerceSystem.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank @Size(min = 5, max = 15) String login,
        @NotNull @Size(min = 8, message = "Password must be at least 8 characters long")String password) {
}
