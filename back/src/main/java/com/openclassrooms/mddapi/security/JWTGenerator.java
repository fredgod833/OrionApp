package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT has expired", e);
        } catch (MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Malformed JWT", e);
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT validation failed", e);
        }
    }
}
