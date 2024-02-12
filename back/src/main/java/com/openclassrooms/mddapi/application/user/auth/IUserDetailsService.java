package com.openclassrooms.mddapi.application.user.auth;

public interface IUserDetailsService<E> {
    E loadUserByUsername(String username);
}
