package com.mddcore.domain.repository;

import com.mddcore.domain.models.User;

import java.util.Optional;

public interface IUserRepository extends IRepository<User> {
    Boolean existByEmail(String email);
    Optional<User> findByEmail(String email);
}
