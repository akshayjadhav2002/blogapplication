package com.example.expensemanager.services.serviceImpl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Ensure the secret key is at least 32 characters (or Base64 encoded)
    @Value("${app.jwt-secret}")
    private String SECRET_KEY;

    // Token expiration time: 24 hours (in milliseconds)
    @Value("${app.jwt-expiration-milliseconds}")
    private long EXPIRATION_TIME;

    // Generate a signing key using the secret key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for a given username.
     *
     * @param username the username for whom the token is generated.
     * @return the generated JWT token.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validates the JWT token by checking its signature and expiration.
     *
     * @param token the JWT token to validate.
     * @return true if valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);  // Will throw an exception if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token.
     * @return the username (subject).
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}

