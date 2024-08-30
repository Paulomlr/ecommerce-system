package com.paulomlr.ecommerceSystem.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paulomlr.ecommerceSystem.domain.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //@Value("${api.security.token.secret}")
    private final String secret;

    public TokenService(Dotenv dotenv) {
        secret = dotenv.get("JWT_SECRET");
    }

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("ecommerceSystem")
                .withSubject(user.getLogin())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("ecommerceSystem")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
