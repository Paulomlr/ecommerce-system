package com.paulomlr.ecommerceSystem.domain.dto.sale;

import com.paulomlr.ecommerceSystem.domain.dto.productSale.ProductSaleRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record SaleRequestDTO (@NotNull UUID userId,
                              @NotNull @Size(min = 1) Set<@Valid ProductSaleRequestDTO> items){
}
