package com.paulomlr.ecommerceSystem.repositories;

import com.paulomlr.ecommerceSystem.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
