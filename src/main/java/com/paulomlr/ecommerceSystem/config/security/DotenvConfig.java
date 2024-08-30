package com.paulomlr.ecommerceSystem.config.security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}
