package com.paulomlr.ecommerceSystem.repositories;

import com.paulomlr.ecommerceSystem.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
