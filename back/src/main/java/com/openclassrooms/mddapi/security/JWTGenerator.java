package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTGenerator {
    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;
    private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();

    private static final Logger logger = LoggerFactory.getLogger(JWTGenerator.class);

    public String generateToken(String email) {
        Date expireDate = new Date(new Date().getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromJWT(String token) {
        return jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
