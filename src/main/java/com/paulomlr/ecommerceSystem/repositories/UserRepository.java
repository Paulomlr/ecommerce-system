package com.paulomlr.ecommerceSystem.repositories;

import com.paulomlr.ecommerceSystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
