package com.paulomlr.ecommerceSystem.domain.dto.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.domain.dto.productSale.ProductSaleResponseDTO;
import com.paulomlr.ecommerceSystem.domain.enums.SaleStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SaleResponseDTO(UUID id,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT-3")
                              Instant instant,
                              SaleStatus saleStatus, Set<ProductSaleResponseDTO> items, Double total) {
    public SaleResponseDTO(Sale sale){
        this(sale.getSaleId(), sale.getSaleDate(), sale.getSaleStatus(),
                sale.getItems().stream().map(ProductSaleResponseDTO::new).collect(Collectors.toSet()), sale.getTotal());
    }
}
