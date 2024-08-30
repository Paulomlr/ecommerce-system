package com.paulomlr.ecommerceSystem.domain.dto.product;

import com.paulomlr.ecommerceSystem.domain.Product;

import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, Double price, Integer stockQuantity) {
    public ProductResponseDTO(Product product){
        this(product.getProductId(), product.getName(), product.getPrice(), product.getStockQuantity());
    }
}