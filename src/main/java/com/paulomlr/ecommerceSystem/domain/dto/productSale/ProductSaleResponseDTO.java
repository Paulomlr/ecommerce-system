package com.paulomlr.ecommerceSystem.domain.dto.productSale;

import com.paulomlr.ecommerceSystem.domain.ProductSale;
import com.paulomlr.ecommerceSystem.domain.dto.product.ProductResponseDTO;

public record ProductSaleResponseDTO(ProductResponseDTO product, Integer quantity, Double subTotal) {
    public ProductSaleResponseDTO(ProductSale productSale){
        this(new ProductResponseDTO(productSale.getProduct()), productSale.getQuantity(), productSale.getSubTotal());
    }
}
