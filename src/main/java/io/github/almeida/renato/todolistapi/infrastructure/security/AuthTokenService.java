package io.github.almeida.renato.todolistapi.infrastructure.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service("authTokenService")
public class AuthTokenService {

    @Value("${jwt.security.token.secret}")
    private String secret;
    @Value("${spring.application.name}")
    private String appName;

    public String generateToken(AuthUser user) {
        try {
            return JWT.create()
                    .withIssuer(appName)
                    .withIssuedAt(Instant.now())
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }

    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(appName)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
