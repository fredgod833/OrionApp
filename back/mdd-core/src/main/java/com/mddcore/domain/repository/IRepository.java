package com.mddcore.domain.repository;

import com.mddcore.domain.models.Identity;

import java.util.List;
import java.util.Optional;

public interface IRepository <T> {
    Optional<T> findById(Identity id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
}
