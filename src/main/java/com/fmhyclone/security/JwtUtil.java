package com.fmhyclone.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final SecretKey Key;
    private final long expirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.Key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Key)
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            parseClaims(token);
            return true;
        } catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(Key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
