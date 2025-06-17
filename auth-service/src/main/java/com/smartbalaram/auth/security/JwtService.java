// File: JwtService.java
package com.smartbalaram.auth.security;

import com.smartbalaram.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * JWT Service:
 * ✅ Generates JWT tokens with claims
 * ✅ Validates token against user
 * ✅ Extracts claims from token
 */
@Service
public class JwtService {
	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    /**
	 * Returns the signing key used for JWT token generation.
	 * Uses HMAC SHA-256 algorithm with the secret from application properties.
	 */
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

   // private static final long EXPIRATION_TIME = 86400000; // 1 day (ms)
    //private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // HS256 secret key

    /**
     * Generates a JWT token for a given user.
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())             // Username = subject
                .claim("role", user.getRole())              // Custom claim
                .setIssuedAt(new Date())                    // Token issue timestamp
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Expiry
                .signWith(getSignKey(), SignatureAlgorithm.HS256)                              // Sign with secret key
                .compact();
    }

    /**
     * Validates token against provided user details.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extracts the username (subject) from the JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the token expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts any custom claim from the token using a resolver function.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses and returns all claims inside the JWT.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // Set secret
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the token is expired.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
