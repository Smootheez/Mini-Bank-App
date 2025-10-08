package dev.smootheez.minibankapp.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import javax.crypto.*;
import java.time.*;
import java.util.*;
import java.util.function.*;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes()); // Create a secret key from the secret key string
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject); // Extract the subject claim from the token
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Apply the claims resolver to the claims
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey()) // Verify the token with the secret key
                .build()
                .parseSignedClaims(token) // Parse the token
                .getPayload();
    }

    // Generate a JWT token from an email
    public String generateToken(String email) {
        Instant now = Instant.now(); // Get the current time
        Instant expiry = now.plusSeconds(jwtExpiration); // Calculate the expiry time

        return Jwts.builder()
                .subject(email) // Set the subject claim to the email
                .issuedAt(Date.from(now))        // convert Instant → Date
                .expiration(Date.from(expiry))   // convert Instant → Date
                .signWith(getSecretKey()) // Sign the token with the secret key
                .compact();
    }

    // Validate a JWT token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token); // Extract the username from the token
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); // Check if the username and token are valid
    }

    // Check if a JWT token is expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(Date.from(Instant.now()));
    }
}
