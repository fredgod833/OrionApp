package com.openclassrooms.mddapi.infrastructure.security;

public interface IJwtExec {
    String makeToken(String email, String password);
}
