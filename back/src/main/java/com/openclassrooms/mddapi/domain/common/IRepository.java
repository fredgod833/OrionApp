package com.openclassrooms.mddapi.domain.common;

import com.openclassrooms.mddapi.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface IRepository <T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
}
