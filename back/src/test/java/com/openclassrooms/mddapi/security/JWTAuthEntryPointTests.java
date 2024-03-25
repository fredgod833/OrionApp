package com.openclassrooms.mddapi.security;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JWTAuthEntryPointTests {

    @Test
    void testCommence() throws Exception {
        JWTAuthEntryPoint entryPoint = new JWTAuthEntryPoint();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException authException = new AuthenticationException("Unauthorized") {};

        entryPoint.commence(request, response, authException);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("Unauthorized", response.getErrorMessage());
    }
}
