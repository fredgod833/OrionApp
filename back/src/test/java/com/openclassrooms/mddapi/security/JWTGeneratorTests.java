package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JWTGeneratorTests {
    
    private JWTGenerator jwtGenerator;
    private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    @BeforeEach
    public void setUp() {
        jwtGenerator = new JWTGenerator();
        ReflectionTestUtils.setField(jwtGenerator, "JWT_EXPIRATION", 86400000);
    }

    @Test
    public void testGenerateJwtToken() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .username("Test user")
                .build();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtGenerator.generateToken(authentication.getName());

        assertTrue(token != null && !token.isEmpty());
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        String token = Jwts.builder()
                .setSubject("Test user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        String username = jwtGenerator.getEmailFromJWT(token);

        assertEquals("Test user", username);
    }

    @Test
    public void testValidateJwtToken_ValidToken() {
        String token = Jwts.builder()
                .setSubject("Test user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        boolean isValid = jwtGenerator.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    public void testValidateJwtToken_InvalidToken() {
        boolean isValid = jwtGenerator.validateToken("invalidToken");

        assertFalse(isValid);
    }

    @Test
    public void testValidateJwtToken_ExpiredToken() {
        String token = Jwts.builder()
                .setSubject("Test user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        boolean isValid = jwtGenerator.validateToken(token);

        assertFalse(isValid);
    }

    @Test
    public void testValidateJwtToken_EmptyClaimsString() {
        boolean isValid = jwtGenerator.validateToken("");

        assertFalse(isValid);
    }
}
