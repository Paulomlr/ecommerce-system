package com.paulomlr.ecommerceSystem.domain.dto.sale;

import com.paulomlr.ecommerceSystem.domain.dto.productSale.ProductSaleRequestDTO;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record SaleRequestDTO (@NotNull UUID userId,
                              @NotNull Set<ProductSaleRequestDTO> items){
}
