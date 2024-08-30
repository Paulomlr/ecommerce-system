package com.paulomlr.ecommerceSystem.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(@NotBlank String token) {
}
