package com.politecnico.polibus.backend.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final long EXPIRATION_TIME = 1000 * 60 * 60;
    private static Key SECRET_KEY;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @PostConstruct
    public void init() {
        if (SECRET_KEY == null) {
            SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            logger.info("Generated Secret Key (Base64): {}", getSecretKeyAsString());
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, username);
        logger.info("Generated Token: {}", token);
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        logger.info("Validating token: {}", token);
        logger.info("Secret Key (Base64): {}", getSecretKeyAsString());
        String username = extractUsername(token);
        boolean isValid = username != null && !isTokenExpired(token);
        logger.info("Token validation result for user {}: {}", username, isValid);
        return isValid;
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        logger.info("Token being parsed: {}", token);
        logger.info("Secret key (raw): {}", getSecretKeyAsString());
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSecretKeyAsString() {
        return Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded());
    }
}
