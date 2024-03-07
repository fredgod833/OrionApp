package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTGenerator {
    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 10;
    private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();

    public String generateToken(Authentication authentication) {
        Date expireDate = new Date(new Date().getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(authentication.getName())
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

