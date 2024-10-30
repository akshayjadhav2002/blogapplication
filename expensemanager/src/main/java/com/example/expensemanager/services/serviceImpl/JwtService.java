package com.example.expensemanager.services.serviceImpl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Ensure the secret key is at least 32 characters (or Base64 encoded)
    private static final String SECRET_KEY = "9e412c42bca1a755c8bda6d1f16036c4466a41562875d86d59e2fdb71cb2e1a7bd5de17b14637307c3fc2338f14bdac8710377d11f6343d81fd0d73a8ac495f08e64fc6be6a5e4a8096891ecc8a16f65d025f79af3316f1980e5ea23865d3f72a0433d5979584021cd95256f25d04e1ef0a1f8ffa226081659562e6d9c7eb7d0a4b771b99e8a17406708ebcb6c860b8c1e764b7a0a7450eeecdba34a291f8b374517d6d38db825391b7c8ef9baac6a16faf8ef1444d8e283ee0d9c3a32d0fd9008d2dce0136027d4a279bf184cb8a288472193fdecfe77abdc1d3dd25f0a9017924e82214c64398357960a80d8a48516ceabed489ac7798ab8a95db5d0158f6a";

    // Token expiration time: 24 hours (in milliseconds)
    private static final long EXPIRATION_TIME = 86400000L;

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

