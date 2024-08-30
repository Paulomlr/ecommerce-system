package com.paulomlr.ecommerceSystem.config;

import com.paulomlr.ecommerceSystem.domain.User;
import com.paulomlr.ecommerceSystem.domain.enums.UserRole;
import com.paulomlr.ecommerceSystem.repositories.UserRepository;
import com.paulomlr.ecommerceSystem.services.exceptions.UniqueConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createDefaultUser() {
        return args -> {
            String username = "admin";
            String password = "admin123";

            if(userRepository.findByLogin(username).isEmpty()){
                var user = new User(username, passwordEncoder.encode(password), UserRole.ADMIN);
                userRepository.save(user);
            } else throw new UniqueConstraintViolationException("Login already exists.");
        };
    }
}
