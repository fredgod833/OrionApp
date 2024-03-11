package com.openclassrooms.mddapi.services;

import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {
    /**
     * Generates a token for the user with the given email.
     *
     * @param email  The user's email.
     * @return  The generated token.
     */
    String generateToken(String email);

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email     The user's email.
     * @param password  The user's password.
     * @throws BadCredentialsException  If the provided credentials are invalid.
     */
    void authenticate(String email, String password) throws BadCredentialsException;
}
