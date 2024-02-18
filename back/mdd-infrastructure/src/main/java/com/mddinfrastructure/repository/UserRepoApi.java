package com.mddinfrastructure.repository;

import java.util.List;
import java.util.Optional;

public interface UserRepoApi <User> {
    Boolean existByEmail(String email);
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
}
