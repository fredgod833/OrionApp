package com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth;

public interface IUserDetailsService<E> {
    E loadUserByUsername(String username);
}
