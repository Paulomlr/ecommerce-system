package com.paulomlr.ecommerceSystem.repositories;

import com.paulomlr.ecommerceSystem.domain.ProductSale;
import com.paulomlr.ecommerceSystem.domain.pk.ProductSalePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSaleRepository extends JpaRepository<ProductSale, ProductSalePK> {
}
