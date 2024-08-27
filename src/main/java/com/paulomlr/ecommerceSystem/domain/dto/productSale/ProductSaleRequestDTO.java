package com.paulomlr.ecommerceSystem.domain.dto.productSale;

import java.util.UUID;

public record ProductSaleRequestDTO (UUID productId, int quantity){
}
