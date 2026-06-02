package com.gobet.hotels.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "THIS_IS_SUPER_SECRET_KEY_FOR_HOTEL_SYSTEM_123456789";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    public String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)
                )
                .signWith(getKey())
                .compact();
    }
    public boolean isTokenValid(String token) {

        try {

            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}