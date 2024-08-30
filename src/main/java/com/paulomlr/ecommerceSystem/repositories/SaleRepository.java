package com.paulomlr.ecommerceSystem.repositories;

import com.paulomlr.ecommerceSystem.domain.Sale;
import com.paulomlr.ecommerceSystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    Optional<Sale> findBySaleDate(Instant saleDate);
    List<Sale> findBySaleDateBetween(Instant startDate, Instant endDate);
    List<Sale> findByUser(User user);
}
