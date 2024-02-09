package com.openclassrooms.mddapi.domain.user.auth;

public interface IUserDetailsService<E> {
    E loadUserByUsername(String username);
}
