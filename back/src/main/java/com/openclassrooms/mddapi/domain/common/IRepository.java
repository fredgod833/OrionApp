package com.openclassrooms.mddapi.domain.common;

import java.util.List;
import java.util.Optional;

public interface IRepository <T, ID> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity);
    void delete(Long id);
    T update(T entity);
    Boolean exist(Long id);
}
